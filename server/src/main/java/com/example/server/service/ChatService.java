package com.example.server.service;

import com.example.server.data.Result;
import com.example.server.model.dto.ChatRoomDto;
import com.example.server.model.dto.MessageDto;
import com.example.server.model.dto.ReceivedMessageDto;

import java.util.List;

public interface ChatService {

    void sendMessageToChatRoom(MessageDto messageDto);

    List<ReceivedMessageDto> receiveMessageFromChatRoom(Long chatRoomId);

    List<ChatRoomDto> getChatRooms();

    Result<ChatRoomDto> getChatRoom(Long chatRoomId);

}
