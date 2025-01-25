package com.example.server.kafka;

import com.example.server.model.dto.ReceivedMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendMessage(String topic, ReceivedMessageDto receivedMessageDto) {
        try {
            var messageJson = objectMapper.writeValueAsString(receivedMessageDto);
            kafkaTemplate.send(topic, messageJson);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize message to JSON: {}", e.getMessage());
            throw new RuntimeException("Failed to serialize message to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred while sending the message: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred while sending the message", e);
        }
    }

}
