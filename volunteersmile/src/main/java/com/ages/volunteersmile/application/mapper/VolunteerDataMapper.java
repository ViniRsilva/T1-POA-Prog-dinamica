package com.ages.volunteersmile.application.mapper;

import com.ages.volunteersmile.application.dto.RoomAccessLevelDTO;
import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.application.Enum.UserStatus;
import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VolunteerDataMapper {

    public VolunteerDataMapper() {
    }

    public VolunteerDTO mapFrom(Volunteer volunteer) {
        var volunteerDTO = new VolunteerDTO();
        volunteerDTO.setId(volunteer.getId());
        volunteerDTO.setName(volunteer.getName());
        volunteerDTO.setEmail(volunteer.getEmail());
        volunteerDTO.setPhoneNumber(volunteer.getPhoneNumber());
        volunteerDTO.setDescription(volunteer.getDescriptionVoluntary());

        if (volunteer.getStatus() != null) {
            volunteerDTO.setStatus(UserStatus.valueOf(volunteer.getStatus()));
        }

        if (volunteer.getRoomAccessLevel() != null) {
            var roomAccessLevelDTO = new RoomAccessLevelDTO();
            roomAccessLevelDTO.setRoomLevelAccessNumber(volunteer.getRoomAccessLevel());
            volunteerDTO.setRoomAccessLevel(roomAccessLevelDTO);
        }

        volunteerDTO.setCreatedAt(volunteer.getCreatedAt());
        return volunteerDTO;
    }

    public Volunteer mapTo(VolunteerDTO volunteerDTO, Volunteer volunteer) {
        volunteer.setName(volunteerDTO.getName());
        volunteer.setEmail(volunteerDTO.getEmail());
        volunteer.setPhoneNumber(volunteerDTO.getPhoneNumber());
        volunteer.setDescriptionVoluntary(volunteerDTO.getDescription());

        if (volunteerDTO.getStatus() != null) {
            volunteer.setStatus(volunteerDTO.getStatus().name());
        }

        if (volunteerDTO.getRoomAccessLevel() != null) {
            volunteer.setRoomAccessLevel(volunteerDTO.getRoomAccessLevel().getRoomLevelAccessNumber());
        }

        return volunteer;
    }

    public List<VolunteerDTO> mapFrom(List<Volunteer> volunteers) {
        return volunteers.stream()
                .map(this::mapFrom)
                .toList();
    }
}
