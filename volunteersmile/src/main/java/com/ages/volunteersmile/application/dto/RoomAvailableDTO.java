package com.ages.volunteersmile.application.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomAvailableDTO {
    private UUID id;
    private Integer number;
    private Long daysSinceLastVisit;
}
