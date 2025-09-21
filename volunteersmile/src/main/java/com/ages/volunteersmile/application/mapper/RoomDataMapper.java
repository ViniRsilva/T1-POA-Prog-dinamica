package com.ages.volunteersmile.application.mapper;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ages.volunteersmile.application.Enum.RoomPriority;
import com.ages.volunteersmile.application.dto.CreateRoomDTO;
import com.ages.volunteersmile.application.dto.FeedbackDTO;
import com.ages.volunteersmile.application.dto.RoomAvailableDTO;
import com.ages.volunteersmile.application.dto.RoomDTO;
import com.ages.volunteersmile.domain.global.model.Room;
import com.ages.volunteersmile.domain.global.model.RoomFeedback;

public final class RoomDataMapper {

    private static final DateTimeFormatter BR_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RoomDataMapper() {}

    public static Room toEntity(CreateRoomDTO req) {
        Room r = new Room();
        r.setFloor(req.getFloor());
        r.setNumber(req.getNumber());
        r.setMaxOccupancy(req.getMaxOccupancy());
        r.setSector(req.getSector());
        r.setStatus(req.getStatus());
        r.setPriority(RoomPriority.valueOf(req.getPriority()));
        r.setDescription(req.getDescription());
        return r;
    }

    public static RoomDTO toResponse(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId().toString());
        dto.setFloor(room.getFloor());
        dto.setNumber(room.getNumber());
        dto.setMaxOccupancy(room.getMaxOccupancy());
        dto.setSector(room.getSector());
        dto.setStatus(room.getStatus().name());
        dto.setPriority(room.getPriority().name());
        dto.setDescription(room.getDescription());
        if (dto.getVisits() == null) {
            dto.setVisits(0);
        }
        dto.setFeedbacks(toFeedbackResponses(room.getFeedbacks()));
        return dto;
    }

    public static List<RoomDTO> toResponses(List<Room> rooms) {
        return rooms.stream().map(RoomDataMapper::toResponse).collect(Collectors.toList());
    }

    public static RoomAvailableDTO toAvailableResponse(Room room, Long daysSinceLastVisit) {
        RoomAvailableDTO dto = new RoomAvailableDTO();
        dto.setId(room.getId());
        dto.setNumber(room.getNumber());
        dto.setDaysSinceLastVisit(Objects.requireNonNullElse(daysSinceLastVisit, 0L));
        return dto;
    }

    private static List<FeedbackDTO> toFeedbackResponses(List<RoomFeedback> feedbacks) {
        if (feedbacks == null) return List.of();
        return feedbacks.stream().map(f -> {
            FeedbackDTO fr = new FeedbackDTO();
            fr.setId(f.getId() != null ? f.getId().toString() : null);
//            fr.setPhotoUrl(f.getPhotoUrl());
            fr.setUserName(f.getUserName());
            fr.setDate(f.getDate() != null ? f.getDate().format(BR_DATE) : null);
            fr.setFeedback(f.getFeedback());
            fr.setFloor(f.getFloor());
            fr.setRoomNumber(f.getRoomNumber());
            return fr;
        }).collect(Collectors.toList());
    }
}
