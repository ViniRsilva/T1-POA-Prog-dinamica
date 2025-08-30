package com.ages.volunteersmile.application.dto;

import java.util.UUID;

import com.ages.volunteersmile.application.Enum.UserStatus;
import com.ages.volunteersmile.domain.global.model.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private UserRole role;
    private UserStatus status;
}
