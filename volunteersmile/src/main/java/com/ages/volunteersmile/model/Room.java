package com.ages.volunteersmile.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Integer number;

    @Column(name = "difficulty_level", nullable = false)
    private Integer difficultyLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }
}
