package com.ages.volunteersmile.application.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVisitDTO {
    @NotNull
    private UUID roomId;

    @NotNull
    private UUID volunteerId; // voluntário responsável

    @NotNull
    private LocalDate startDate; // data de início agendada

    private LocalDate endDate; // opcional para visitas de múltiplos dias

    @Min(1)
    private Integer durationMinutes; // opcional, calculado posteriormente

    @Size(max = 5000)
    private String notes;
}
