package com.example.server.kafka;

import com.example.server.model.dto.MessageDto;
import com.example.server.model.dto.ReceivedMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topicPattern = "chatroom_.*", groupId = "chat_group")
    public void consumeMessage(String messageJson) {
        try {
            ReceivedMessageDto messageDto = objectMapper.readValue(messageJson, ReceivedMessageDto.class);
            messagingTemplate.convertAndSend("/topic/chatroom/" + messageDto.getChatRoomId(), messageDto);
            log.info("Message consumed: {}", messageDto);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize message due to processing issue: {}", e.getMessage());
            throw new RuntimeException("Failed to deserialize message due to processing issue", e);
        } catch (MessagingException e) {
            log.error("Failed to send message to WebSocket: {}", e.getMessage());
            throw new RuntimeException("Failed to send message to WebSocket", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred while consuming the message: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred while consuming the message", e);
        }
    }

}
