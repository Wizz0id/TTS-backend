package com.example.TTS_web.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="speech", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedSpeech {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "path", nullable = false)
    private String pathToAudio;
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private BaseModel model;
}
