package com.ages.volunteersmile.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolunteerStatusDTO {
    private Enum ACTIVE;
    private Enum INACTIVE;
    private Enum PENDING;
}
