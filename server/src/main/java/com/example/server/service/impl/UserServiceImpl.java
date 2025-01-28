package com.example.server.service.impl;

import com.example.server.model.User;
import com.example.server.data.Result;
import com.example.server.model.dto.UserAuthDto;
import com.example.server.model.dto.UserDto;
import com.example.server.repository.UserRepository;
import com.example.server.service.JwtService;
import com.example.server.service.UserService;
import com.example.server.utils.Encrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    @Override
    public UserDto convertToDto(Object object) {
        if (object instanceof User) {
            return objectMapper.convertValue(object, UserDto.class);
        }
        throw new IllegalArgumentException("Object is not an instance of User");
    }

    @Override
    public Result<User> authenticate(UserAuthDto userAuthDto) {
        var nickname = userAuthDto.getNickname();
        var email = userAuthDto.getEmail();
        var password = userAuthDto.getPassword();

        var user = userRepository.findByNicknameOrEmail(nickname, email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getPassword().equals(Encrypt.encryptSha256(Encrypt.encryptSha256(password)))) {
            throw new UsernameNotFoundException("Invalid password");
        }

        return Result.success(user);
    }

    @Override
    public void register(UserAuthDto userAuthDto) {
        var nickname = userAuthDto.getNickname();
        var email = userAuthDto.getEmail();

        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("Nickname is already in use");
        } else if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        var user = objectMapper.convertValue(userAuthDto, User.class);
        user.setPassword(Encrypt.encryptSha256(Encrypt.encryptSha256(user.getPassword())));
        userRepository.save(user);
    }

    @Override
    public Result<UserDto> findById(Long id) {
        var result = userRepository.findById(id);
        var userDto = result.map(this::convertToDto);
        userDto.ifPresent(dto -> dto.setSentMessages(getSentMessageCount(id)));
        return userDto.map(Result::success)
                .orElseGet(() -> Result.failure("User not found"));
    }

    @Override
    public Result<String> refreshAccessToken(String refreshToken) {
        var email = jwtService.extractEmail(refreshToken);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new JwtException("Invalid refresh token");
        }

        return Result.success(jwtService.generateToken(user));
    }

    private Long getSentMessageCount(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return (long) user.getMessages().size();
    }

}
