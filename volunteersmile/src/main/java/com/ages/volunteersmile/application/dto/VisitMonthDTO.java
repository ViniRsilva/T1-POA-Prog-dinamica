package com.ages.volunteersmile.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitMonthDTO {
    private Integer roomNumber;
    private Integer floor;
    private LocalDate scheduleDate;
}
