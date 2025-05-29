package com.example.TTS_web.backend.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Table(name="user_model", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "path_to_model", nullable = false)
    private String pathToModel;
    @Column(name = "description")
    private String description;
    @Column(name = "path_to_loss_plot", nullable = false)
    private String pathToLossPlot;
    @Column(name = "path_to_accuracy_plot", nullable = false)
    private String pathToAccuracyPlot;
    @Column(name = "stoi", nullable = false)
    private String stoi;
    @Column(name = "pesq", nullable = false)
    private String pesq;
    @Column(name = "cd", nullable = false)
    private String cd;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basic_id", referencedColumnName = "id")
    private BaseModel basic;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
