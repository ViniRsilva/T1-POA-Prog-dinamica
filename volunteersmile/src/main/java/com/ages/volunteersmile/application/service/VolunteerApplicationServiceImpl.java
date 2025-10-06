package com.ages.volunteersmile.application.service;

import com.ages.volunteersmile.application.dto.CreateVolunteerDTO;
import com.ages.volunteersmile.application.dto.UpdateVolunteerDTO;
import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.application.dto.VolunteerProfileDTO;
import com.ages.volunteersmile.application.dto.UpdatePasswordDTO;
import com.ages.volunteersmile.application.mapper.VolunteerDataMapper;
import com.ages.volunteersmile.domain.adapters.ExceptionsAdapter;
import com.ages.volunteersmile.domain.global.model.User;
import com.ages.volunteersmile.domain.global.model.UserRole;
import com.ages.volunteersmile.domain.global.model.UserVisit;
import com.ages.volunteersmile.domain.global.model.Visit;
import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
import com.ages.volunteersmile.repository.UserRepository;
import com.ages.volunteersmile.repository.UserVisitRepository;
import com.ages.volunteersmile.repository.VolunteerRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VolunteerApplicationServiceImpl implements VolunteerApplicationService {

    private final VolunteerRepository repository;
    private final UserVisitRepository userVisitRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ExceptionsAdapter exceptions;
    private final VolunteerDataMapper volunteerDataMapper;

    public VolunteerApplicationServiceImpl(VolunteerRepository repository, UserVisitRepository userVisitRepository, UserRepository userRepository,
                                           BCryptPasswordEncoder passwordEncoder,
                                           ExceptionsAdapter exceptions,
                                           VolunteerDataMapper volunteerDataMapper) {
        this.repository = repository;
        this.userVisitRepository = userVisitRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.exceptions = exceptions;
        this.volunteerDataMapper = volunteerDataMapper;
    }

    @Override
    @Transactional
    public VolunteerDTO createVolunteer(CreateVolunteerDTO request) {
        repository.findByEmail(request.getEmail()).ifPresent(existing -> {
            throw exceptions.conflict("Já existe um voluntário com este email");
        });

        String hashedPassword = passwordEncoder.encode(request.getPassword());
    Volunteer newVolunteer = volunteerDataMapper.mapToNewVolunteer(request, hashedPassword);

    repository.save(newVolunteer);
        return volunteerDataMapper.mapFrom(newVolunteer);
    }

    @Override
    public VolunteerDTO findByEmail(String email) {
        Volunteer volunteer = repository.findByEmail(email)
                .orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));
        return volunteerDataMapper.mapFrom(volunteer);
    }

    @Override
    public VolunteerDTO findById(UUID id) {
    Volunteer volunteer = repository.findById(id)
                .orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));
        return volunteerDataMapper.mapFrom(volunteer);
    }

    @Override
    public VolunteerProfileDTO getProfilebyID(UUID id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("Usuário não autenticado");
        }

        String loggedEmail = authentication.getName();

        User logged = userRepository.findByEmail(loggedEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean isAdmin = logged.getAppRole()== UserRole.ADMIN;

        if (!isAdmin && !logged.getId().equals(id)) {
            throw new RuntimeException("Acesso negado");
        }

        Volunteer volunteer = repository.findById(id).orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));

        List<Visit> visits = userVisitRepository.findByUserId(volunteer.getId())
                .stream()
                .map(UserVisit::getVisit)
                .toList();

        return volunteerDataMapper.mapToVolunteerProfileDTO(volunteer, visits);

    }

    @Override
    public List<VolunteerDTO> findAll() {
        return volunteerDataMapper.mapFrom(repository.findAll());
    }

    @Override
    @Transactional
    public VolunteerDTO updateVolunteer(UUID id, UpdateVolunteerDTO updateRequest) {
    Volunteer volunteer = repository.findById(id)
                .orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(volunteer.getEmail())) {
            repository.findByEmail(updateRequest.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw exceptions.conflict("Já existe um voluntário com este email");
                }
            });
        }

    volunteerDataMapper.updateFromDto(updateRequest, volunteer);

    repository.save(volunteer);
        return volunteerDataMapper.mapFrom(volunteer);
    }

    @Override
    @Transactional
    public void deleteVolunteerById(UUID id) {
    Volunteer volunteer = repository.findById(id)
        .orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));

    // Verifica se há visitas associadas via user_visit antes de permitir a exclusão lógica
    // (injeção do userVisitRepository exigiria refatorar construtor; por simplicidade considerar adicionar depois)
    // TODO: Injetar UserVisitRepository e checar existsByUser_Id(id) se regra de negócio exigir bloqueio.

    volunteer.setDeletedAt(LocalDateTime.now());
    volunteer.setStatus(com.ages.volunteersmile.application.Enum.UserStatus.INACTIVE);
    repository.save(volunteer); // Soft delete: não remove fisicamente para preservar histórico de visitas
    }

    @Override
    @Transactional
    public void updatePassword(UUID volunteerId, UpdatePasswordDTO passwordUpdateRequest) {
    Volunteer volunteer = repository.findById(volunteerId)
                .orElseThrow(() -> exceptions.notFound("Voluntário não encontrado com este ID"));

        if (!passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getConfirmNewPassword())) {
            throw exceptions.badRequest("Nova senha e confirmação devem ser iguais");
        }
        if (!passwordEncoder.matches(passwordUpdateRequest.getOldPassword(), volunteer.getPassword())) {
            throw exceptions.badRequest("Senha antiga incorreta");
        }
        volunteer.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        repository.save(volunteer);
    }
}
