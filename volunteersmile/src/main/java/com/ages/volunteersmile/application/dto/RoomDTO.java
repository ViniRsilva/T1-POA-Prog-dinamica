package com.ages.volunteersmile.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomDTO {
    private String id;
    private Integer floor;
    private Integer number;
    @JsonProperty("max_occupancy")
    private Integer maxOccupancy;
    private String sector;
    private String status;
    private Integer visits;
    private String priority;
    private String description;
    private List<FeedbackDTO> feedbacks;
}
