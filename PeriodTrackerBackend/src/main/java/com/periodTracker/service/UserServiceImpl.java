package com.periodTracker.service;

import com.periodTracker.dto.UserSignupResponseDTO;
import com.periodTracker.dto.UserSignupRequestDTO;
import com.periodTracker.entity.Profile;
import com.periodTracker.entity.User;
import com.periodTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserSignupResponseDTO signup(UserSignupRequestDTO request) {

        // 1. Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // later hash it

        // 2. Create Profile
        Profile profile = new Profile();
        profile.setUser(user);

        profile.setAge(request.getAge());
        profile.setHeight(request.getHeight());
        profile.setWeight(request.getWeight());

        profile.setCycleLength(
                request.getCycleLength() != null ? request.getCycleLength() : 28
        );

        profile.setPeriodDuration(
                request.getPeriodDuration() != null ? request.getPeriodDuration() : 5
        );

        user.setProfile(profile);

        // 3. Save
        User savedUser = userRepository.save(user);

        // 4. Build Response
        UserSignupResponseDTO response = new UserSignupResponseDTO();
        response.setUserId(savedUser.getUserId());
        response.setUsername(savedUser.getUsername());
        response.setCycleLength(profile.getCycleLength());
        response.setPeriodDuration(profile.getPeriodDuration());
        return response;
    }
}
