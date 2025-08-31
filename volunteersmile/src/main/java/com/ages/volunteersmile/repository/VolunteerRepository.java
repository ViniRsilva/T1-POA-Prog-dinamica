package com.ages.volunteersmile.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ages.volunteersmile.domain.volunteer.model.Volunteer;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, UUID> {

    Optional<Volunteer> findById(UUID id);
    
    Optional<Volunteer> findByEmail(String email);

    List<Volunteer> findAll();
}