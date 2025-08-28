package com.ages.volunteersmile.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String description;
    private UserStatusDTO status;
    private RoomAccessLevelDTO roomAccessLevel;
    private LocalDateTime createdAt;
}
