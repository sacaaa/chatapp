package com.example.server.repository;

import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNicknameOrEmail(String nickname, String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

}
