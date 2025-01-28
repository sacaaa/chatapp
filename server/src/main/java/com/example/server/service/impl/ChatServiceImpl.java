package com.example.server.service.impl;

import com.example.server.kafka.KafkaProducer;
import com.example.server.model.Message;
import com.example.server.model.dto.ChatRoomDto;
import com.example.server.model.dto.MessageDto;
import com.example.server.model.dto.ReceivedMessageDto;
import com.example.server.repository.ChatRoomRepository;
import com.example.server.repository.MessageRepository;
import com.example.server.repository.UserRepository;
import com.example.server.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final KafkaProducer kafkaProducer;

    private final UserRepository userRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final MessageRepository messageRepository;

    @Override
    public void sendMessageToChatRoom(MessageDto messageDto) {
        var chatRoom = chatRoomRepository.findById(messageDto.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found"));
        var sender = userRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        Message message = new Message();
        message.setChatRoom(chatRoom);
        message.setSender(sender);
        message.setContent(messageDto.getContent());

        messageRepository.save(message);

        var loadedMessage = messageRepository.findById(message.getId())
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
        var receivedMessageDto = convertToReceivedMessageDto(loadedMessage);
        kafkaProducer.sendMessage("chatroom_" + chatRoom.getId(), receivedMessageDto);
    }

    @Override
    public List<ReceivedMessageDto> receiveMessageFromChatRoom(Long chatRoomId) {
        return messageRepository.findAllByChatRoomId(chatRoomId).stream()
                .map(this::convertToReceivedMessageDto)
                .toList();
    }

    @Override
    public List<ChatRoomDto> getChatRooms() {
        return chatRoomRepository.findAll().stream()
                .map(chatRoom -> {
                    var chatRoomDto = new ChatRoomDto();
                    chatRoomDto.setId(chatRoom.getId());
                    chatRoomDto.setName(chatRoom.getName());
                    chatRoomDto.setDescription(chatRoom.getDescription());
                    return chatRoomDto;
                })
                .toList();
    }

    private ReceivedMessageDto convertToReceivedMessageDto(Message message) {
        var receivedMessageDto = new ReceivedMessageDto();
        receivedMessageDto.setId(message.getId());
        receivedMessageDto.setContent(message.getContent());
        receivedMessageDto.setChatRoomId(message.getChatRoom().getId());
        receivedMessageDto.setSenderId(message.getSender().getId());
        receivedMessageDto.setSenderNickname(message.getSender().getNickname());
        receivedMessageDto.setSenderAvatarId(message.getSender().getAvatarId());
        receivedMessageDto.setCreatedAt(message.getCreatedAt());
        return receivedMessageDto;
    }

}
