package com.example.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedMessageDto {

    private Long id;
    private String content;
    private Long chatRoomId;
    private String senderNickname;
    private LocalDateTime createdAt;

}
