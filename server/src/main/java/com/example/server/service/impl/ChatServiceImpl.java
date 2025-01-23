package com.example.server.service.impl;

import com.example.server.model.User;
import com.example.server.model.data.Result;
import com.example.server.model.dto.MessageSenderDto;
import com.example.server.model.dto.UserDto;
import com.example.server.repository.UserRepository;
import com.example.server.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    @Override
    public MessageSenderDto convertToDto(Object object) {
        if (object instanceof User) {
            return objectMapper.convertValue(object, MessageSenderDto.class);
        }
        throw new IllegalArgumentException("Object is not an instance of User");
    }

    @Override
    public Result<Void> sendMessage(Long chatroomId, Long userId, String message) {
        return null;
    }

}
