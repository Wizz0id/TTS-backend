package com.example.TTS_web.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublicationDTO {
    private long id;
    private String title;
    private String description;
    private String username;
    private UserModelDTO model;
    private boolean published;
}
