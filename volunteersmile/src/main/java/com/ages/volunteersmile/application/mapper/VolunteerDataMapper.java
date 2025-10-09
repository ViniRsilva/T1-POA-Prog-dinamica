package com.ages.volunteersmile.application.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ages.volunteersmile.application.Enum.UserStatus;
import com.ages.volunteersmile.application.dto.RoomAccessLevelDTO;
import com.ages.volunteersmile.application.dto.UpdateVolunteerDTO;
import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.domain.volunteer.model.Volunteer;

@Component
public class VolunteerDataMapper {

    public VolunteerDataMapper() {
    }

    public VolunteerDTO mapFrom(Volunteer volunteer) {
        VolunteerDTO dto = new VolunteerDTO();
        dto.setId(volunteer.getId());
        dto.setName(volunteer.getName());
        dto.setEmail(volunteer.getEmail());
        dto.setPhoneNumber(volunteer.getPhoneNumber());
        dto.setDescription(volunteer.getDescriptionVoluntary());
        mapStatusToDto(volunteer, dto);
        mapRoomAccessToDto(volunteer, dto);
    dto.setRole(volunteer.getAppRole());
        dto.setCreatedAt(volunteer.getCreatedAt());
        return dto;
    }

    public Volunteer mapToNewVolunteer(com.ages.volunteersmile.application.dto.CreateVolunteerDTO dto,
                                       String hashedPassword) {
        Volunteer volunteer = new Volunteer();
        volunteer.setEmail(dto.getEmail());
        volunteer.setPassword(hashedPassword);
        volunteer.setName(dto.getName());
        volunteer.setPhoneNumber(dto.getPhoneNumber());
    volunteer.setStatus(UserStatus.PENDING);
        volunteer.setDescriptionVoluntary(dto.getDescriptionVoluntary());
        volunteer.setCreatedAt(LocalDateTime.now());
        return volunteer;
    }


    public void updateFromDto(UpdateVolunteerDTO dto, Volunteer volunteer) {
        if (dto == null) return;
        applyIfChanged(dto.getName(), volunteer.getName(), volunteer::setName);
        applyIfChanged(dto.getEmail(), volunteer.getEmail(), volunteer::setEmail);
        applyIfChanged(dto.getPhoneNumber(), volunteer.getPhoneNumber(), volunteer::setPhoneNumber);
        applyIfChanged(dto.getDescription(), volunteer.getDescriptionVoluntary(), volunteer::setDescriptionVoluntary);
        if (dto.getStatus() != null) {
            volunteer.setStatus(dto.getStatus());
        }
        if (dto.getRoomAccessLevel() != null) {
            volunteer.setRoomAccessLevel(dto.getRoomAccessLevel().getRoomLevelAccessNumber());
        }
    }

    public List<VolunteerDTO> mapFrom(List<Volunteer> volunteers) {
        return volunteers.stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }

    private void mapStatusToDto(Volunteer volunteer, VolunteerDTO dto) {
        if (volunteer.getStatus() != null) {
            dto.setStatus(volunteer.getStatus());
        }
    }

    private void mapRoomAccessToDto(Volunteer volunteer, VolunteerDTO dto) {
        if (volunteer.getRoomAccessLevel() != null) {
            RoomAccessLevelDTO rl = new RoomAccessLevelDTO();
            rl.setRoomLevelAccessNumber(volunteer.getRoomAccessLevel());
            dto.setRoomAccessLevel(rl);
        }
    }

    private <T> void applyIfChanged(T newValue, T currentValue, java.util.function.Consumer<T> setter) {
        if (newValue != null && !Objects.equals(newValue, currentValue)) {
            setter.accept(newValue);
        }
    }

    public Page<VolunteerDTO> mapFrom(Page<Volunteer> all) {
        return all.map(this::mapFrom);
    }
}
