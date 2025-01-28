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
                new ChatRoom(null, "TechTalk", "Discuss the latest in tech and gadgets",
                        null, null, null),
                new ChatRoom(null, "BookLovers", "Share and discuss your favorite books",
                        null, null, null),
                new ChatRoom(null, "MovieMania", "Chat about the latest movies and TV shows",
                        null, null, null),
                new ChatRoom(null, "FitnessGoals", "Motivation and tips for staying fit and healthy",
                        null, null, null),
                new ChatRoom(null, "TravelersHub", "Explore destinations and share travel tips",
                        null, null, null),
                new ChatRoom(null, "GamerZone", "Talk about your favorite games and strategies",
                        null, null, null),
                new ChatRoom(null, "MusicVibes", "Discuss music genres, artists, and playlists",
                        null, null, null),
                new ChatRoom(null, "FoodiesUnite", "Share recipes and talk about delicious food",
                        null, null, null),
                new ChatRoom(null, "CryptoCorner", "Discuss blockchain, crypto trading, and trends",
                        null, null, null),
                new ChatRoom(null, "CodeCamp", "Share programming tips and discuss software development",
                        null, null, null)
        ));

        userRepository.saveAll(List.of(
                new User(null, "user", "asd@gmail.com",
                        Encrypt.encryptSha256(Encrypt.encryptSha256("password")), 1, null, null, null)
        ));
    }

}
