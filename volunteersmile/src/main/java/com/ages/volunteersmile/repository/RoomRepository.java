package com.ages.volunteersmile.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ages.volunteersmile.application.Enum.RoomPriority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ages.volunteersmile.application.Enum.RoomStatus;
import com.ages.volunteersmile.domain.global.model.Room;
import com.ages.volunteersmile.domain.global.model.Visit;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room>  {
    Optional<Room> findByNumber(Integer number);
    boolean existsByNumber(Integer number);

            @Query("SELECT r FROM Room r "
                + "WHERE r.status = :activeStatus "
                + "AND NOT EXISTS ("
                + "  SELECT 1 FROM Visit v "
                + "  WHERE v.room = r "
                + "    AND v.status = :scheduledStatus "
                + "    AND v.scheduleDate = :date"
                + ")")
            List<Room> findAvailableByDate(@Param("date") LocalDate date,
                                    @Param("activeStatus") RoomStatus activeStatus,
                                    @Param("scheduledStatus") Visit.VisitStatus scheduledStatus);


    @Query("SELECT r FROM Room r " +
            "WHERE (:floor IS NULL OR r.floor = :floor) " +
            "AND (:priority IS NULL OR r.priority = :priority) " +
            "ORDER BY CASE r.priority " +
            "WHEN 'LOW' THEN 1 " +
            "WHEN 'MEDIUM' THEN 2 " +
            "WHEN 'HIGH' THEN 3 END ASC")
    Page<Room> findAllByFloorAndPriorityOrderByPriority(
            @Param("floor") Integer floor,
            @Param("priority") RoomPriority priority,
            Pageable pageable
    );

}

