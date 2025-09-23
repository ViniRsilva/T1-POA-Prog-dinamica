package com.ages.volunteersmile.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitTimeDTO {
    private long durationMinutes;
    private String formattedDuration;

}
