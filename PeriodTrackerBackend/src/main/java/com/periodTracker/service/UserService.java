package com.periodTracker.service;

import com.periodTracker.dto.UserSignupResponseDTO;
import com.periodTracker.dto.UserSignupRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserSignupResponseDTO signup(UserSignupRequestDTO request);
}
