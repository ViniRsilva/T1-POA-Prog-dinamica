package com.ages.volunteersmile.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ages.volunteersmile.application.dto.LoginRequestDTO;
import com.ages.volunteersmile.application.dto.LoginResponseDTO;
import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.application.mapper.VolunteerDataMapper;
import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
import com.ages.volunteersmile.repository.VolunteerRepository;
import com.ages.volunteersmile.utils.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private VolunteerRepository volunteerRepository;
    
    @Autowired
    private VolunteerDataMapper volunteerDataMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Optional<Volunteer> volunteerOpt = volunteerRepository.findByEmail(request.getEmail());
        if (volunteerOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), volunteerOpt.get().getPassword())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
        
        VolunteerDTO volunteerDTO = volunteerDataMapper.mapFrom(volunteerOpt.get());
        String token = jwtUtil.generateToken(volunteerDTO);
        return new LoginResponseDTO(
            token,
            volunteerDTO.getName(),
            volunteerDTO.getEmail()
        );
    }
}