package com.periodTracker.service;

import com.periodTracker.dto.LoginRequestDTO;
import com.periodTracker.dto.LoginResponseDTO;
import com.periodTracker.entity.User;
import com.periodTracker.exceptions.AppExceptions;
import com.periodTracker.repository.UserRepository;
import com.periodTracker.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user=userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new AppExceptions(404,"Username not found"));
     if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
         throw new AppExceptions(404,"Invalid credentials");
     }

     String token= jwtUtil.generateToken(user.getUsername());
     LoginResponseDTO response=new LoginResponseDTO();
     response.setToken(token);
     return response;
    }
}
