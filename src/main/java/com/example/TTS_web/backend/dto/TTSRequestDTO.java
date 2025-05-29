package com.example.TTS_web.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TTSRequestDTO {
    private String text;
    private String speaker = "xenia";
    private int sampleRate;
    private boolean putAccent = true;
    private boolean putYo = true;

}
