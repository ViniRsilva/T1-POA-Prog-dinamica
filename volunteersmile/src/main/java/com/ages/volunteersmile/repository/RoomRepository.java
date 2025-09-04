package com.ages.volunteersmile.repository;

import com.ages.volunteersmile.domain.global.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findByNumber(Integer number);
    boolean existsByNumber(Integer number);
}
