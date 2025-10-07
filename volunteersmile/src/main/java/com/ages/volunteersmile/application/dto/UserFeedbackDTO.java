package com.ages.volunteersmile.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserFeedbackDTO {
    private UUID userId;
    private String feedback;
}
