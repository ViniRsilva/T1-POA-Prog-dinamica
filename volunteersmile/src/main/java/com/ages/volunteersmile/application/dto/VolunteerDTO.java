package com.ages.volunteersmile.application.dto;


import java.time.LocalDateTime;

public class VolunteerDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String description;
    private VolunteerStatusDTO status;
    private RoomAccessLevelDTO roomAccessLevel;
    private LocalDateTime createdAt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VolunteerStatusDTO getStatus() {
        return status;
    }

    public void setStatus(VolunteerStatusDTO status) {
        this.status = status;
    }

    public RoomAccessLevelDTO getRoomAccessLevel() {
        return roomAccessLevel;
    }

    public void setRoomAccessLevel(RoomAccessLevelDTO roomAccessLevel) {
        this.roomAccessLevel = roomAccessLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
