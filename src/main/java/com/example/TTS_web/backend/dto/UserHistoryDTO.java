package com.example.TTS_web.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserHistoryDTO {
    private long id;
    private long userId;
    private String username;
    private LocalDateTime dateTime;
    private long speechId;
    private String text;
    private String pathToAudio;
}
