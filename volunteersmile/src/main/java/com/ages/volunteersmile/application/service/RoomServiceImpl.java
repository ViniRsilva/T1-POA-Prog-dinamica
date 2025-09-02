package com.ages.volunteersmile.application.service;

import com.ages.volunteersmile.application.dto.CreateRoomDTO;
import com.ages.volunteersmile.application.dto.RoomDTO;
import com.ages.volunteersmile.application.mapper.RoomDataMapper;
import com.ages.volunteersmile.domain.global.model.Room;
import com.ages.volunteersmile.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    @Override
    public RoomDTO addRoom(CreateRoomDTO request) {
        if (roomRepository.existsByNumber(request.getNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Room entity = RoomDataMapper.toEntity(request);
        Room saved = roomRepository.save(entity);
        return RoomDataMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public List<RoomDTO> listAll() {
        return RoomDataMapper.toResponses(roomRepository.findAll());
    }

    @Transactional
    @Override
    public RoomDTO getById(UUID id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return RoomDataMapper.toResponse(room);
    }
}