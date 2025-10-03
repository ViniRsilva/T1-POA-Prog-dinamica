package com.ages.volunteersmile.application.dto;


import java.time.LocalDateTime;

import com.ages.volunteersmile.application.Enum.UserStatus;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolunteerDTO extends UserDTO {

    private String email;
    private String name;
    private String phoneNumber;
    private String description;
    private UserStatus status;
    private RoomAccessLevelDTO roomAccessLevel;
    private LocalDateTime createdAt;
}
