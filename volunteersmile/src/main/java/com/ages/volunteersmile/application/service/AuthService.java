package com.ages.volunteersmile.application.service;

import com.ages.volunteersmile.application.dto.LoginRequestDTO;
import com.ages.volunteersmile.application.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
}