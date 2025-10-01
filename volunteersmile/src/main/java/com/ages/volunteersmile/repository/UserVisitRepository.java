package com.ages.volunteersmile.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ages.volunteersmile.domain.global.model.UserVisit;

@Repository
public interface UserVisitRepository extends JpaRepository<UserVisit, UUID> {

    @Query("SELECT uv FROM UserVisit uv WHERE uv.visit.id IN :ids")
    List<UserVisit> findByVisitIdIn(@Param("ids") List<UUID> visitIds);

    UserVisit findFirstByVisit_Id(UUID visitId);

    List<UserVisit> findByUserId(UUID id_user);

    boolean existsByUser_Id(UUID userId);

    Optional<UserVisit> findFirstByUser_IdAndVisit_StartDateAfterOrderByVisit_StartDateAsc(UUID userId, LocalDateTime now);
}
