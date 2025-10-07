package com.ages.volunteersmile.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
import com.ages.volunteersmile.application.dto.*;
import org.springframework.stereotype.Service;

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
    public VisitDTO getNextVisitByVolunteer(UUID volunteerId) {
        User volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));

        UserVisit nextUserVisit = userVisitRepository
                .findFirstByUser_IdAndVisit_StartDateAfterOrderByVisit_StartDateAsc(
                        volunteerId, java.time.LocalDateTime.now())
                .orElseThrow(() -> exceptions.notFound("Nenhuma próxima visita encontrada para o voluntário"));

        return VisitDataMapper.toDtoWithVolunteer(nextUserVisit.getVisit(), volunteer);
    }

    @Override
    @Transactional
    public List<VisitDTO> listByDay(LocalDate date) {
//        LocalDateTime start = date.atStartOfDay();
//        LocalDateTime end   = date.plusDays(1).atStartOfDay();

        List<Visit> visits = visitRepository.findAllOverlapping(date, date.plusDays(1));

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
    public List<VisitMonthDTO> listByMonth(LocalDate anyDateInMonth) {
        LocalDate firstOfMonth     = anyDateInMonth.withDayOfMonth(1);
        LocalDate firstOfNextMonth = firstOfMonth.plusMonths(1);

        List<Visit> visits = visitRepository.findAllOverlapping(firstOfMonth, firstOfNextMonth);

        return visits.stream()
                .map(v -> {
                    VisitMonthDTO dto = new VisitMonthDTO();
                    dto.setRoomNumber(v.getRoom().getNumber());
                    dto.setFloor(v.getRoom().getFloor());
                    dto.setScheduleDate(v.getScheduleDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VisitTimeDTO endVisitById(UUID visitId) {

        Visit visit = visitRepository.findVisitById(visitId);
        if(visit.getEndDate() != null){

            throw exceptions.conflict("visita ja finalizada");

        }
        List<UUID> uv = userVisitRepository.findAllUserIdsByVisitId(visitId);
        List<Volunteer> volunteer = uv.stream()
                .map(volunteerRepository::findById)
                .flatMap(Optional::stream)
                .toList();

        LocalDateTime endTime = LocalDateTime.now();
        visit.setEndDate(endTime);

        Integer durationMinutes = java.time.Duration.between(visit.getStartDate(), endTime).toMinutesPart();
        visit.setDurationMinutes(durationMinutes);

        volunteer.forEach(v -> v.setTotaltime(v.getTotaltime() + durationMinutes));
        volunteerRepository.saveAll(volunteer);
        visitRepository.save(visit);

        String formatted = String.format("%dh %02dmin", durationMinutes / 60, durationMinutes % 60);
        return new VisitTimeDTO(durationMinutes, formatted);
    }

    @Override
    @Transactional
    public VisitDTO startVisitById(UUID visitId) {
        Visit visit = visitRepository.findVisitById(visitId);

        LocalDateTime startTime = LocalDateTime.now();
        visit.setStartDate(startTime);

        visitRepository.save(visit);

        return VisitDataMapper.toDto(visit);
    }

    @Override
    public FeedbackDTO addVolunteerFeedback(UserVisitFeedbackDTO dto) {
        UserVisit userVisit = userVisitRepository.findById(dto.getUserVisitId())
            .orElseThrow(() -> exceptions.notFound("UserVisit não encontrado"));
        userVisit.setVolunteerFeedback(dto.getFeedback());
        userVisitRepository.save(userVisit);
        return VisitDataMapper.toFeedbackDto(userVisit);
    }

    @Override
    public FeedbackDTO getLastVolunteerFeedback(UUID roomId) {
        UserVisit userVisit = userVisitRepository.findTopByVisit_Room_IdAndVolunteerFeedbackIsNotNullOrderByVisit_StartDateDesc(roomId);
        if (userVisit == null) {
            return null;
        }
        return VisitDataMapper.toFeedbackDto(userVisit);
    }

    @Override
    public List<FeedbackDTO> getAllFeedbacksByRoom(UUID roomId) {
        List<UserVisit> userVisits = userVisitRepository.findAllByVisit_Room_IdAndVolunteerFeedbackIsNotNull(roomId);
        return userVisits.stream()
                .map(VisitDataMapper::toFeedbackDto)
                .toList();
    }

}
