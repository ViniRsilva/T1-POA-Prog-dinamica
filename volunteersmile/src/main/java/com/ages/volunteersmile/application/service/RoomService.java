package com.ages.volunteersmile.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.ages.volunteersmile.application.dto.CreateRoomDTO;
import com.ages.volunteersmile.application.dto.RoomAvailableDTO;
import com.ages.volunteersmile.application.dto.RoomDTO;
import org.springframework.data.domain.Page;

public interface RoomService {

    RoomDTO addRoom(CreateRoomDTO request);

    List<RoomDTO> listAll();

    RoomDTO getById(UUID id);

    RoomDTO updateRoom(UUID id, com.ages.volunteersmile.application.dto.UpdateRoomDTO request);

    void deleteRoom(UUID id);

    List<RoomAvailableDTO> listAvailableByDate(LocalDate date);

    Page<RoomDTO> listPage(int page, int size, String sortBy, String direction, Integer floor, String priority);
}