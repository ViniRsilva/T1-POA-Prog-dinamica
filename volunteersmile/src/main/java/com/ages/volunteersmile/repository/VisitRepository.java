package com.ages.volunteersmile.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ages.volunteersmile.domain.global.model.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, UUID> {
	boolean existsByRoom_Id(UUID roomId);
}
