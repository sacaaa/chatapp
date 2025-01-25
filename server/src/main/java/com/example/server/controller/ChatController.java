package com.example.server.controller;

import com.example.server.model.dto.ChatRoomDto;
import com.example.server.model.dto.MessageDto;
import com.example.server.model.dto.ReceivedMessageDto;
import com.example.server.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/message/send")
    public void sendMessageToChatRoom(@RequestBody MessageDto messageDto) {
        chatService.sendMessageToChatRoom(messageDto);
    }

    @GetMapping("/message/receive/{chatRoomId}")
    public ResponseEntity<List<ReceivedMessageDto>> receiveMessageFromChatRoom(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatService.receiveMessageFromChatRoom(chatRoomId));
    }

    @GetMapping("/chat-rooms")
    public ResponseEntity<List<ChatRoomDto>> getChatRooms() {
        return ResponseEntity.ok(chatService.getChatRooms());
    }

}
