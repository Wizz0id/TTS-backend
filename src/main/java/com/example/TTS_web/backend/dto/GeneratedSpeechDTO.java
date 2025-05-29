package com.example.TTS_web.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeneratedSpeechDTO {
    private long id;
    private String text;
    private String pathToAudio;
    private long modelId;
    private String speaker;
}
