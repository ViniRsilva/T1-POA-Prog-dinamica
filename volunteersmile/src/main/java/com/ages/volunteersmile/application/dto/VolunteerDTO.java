package com.ages.volunteersmile.application.dto;


import com.ages.volunteersmile.application.Enum.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VolunteerDTO {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String description;
    private UserStatus status;
    private RoomAccessLevelDTO roomAccessLevel;
    private LocalDateTime createdAt;
}
