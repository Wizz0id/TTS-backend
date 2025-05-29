package com.example.TTS_web.backend.repository;

import com.example.TTS_web.backend.entity.GeneratedSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeechRepository extends JpaRepository<GeneratedSpeech, Long> {
    @Query(value = "select * from speech " +
            "where text ilike :text and model_id=:modelId " +
            "ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<GeneratedSpeech> findByText(String text, long modelId);
}
