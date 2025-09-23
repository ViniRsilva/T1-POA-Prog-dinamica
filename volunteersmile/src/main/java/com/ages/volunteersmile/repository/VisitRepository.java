package com.ages.volunteersmile.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ages.volunteersmile.application.dto.VisitDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ages.volunteersmile.domain.global.model.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, UUID> {
	boolean existsByRoom_Id(UUID roomId);

	@Query("SELECT v.room.id, MAX(COALESCE(v.endDate, v.startDate)) "
		 + "FROM Visit v "
		 + "WHERE v.room.id IN :roomIds AND COALESCE(v.endDate, v.startDate) <= :targetDate "
		 + "GROUP BY v.room.id")
	List<Object[]> findLastVisitDateByRoomIdsUpTo(@Param("roomIds") List<UUID> roomIds,
												  @Param("targetDate") LocalDate targetDate);

	@Query("""
       SELECT v
       FROM Visit v
       WHERE v.startDate < :end
         AND (v.endDate IS NULL OR v.endDate >= :start)
       """)
	List<Visit> findAllOverlapping(@Param("start") LocalDate start,
								   @Param("end")   LocalDate end);

    @Query("""
       SELECT v
       FROM Visit v
       WHERE v.id = :id
       """)
    Visit findVisitById(@Param("id") UUID visitId);

}
