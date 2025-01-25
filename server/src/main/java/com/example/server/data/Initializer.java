package com.example.server.data;

import com.example.server.model.ChatRoom;
import com.example.server.model.User;
import com.example.server.repository.ChatRoomRepository;
import com.example.server.repository.UserRepository;
import com.example.server.utils.Encrypt;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        chatRoomRepository.saveAll(List.of(
            new ChatRoom(null, "chatroom_1", null, null, null),
            new ChatRoom(null, "chatroom_2", null, null, null),
            new ChatRoom(null, "chatroom_3", null, null, null)
        ));

        var password = Encrypt.encryptSha256("password");
        userRepository.saveAll(List.of(
            new User(null, "user_1", "user1@example.com", password, null, null ,null),
            new User(null, "user_2", "user2@example.com", password, null, null ,null),
            new User(null, "user_3", "user3@example.com", password, null, null ,null)
        ));
    }

}
