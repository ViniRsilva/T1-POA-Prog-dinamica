package com.ages.volunteersmile.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitDTO {
    private UUID id;
    private UUID roomId;
    private Integer roomNumber;
    private UUID volunteerId;
    private String volunteerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime schedulingDate;
    private String status;
    private Integer durationMinutes;
    private Integer totalTime;
    private String notes;
}
