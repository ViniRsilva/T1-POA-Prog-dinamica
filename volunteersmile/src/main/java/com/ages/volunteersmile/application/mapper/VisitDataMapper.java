package com.ages.volunteersmile.application.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import com.ages.volunteersmile.application.dto.CreateVisitDTO;
import com.ages.volunteersmile.application.dto.FeedbackDTO;
import com.ages.volunteersmile.application.dto.VisitDTO;
import com.ages.volunteersmile.application.dto.VisitWithHasFeedbackDTO;
import com.ages.volunteersmile.domain.global.model.Room;
import com.ages.volunteersmile.domain.global.model.User;
import com.ages.volunteersmile.domain.global.model.UserVisit;
import com.ages.volunteersmile.domain.global.model.Visit;
import com.ages.volunteersmile.domain.volunteer.model.Volunteer;


public final class VisitDataMapper {

    private VisitDataMapper() {}

    public static Visit toEntity(CreateVisitDTO dto, Room room) {
        Visit v = new Visit();
        v.setRoom(room);
        v.setStartDate(dto.getStartDate());
        v.setEndDate(dto.getEndDate());
        // scheduleDate é o dia selecionado para a marcação (preferir dto.scheduleDate)
        if (dto.getScheduleDate() != null) {
            v.setScheduleDate(dto.getScheduleDate());
        } else if (dto.getStartDate() != null) {
            v.setScheduleDate(dto.getStartDate().toLocalDate());
        }
        v.setDurationMinutes(dto.getDurationMinutes());
        v.setTotalTime(dto.getTotalTime());
        v.setNotes(dto.getNotes());
        v.setStatus(Visit.VisitStatus.SCHEDULED);
        return v;
    }

    public static VisitDTO toDto(Visit v) {
        VisitDTO dto = new VisitDTO();
        dto.setId(v.getId());
        dto.setRoomId(v.getRoom() != null ? v.getRoom().getId() : null);
        dto.setRoomNumber(v.getRoom() != null ? v.getRoom().getNumber() : null);
        dto.setStartDate(v.getStartDate());
        dto.setEndDate(v.getEndDate());
        dto.setScheduleDate(v.getScheduleDate());
        dto.setStatus(v.getStatus() != null ? v.getStatus().name() : null);
        dto.setDurationMinutes(v.getDurationMinutes());
        dto.setTotalTime(v.getTotalTime());
        dto.setNotes(v.getNotes());
        return dto;
    }

    public static VisitDTO toDtoWithVolunteer(Visit v, User volunteer) {
        VisitDTO dto = toDto(v);
        if (volunteer != null) {
            dto.setVolunteerId(volunteer.getId());
            dto.setVolunteerName(volunteer.getName());
        }
        return dto;
    }

    public static List<VisitDTO> toDtos(List<Visit> visits) {
        return visits.stream().map(VisitDataMapper::toDto).collect(Collectors.toList());
    }


    public static VisitWithHasFeedbackDTO  toDtoWithHasFeedback(Visit v, boolean hasFeedback, Volunteer volunteer) {

        VisitWithHasFeedbackDTO dto = new VisitWithHasFeedbackDTO();
        dto.setId(v.getId());
        dto.setVolunteerId(volunteer.getId());
        dto.setRoomId(v.getRoom() != null ? v.getRoom().getId() : null);
        dto.setRoomNumber(v.getRoom() != null ? v.getRoom().getNumber() : null);
        dto.setStartDate(v.getStartDate());
        dto.setEndDate(v.getEndDate());
        dto.setScheduleDate(v.getScheduleDate());
        dto.setStatus(v.getStatus() != null ? v.getStatus().name() : null);
        dto.setDurationMinutes(v.getDurationMinutes());
        dto.setTotalTime(v.getTotalTime());
        dto.setNotes(v.getNotes());
        dto.setHasFeedback(hasFeedback);
        return dto;
    }

    public static List<VisitWithHasFeedbackDTO> toListOfVisitWithHasFeedbackDTOList(
            List<Visit> visits,
            List<Boolean> has,
            Volunteer volunteer) {

        return IntStream.range(0, has.size())
                .mapToObj(i -> toDtoWithHasFeedback(visits.get(i), has.get(i), volunteer))
                .collect(Collectors.toList());
    }

    public static FeedbackDTO toFeedbackDto(UserVisit userVisit) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(userVisit.getId().toString());
        dto.setUserName(userVisit.getUser().getName());
        dto.setDate(userVisit.getVisit().getStartDate() != null ? userVisit.getVisit().getStartDate().toString() : null);
        dto.setFeedback(userVisit.getVolunteerFeedback());
        dto.setFloor(userVisit.getVisit().getRoom().getFloor());
        dto.setRoomNumber(userVisit.getVisit().getRoom().getNumber());
        return dto;
    }

}
