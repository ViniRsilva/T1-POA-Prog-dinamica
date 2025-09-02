package com.ages.volunteersmile.domain.global.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "room_feedbacks")
public class RoomFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String photoUrl;

    @Column(name = "user_name", nullable = false)
    private String userName;

    private LocalDate date;

    @Column(columnDefinition = "text")
    private String feedback;

    private Integer floor;
    private Integer roomNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}