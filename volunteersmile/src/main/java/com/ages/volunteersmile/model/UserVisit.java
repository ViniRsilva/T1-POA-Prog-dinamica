package com.ages.volunteersmile.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users_visits")
public class UserVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visit", nullable = false)
    private Visit visit;

    @Column(name = "volunteer_feedback", columnDefinition = "TEXT")
    private String volunteerFeedback;

    @Column(name = "attendance_confirmed")
    private Boolean attendanceConfirmed;
}
