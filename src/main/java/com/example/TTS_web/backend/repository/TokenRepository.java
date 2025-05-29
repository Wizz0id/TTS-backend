package com.example.TTS_web.backend.repository;


import com.example.TTS_web.backend.entity.TrainingToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TrainingToken, Long> {
    Optional<TrainingToken> findByToken(String token);
}
