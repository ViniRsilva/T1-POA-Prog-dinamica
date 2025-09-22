package com.ages.volunteersmile.application.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ages.volunteersmile.application.Enum.RoomPriority;
import com.ages.volunteersmile.application.Enum.RoomStatus;
import com.ages.volunteersmile.application.dto.CreateRoomDTO;
import com.ages.volunteersmile.application.dto.RoomAvailableDTO;
import com.ages.volunteersmile.application.dto.RoomDTO;
import com.ages.volunteersmile.application.dto.UpdateRoomDTO;
import com.ages.volunteersmile.application.mapper.RoomDataMapper;
import com.ages.volunteersmile.domain.global.model.Room;
import com.ages.volunteersmile.domain.global.model.Visit;
import com.ages.volunteersmile.repository.RoomRepository;
import com.ages.volunteersmile.repository.VisitRepository;
import com.ages.volunteersmile.repository.VolunteerRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final VisitRepository visitRepository;
    private final VolunteerRepository volunteerRepository;

    public RoomServiceImpl(RoomRepository roomRepository, VisitRepository visitRepository, VolunteerRepository volunteerRepository) {
        this.roomRepository = roomRepository;
        this.visitRepository = visitRepository;
        this.volunteerRepository = volunteerRepository;
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
    @Transactional
    @Override
    public RoomDTO updateRoom(UUID id, UpdateRoomDTO request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        room.setFloor(request.getFloor());
        room.setNumber(request.getNumber());
        room.setMaxOccupancy(request.getMaxOccupancy());
        room.setSector(request.getSector());
        room.setStatus(request.getStatus());
        room.setPriority(request.getPriority());
        room.setDescription(request.getDescription());
        Room saved = roomRepository.save(room);
        return RoomDataMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public void deleteRoom(UUID id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        roomRepository.delete(room);
    }

    @Transactional
    @Override
    public List<RoomAvailableDTO> listAvailableByDate(LocalDate date) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth.getPrincipal() == null || !auth.isAuthenticated()) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
    }
            String email = auth.getName(); // principal is the email (see JwtFilter)
            if (email == null || "anonymousUser".equalsIgnoreCase(email)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado");
            }
    var volunteer = volunteerRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas voluntários podem acessar"));
    int accessLevel = volunteer.getRoomAccessLevel() != null ? volunteer.getRoomAccessLevel() : 0;

    List<Room> rooms = roomRepository.findAvailableByDate(date, RoomStatus.ACTIVE, Visit.VisitStatus.SCHEDULED)
        .stream()
        .filter(room -> room.getFloor() != null && room.getFloor() <= accessLevel)
        .collect(Collectors.toList());
    List<UUID> roomIds = rooms.stream().map(Room::getId).collect(Collectors.toList());
    Map<UUID, LocalDate> lastVisitDateByRoom = visitRepository
        .findLastVisitDateByRoomIdsUpTo(roomIds, date)
        .stream()
        .collect(Collectors.toMap(
            row -> (UUID) row[0],
            row -> (LocalDate) row[1]
        ));

    Comparator<Room> byPriorityDesc = Comparator.comparing(
        room -> room.getPriority() == null ? RoomPriority.LOW : room.getPriority(),
        Comparator.reverseOrder()
    );

    Comparator<Room> byDaysSinceLastVisitDesc = Comparator.comparing(
        room -> {
            LocalDate last = lastVisitDateByRoom.get(room.getId());
            if (last == null) return Long.MAX_VALUE;
            return ChronoUnit.DAYS.between(last, date);
        },
        Comparator.reverseOrder()
    );

    Comparator<Room> byNumberAsc = Comparator.comparing(Room::getNumber, Comparator.nullsLast(Comparator.naturalOrder()));

        return rooms.stream()
                .sorted(byPriorityDesc.thenComparing(byDaysSinceLastVisitDesc).thenComparing(byNumberAsc))
                .map(room -> {
                    LocalDate last = lastVisitDateByRoom.get(room.getId());
                    Long daysSince = (last == null) ? null : ChronoUnit.DAYS.between(last, date);
                    return RoomDataMapper.toAvailableResponse(room, daysSince);
                })
                .collect(Collectors.toList());
    }
}