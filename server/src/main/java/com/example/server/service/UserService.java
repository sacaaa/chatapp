package com.example.server.service;

import com.example.server.model.User;
import com.example.server.model.data.Result;
import com.example.server.model.dto.UserAuthDto;
import com.example.server.model.dto.UserDto;

public interface UserService extends DtoService<UserDto> {

    Result<User> authenticate(UserAuthDto userAuthDto);

    void register(UserAuthDto userAuthDto);

    Result<UserDto> findById(Long id);

    Result<String> refreshAccessToken(String refreshToken);

}
