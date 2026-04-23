package com.periodTracker.service;

import com.periodTracker.dto.LoginRequestDTO;
import com.periodTracker.dto.LoginResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);

}
