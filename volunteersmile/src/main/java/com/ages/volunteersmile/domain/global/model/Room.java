package com.ages.volunteersmile.domain.global.model;

import com.ages.volunteersmile.application.Enum.RoomPriority;
import com.ages.volunteersmile.application.Enum.RoomStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String sector;

    @Column(name = "max_occupancy", nullable = false)
    private Integer maxOccupancy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RoomFeedback> feedbacks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomPriority priority;
}

