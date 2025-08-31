package com.ages.volunteersmile.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ages.volunteersmile.application.dto.LoginRequestDTO;
import com.ages.volunteersmile.application.dto.LoginResponseDTO;
import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
import com.ages.volunteersmile.repository.VolunteerRepository;

@Service
public class AuthService {
    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponseDTO login(LoginRequestDTO request) {
        Optional<Volunteer> volunteerOpt = volunteerRepository.findByEmail(request.getEmail());
        if (volunteerOpt.isEmpty()) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
        Volunteer volunteer = volunteerOpt.get();
        boolean match = passwordEncoder.matches(request.getPassword(), volunteer.getPassword());
        if (!match) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
        String token = jwtUtil.generateToken(volunteer.getEmail());
        return new LoginResponseDTO(token, volunteer.getName(), volunteer.getEmail());
    }
}