package com.ages.volunteersmile.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
public interface VolunteerRepository {
    Optional<Volunteer> findByEmail(String email);
    List<Volunteer> findAllActive();
    Optional<Volunteer> findActiveById(UUID id);
    Volunteer save(Volunteer volunteer);
}