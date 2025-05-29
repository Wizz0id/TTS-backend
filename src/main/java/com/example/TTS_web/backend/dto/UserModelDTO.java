package com.example.TTS_web.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModelDTO extends BaseModelDTO{
    private long id;
    private String name;
    private String description;
    private String pathToLossPlot;
    private String pathToAccuracyPlot;
    private String pathToModel;
    private String stoi;
    private String pesq;
    private String cd;
    private boolean published;
    private String baseName;
    private String userName;
}
