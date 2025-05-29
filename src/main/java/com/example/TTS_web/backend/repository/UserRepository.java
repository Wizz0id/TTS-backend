package com.example.TTS_web.backend.repository;

import com.example.TTS_web.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from users " +
            "where username=:username", nativeQuery = true)
    Optional<User> findByUsername(String username);
}
