package com.periodTracker.controller;

import com.periodTracker.dto.UserSignupRequestDTO;
import com.periodTracker.dto.UserSignupResponseDTO;
import com.periodTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/api/user")
public class UserSignupController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDTO>signup(@RequestBody UserSignupRequestDTO request){
        return ResponseEntity.ok(userService.signup(request));
    }
}
