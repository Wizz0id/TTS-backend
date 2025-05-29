package com.example.TTS_web.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseModelDTO {
    private long id;
    private String name;
    private List<VoiceDTO> voiceList;
}
