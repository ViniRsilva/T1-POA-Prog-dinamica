package com.ages.volunteersmile.application.service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ages.volunteersmile.application.dto.CreateVolunteerDTO;
import com.ages.volunteersmile.application.dto.UpdatePasswordDTO;
import com.ages.volunteersmile.application.dto.UpdateVolunteerDTO;
import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.application.mapper.VolunteerDataMapper;
import com.ages.volunteersmile.domain.adapters.ExceptionsAdapter;
import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
import com.ages.volunteersmile.repository.VolunteerRepository;

@Service
@Transactional(readOnly = true)
public class VolunteerApplicationServiceImpl implements VolunteerApplicationService {

    private final VolunteerRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ExceptionsAdapter exceptions;
    private final VolunteerDataMapper volunteerDataMapper;

    public VolunteerApplicationServiceImpl(VolunteerRepository repository,
                                           BCryptPasswordEncoder passwordEncoder,
                                           ExceptionsAdapter exceptions,
                                           VolunteerDataMapper volunteerDataMapper) {
        this.repository = repository;
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
    public List<VolunteerDTO> findAll() {
        return volunteerDataMapper.mapFrom(repository.findAll());
    }

    @Override
    public Page<VolunteerDTO> findAllPaginated(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return volunteerDataMapper.mapFrom(repository.findAll(pageable)) ;
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
