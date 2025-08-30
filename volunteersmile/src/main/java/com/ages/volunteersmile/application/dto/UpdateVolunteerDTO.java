package com.ages.volunteersmile.application.dto;

import com.ages.volunteersmile.application.Enum.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UpdateVolunteerDTO {

    @Size(max = 150)
    @Email
    private String email;

    @Size(max = 120)
    private String name;

    @Size(max = 30)
    private String phoneNumber;

    @Size(max = 1000)
    private String description;

    private UserStatus status;
    private RoomAccessLevelDTO roomAccessLevel;
}
