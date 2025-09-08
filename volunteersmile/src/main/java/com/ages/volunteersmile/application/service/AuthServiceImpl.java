package com.ages.volunteersmile.application.service;

import java.util.Optional;

import com.ages.volunteersmile.application.dto.UserDTO;
import com.ages.volunteersmile.application.mapper.UserDataMapper;
import com.ages.volunteersmile.domain.global.model.User;
import com.ages.volunteersmile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ages.volunteersmile.application.dto.LoginRequestDTO;
import com.ages.volunteersmile.application.dto.LoginResponseDTO;
import com.ages.volunteersmile.utils.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserDataMapper userDataMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
        
        UserDTO userDTO = userDataMapper.mapFrom(userOpt.get());
        String token = jwtUtil.generateToken(userDTO);
        return new LoginResponseDTO(
            token, userDTO.getName(), userDTO.getEmail()
        );
    }
}