package com.ages.volunteersmile.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {
    private String id;
    private String photoUrl;
    @JsonProperty("user_name")
    private String userName;
    private String date;
    private String feedback;
    private Integer floor;
    @JsonProperty("room")
    private Integer roomNumber;
}
