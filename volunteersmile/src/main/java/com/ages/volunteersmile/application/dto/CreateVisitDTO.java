package com.ages.volunteersmile.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDate scheduleDate; // dia selecionado para agendamento

    @NotNull
    private LocalDateTime startDate; // data de início agendada

    private LocalDateTime endDate; // opcional para visitas de múltiplos dias

    @Min(1)
    private Integer durationMinutes; // opcional, calculado posteriormente

    @Min(1)
    private Integer totalTime;

    @Size(max = 5000)
    private String notes;
}
