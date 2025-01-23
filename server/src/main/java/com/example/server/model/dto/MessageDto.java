package com.example.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private Long id;
    private String content;
    private Long chatRoomId;
    private Long senderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
