package com.ages.volunteersmile.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ages.volunteersmile.application.Enum.RoomStatus;
import com.ages.volunteersmile.domain.global.model.Room;
import com.ages.volunteersmile.domain.global.model.Visit;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findByNumber(Integer number);
    boolean existsByNumber(Integer number);

            @Query("SELECT r FROM Room r "
                + "WHERE r.status = :activeStatus "
                + "AND NOT EXISTS ("
                + "  SELECT 1 FROM Visit v "
                + "  WHERE v.room = r "
                + "    AND v.status = :scheduledStatus "
                + "    AND ("
                + "         (v.endDate IS NULL AND v.startDate = :date) "
                + "      OR (v.endDate IS NOT NULL AND v.startDate <= :date AND v.endDate >= :date)"
                + "    )"
                + ")")
            List<Room> findAvailableByDate(@Param("date") LocalDate date,
                                    @Param("activeStatus") RoomStatus activeStatus,
                                    @Param("scheduledStatus") Visit.VisitStatus scheduledStatus);
}

