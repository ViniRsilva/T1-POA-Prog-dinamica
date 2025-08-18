package com.ages.volunteersmile.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("VOLUNTEER")
public class Volunteer extends User {

    @Column(name = "room_access_level")
    private Integer roomAccessLevel;

    @Column(name = "description_voluntary", columnDefinition = "TEXT")
    private String descriptionVoluntary;
}
