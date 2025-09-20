package com.ages.volunteersmile.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ages.volunteersmile.application.dto.CreateVisitDTO;
import com.ages.volunteersmile.application.dto.VisitDTO;
import com.ages.volunteersmile.application.mapper.VisitDataMapper;
import com.ages.volunteersmile.domain.adapters.ExceptionsAdapter;
import com.ages.volunteersmile.domain.global.model.Room;
import com.ages.volunteersmile.domain.global.model.User;
import com.ages.volunteersmile.domain.global.model.UserVisit;
import com.ages.volunteersmile.domain.global.model.Visit;
import com.ages.volunteersmile.repository.RoomRepository;
import com.ages.volunteersmile.repository.UserVisitRepository;
import com.ages.volunteersmile.repository.VisitRepository;
import com.ages.volunteersmile.repository.VolunteerRepository;

import jakarta.transaction.Transactional;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final RoomRepository roomRepository;
        private final VolunteerRepository volunteerRepository;
    private final UserVisitRepository userVisitRepository;
    private final ExceptionsAdapter exceptions;

    public VisitServiceImpl(VisitRepository visitRepository,
                            RoomRepository roomRepository,
                            VolunteerRepository volunteerRepository,
                            UserVisitRepository userVisitRepository,
                            ExceptionsAdapter exceptions) {
        this.visitRepository = visitRepository;
        this.roomRepository = roomRepository;
        this.volunteerRepository = volunteerRepository;
        this.userVisitRepository = userVisitRepository;
        this.exceptions = exceptions;
    }

    @Override
    @Transactional
    public VisitDTO createVisit(CreateVisitDTO dto) {
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> exceptions.notFound("Quarto não encontrado"));
        var volunteer = volunteerRepository.findById(dto.getVolunteerId())
                .orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));

        Visit entity = VisitDataMapper.toEntity(dto, room);
        Visit saved = visitRepository.save(entity);

        UserVisit uv = new UserVisit();
        uv.setVisit(saved);
        uv.setUser(volunteer);
        uv.setAttendanceConfirmed(Boolean.FALSE);
        userVisitRepository.save(uv);

        return VisitDataMapper.toDtoWithVolunteer(saved, volunteer);
    }

    @Override
    @Transactional
    public List<VisitDTO> listAll() {
        List<Visit> visits = visitRepository.findAll();
        // load volunteer association from UserVisit (assuming one volunteer per visit)
        List<UserVisit> userVisits = userVisitRepository.findByVisitIdIn(
                visits.stream().map(Visit::getId).collect(Collectors.toList())
        );
        Map<UUID, User> visitVolunteerMap = userVisits.stream()
                .collect(Collectors.toMap(uv -> uv.getVisit().getId(), uv -> uv.getUser(), (a,b)->a));
        return visits.stream()
                .map(v -> VisitDataMapper.toDtoWithVolunteer(v, visitVolunteerMap.get(v.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<VisitDTO> listByDay(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = date.plusDays(1).atStartOfDay();

        List<Visit> visits = visitRepository.findAllOverlapping(start, end);

        Map<UUID, User> visitVolunteerMap = visits.isEmpty()
                ? Collections.emptyMap()
                : userVisitRepository.findByVisitIdIn(
                        visits.stream().map(Visit::getId).toList()
                ).stream()
                .collect(Collectors.toMap(
                        uv -> uv.getVisit().getId(),
                        UserVisit::getUser,
                        (a, b) -> a
                ));

        final Map<UUID, User> finalMap = visitVolunteerMap;

        return visits.stream()
                .map(v -> VisitDataMapper.toDtoWithVolunteer(v, finalMap.get(v.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<VisitDTO> listByMonth(LocalDate anyDateInMonth) {
        LocalDate firstOfMonth     = anyDateInMonth.withDayOfMonth(1);
        LocalDate firstOfNextMonth = firstOfMonth.plusMonths(1);

        LocalDateTime start = firstOfMonth.atStartOfDay();
        LocalDateTime end   = firstOfNextMonth.atStartOfDay();

        List<Visit> visits = visitRepository.findAllOverlapping(start, end);

        Map<UUID, User> visitVolunteerMap = visits.isEmpty()
                ? Collections.emptyMap()
                : userVisitRepository.findByVisitIdIn(
                        visits.stream().map(Visit::getId).toList()
                ).stream()
                .collect(Collectors.toMap(
                        uv -> uv.getVisit().getId(),
                        UserVisit::getUser,
                        (a, b) -> a
                ));

        final Map<UUID, User> finalMap = visitVolunteerMap;

        return visits.stream()
                .map(v -> VisitDataMapper.toDtoWithVolunteer(v, finalMap.get(v.getId())))
                .collect(Collectors.toList());
    }
}
