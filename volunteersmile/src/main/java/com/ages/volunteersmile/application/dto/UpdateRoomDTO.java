package com.ages.volunteersmile.application.dto;

import com.ages.volunteersmile.application.Enum.RoomPriority;
import com.ages.volunteersmile.application.Enum.RoomStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoomDTO {
    @NotNull
    @Min(0)
    private Integer floor;

    @NotNull @Min(1)
    private Integer number;

    @NotNull @Min(1)
    private Integer maxOccupancy;

    @NotBlank
    private String sector;

    @NotNull
    private RoomStatus status;

    @NotNull
    private RoomPriority priority;

    @NotBlank
    private String description;
}
