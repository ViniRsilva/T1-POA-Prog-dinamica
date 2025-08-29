package com.ages.volunteersmile.application.service;

import com.ages.volunteersmile.application.Enum.UserStatus;
import com.ages.volunteersmile.application.dto.CreateVolunteerDTO;
import com.ages.volunteersmile.application.dto.UpdatePasswordDTO;
import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.application.mapper.VolunteerDataMapper;
import com.ages.volunteersmile.domain.adapters.ExceptionsAdapter;
import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
import com.ages.volunteersmile.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserApplicationService {

    private final VolunteerRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ExceptionsAdapter exceptions;
    private final VolunteerDataMapper volunteerDataMapper;

    @Autowired
    public UserApplicationService(VolunteerRepository repository, BCryptPasswordEncoder passwordEncoder, ExceptionsAdapter exceptions, VolunteerDataMapper volunteerDataMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.exceptions = exceptions;
        this.volunteerDataMapper = volunteerDataMapper;
    }

    public VolunteerDTO createVolunteer(CreateVolunteerDTO body) {
        Volunteer existingVolunteer = repository.findByEmail(body.getEmail()).orElse(null);
        if (existingVolunteer != null) {
            throw exceptions.conflict("Já existe um voluntário com este email");
        }

        UserStatus status = body.getStatus() != null ?
                UserStatus.valueOf(body.getStatus()) : UserStatus.valueOf("PENDING");

        String hashedPassword = passwordEncoder.encode(body.getPassword());

        Volunteer volunteer = new Volunteer();
        volunteer.setEmail(body.getEmail());
        volunteer.setPassword(hashedPassword);
        volunteer.setName(body.getName());
        volunteer.setPhoneNumber(body.getPhoneNumber());
        volunteer.setStatus(String.valueOf(status));
        volunteer.setRoomAccessLevel(body.getRoomAccessLevel());
        volunteer.setDescriptionVoluntary(body.getDescriptionVoluntary());
        volunteer.setCreatedAt(LocalDateTime.now());

        repository.saveAndFlush(volunteer);
        return volunteerDataMapper.mapFrom(volunteer);

    }

    public VolunteerDTO findByEmail(String email) {
        Volunteer v = repository.findByEmail(email).orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));
        return volunteerDataMapper.mapFrom(v);
    }

    public VolunteerDTO findById(UUID id) {
        Volunteer v = repository.findByIdAndNotDeleted(id).orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));
        return volunteerDataMapper.mapFrom(v);
    }

    public List<VolunteerDTO> findAll() {
        List<Volunteer> v = repository.findAllActive();
        return volunteerDataMapper.mapFrom(v);
    }

    public VolunteerDTO updateVolunteer(UUID id, VolunteerDTO dto) {
        Volunteer volunteer = repository.findByIdAndNotDeleted(id).orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));

        if (dto.getEmail() != null && !dto.getEmail().equals(volunteer.getEmail())) {
            Volunteer existingVolunteer = repository.findByEmail(dto.getEmail()).orElse(null);
            if (existingVolunteer != null && !existingVolunteer.getId().equals(id)) {
                throw exceptions.conflict("Já existe um voluntário com este email");
            }
        }

        if(dto.getName() != null && !dto.getName().equals(volunteer.getName())) {
            volunteer.setName(dto.getName());
        }

        if(dto.getEmail() != null && !dto.getEmail().equals(volunteer.getEmail())) {
            volunteer.setEmail(dto.getEmail());
        }

        if(dto.getPhoneNumber() != null && !dto.getPhoneNumber().equals(volunteer.getPhoneNumber())) {
            volunteer.setPhoneNumber(dto.getPhoneNumber());
        }

        if(dto.getStatus() != null) {
            volunteer.setStatus(dto.getStatus().name());
        }

        if (dto.getRoomAccessLevel() != null) {
            volunteer.setRoomAccessLevel(dto.getRoomAccessLevel().getRoomLevelAccessNumber());
        }
        if (dto.getDescription() != null) {
            volunteer.setDescriptionVoluntary(dto.getDescription());
        }

        Volunteer v = repository.saveAndFlush(volunteer);
        return  volunteerDataMapper.mapFrom(v);
    }

    public void deleteVolunteer(UUID id) {
        Volunteer volunteer = repository.findByIdAndNotDeleted(id).orElseThrow(() -> exceptions.notFound("Voluntário não encontrado"));
        volunteer.setDeletedAt(LocalDateTime.now());
        repository.save(volunteer);
    }

    public void updatePassword(UUID volunteerId, UpdatePasswordDTO dto) {
        Volunteer volunteer = repository.findByIdAndNotDeleted(volunteerId).orElseThrow(() -> exceptions.notFound("Voluntário não encontrado com este ID"));

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw exceptions.badRequest("Nova senha e confirmação devem ser iguais");
        }

        boolean oldPasswordIsEqual = passwordEncoder.matches(dto.getOldPassword(), volunteer.getPassword());
        if (!oldPasswordIsEqual) {
            throw exceptions.badRequest("Senha antiga incorreta");
        }

        String newPasswordHashed = passwordEncoder.encode(dto.getNewPassword());
        volunteer.setPassword(newPasswordHashed);
        repository.save(volunteer);
    }
}
