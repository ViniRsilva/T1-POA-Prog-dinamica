package com.ages.volunteersmile.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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



    @Size(max = 1000)
    private String descriptionVoluntary;
}
