package com.ages.volunteersmile.application.service;

import java.util.List;
import java.util.UUID;

import com.ages.volunteersmile.application.dto.*;

public interface VolunteerApplicationService {
    VolunteerDTO createVolunteer(CreateVolunteerDTO body);
    VolunteerDTO findByEmail(String email);
    VolunteerDTO findById(UUID id);
    VolunteerProfileDTO getProfilebyID(UUID id);
    List<VolunteerDTO> findAll();
    VolunteerDTO updateVolunteer(UUID id, UpdateVolunteerDTO dto);
    void deleteVolunteerById(UUID id);
    void updatePassword(UUID volunteerId, UpdatePasswordDTO dto);
}
