package com.periodTracker.controller;

import com.periodTracker.dto.LoginRequestDTO;
import com.periodTracker.dto.LoginResponseDTO;
import com.periodTracker.dto.UserSignupRequestDTO;
import com.periodTracker.dto.UserSignupResponseDTO;
import com.periodTracker.service.AuthService;
import com.periodTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/api/user")
@CrossOrigin("*")
public class UserSignupController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDTO>signup(@RequestBody UserSignupRequestDTO request){
        return ResponseEntity.ok(userService.signup(request));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
