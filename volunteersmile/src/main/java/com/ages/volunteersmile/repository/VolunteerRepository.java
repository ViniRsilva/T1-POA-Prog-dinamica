package com.ages.volunteersmile.repository;

import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VolunteerRepository extends JpaRepository<Volunteer, UUID> {

    @Query("SELECT v FROM Volunteer v WHERE v.email = :email AND v.deletedAt IS NULL")
    Optional<Volunteer> findByEmail(@Param("email") String email);

    @Query("SELECT v FROM Volunteer v WHERE v.deletedAt IS NULL")
    List<Volunteer> findAllActive();

    @Query("SELECT v FROM Volunteer v WHERE v.id = :id AND v.deletedAt IS NULL")
    Optional<Volunteer> findByIdAndNotDeleted(@Param("id") UUID id);
}