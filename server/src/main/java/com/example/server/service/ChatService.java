package com.example.server.service;

import com.example.server.model.data.Result;
import com.example.server.model.dto.MessageSenderDto;

public interface ChatService extends DtoService<MessageSenderDto> {

    Result<Void> sendMessage(Long chatroomId, Long userId, String message);

}
