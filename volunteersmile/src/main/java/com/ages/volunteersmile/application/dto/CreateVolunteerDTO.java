package com.ages.volunteersmile.application.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Getter
@Setter
public class CreateVolunteerDTO {
    @NotBlank
    @Email
    @Size(max = 150)
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String name;

    @Size(max = 30)
    private String phoneNumber;

    private String status;

    @Min(1)
    @Max(10)
    private Integer roomAccessLevel;

    @Size(max = 1000)
    private String descriptionVoluntary;
}
