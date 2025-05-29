package com.example.TTS_web.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="base_model", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "path_to_model", nullable = false)
    private String pathToModel;

    @OneToMany(mappedBy = "model")
    @ToString.Exclude
    private List<GeneratedSpeech> speechList;

    @OneToMany(mappedBy = "basic", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<UserModel> modelList;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Voice> voiceList;
}
