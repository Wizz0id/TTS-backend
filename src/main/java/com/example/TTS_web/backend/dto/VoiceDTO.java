package com.example.TTS_web.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoiceDTO {
    private long id;
    private String name;
    private String language;
}
