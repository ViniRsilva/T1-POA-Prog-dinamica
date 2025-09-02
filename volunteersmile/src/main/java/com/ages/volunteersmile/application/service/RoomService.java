package com.ages.volunteersmile.application.service;

import com.ages.volunteersmile.application.dto.CreateRoomDTO;
import com.ages.volunteersmile.application.dto.RoomDTO;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    RoomDTO addRoom(CreateRoomDTO request);

    List<RoomDTO> listAll();

    RoomDTO getById(UUID id);
}