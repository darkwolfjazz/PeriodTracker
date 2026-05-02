package com.periodTracker.service;

import com.periodTracker.dto.UserSignupResponseDTO;
import com.periodTracker.dto.UserSignupRequestDTO;
import com.periodTracker.entity.Profile;
import com.periodTracker.entity.User;
import com.periodTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserSignupResponseDTO signup(UserSignupRequestDTO request) {
        // 1. Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // later hash it
        // Create profile
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setAge(request.getAge());
        profile.setHeight(request.getHeight());
        profile.setWeight(request.getWeight());
        user.setProfile(profile);
        // Save
        User savedUser = userRepository.save(user);
        // Response
        UserSignupResponseDTO response =
                new UserSignupResponseDTO();
        response.setUserId(savedUser.getUserId());
        response.setUsername(savedUser.getUsername());
        return response;
    }
}
