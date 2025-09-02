package com.ages.volunteersmile.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomDTO {
    private Integer floor;
    private Integer number;
    private Integer difficultyLevel;
    private Integer maxOccupancy;
    private String sector;
    private String status;
    private Integer visits;
    private String priority;
    private String description;
    private List<FeedbackDTO> feedbacks;
}
