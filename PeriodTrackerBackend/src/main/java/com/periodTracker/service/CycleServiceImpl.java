package com.periodTracker.service;

import com.periodTracker.dto.CycleRequestDTO;
import com.periodTracker.dto.CycleResponseDTO;
import com.periodTracker.dto.FullCycleResponse;
import com.periodTracker.dto.PhaseRecommendationDTO;
import com.periodTracker.entity.Cycle;
import com.periodTracker.entity.Profile;
import com.periodTracker.entity.User;
import com.periodTracker.repository.CycleRepository;
import com.periodTracker.repository.ProfileRepository;
import com.periodTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CycleServiceImpl implements CycleService {

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private ProfileRepository profileRepository;

     @Autowired
     private CycleRepository cycleRepository;

    @Override
    public FullCycleResponse createCycle(CycleRequestDTO request) {
        //fetching user
        User user=userRepository.
                findById(request.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
        //fetching profile
        Profile profile=profileRepository
                .findByUserUserId(user.getUserId()).orElseThrow(()->new RuntimeException("Profile not found"));
        int cycleLength = request.getCycleLength() != null
                ? request.getCycleLength()
                : profile.getCycleLength() != 0
                ? profile.getCycleLength()
                : 28;

        int periodDuration = request.getPeriodDuration() != null
                ? request.getPeriodDuration()
                : profile.getPeriodDuration() != 0
                ? profile.getPeriodDuration()
                : 5;

        LocalDate startDate=request.getLastPeriodDate();
       //dates calculation
        LocalDate periodEnd = startDate.plusDays(periodDuration - 1);
        LocalDate ovulationDate = startDate.plusDays(cycleLength - 14);
        LocalDate nextPeriodDate = startDate.plusDays(cycleLength);

        //detect phases
        String currentPhase = getCurrentPhase(startDate, cycleLength, periodDuration);
        // 6. Save Cycle
        Cycle cycle = new Cycle();
        cycle.setUser(user);
        cycle.setStartDate(startDate);
        cycle.setEndDate(periodEnd);
        cycle.setCycleLength(cycleLength);
        cycle.setPeriodDuration(periodDuration);
        cycle.setOvulationDate(ovulationDate);
        cycle.setNextPeriodDate(nextPeriodDate);

        cycleRepository.save(cycle);

        // 7. Build response
        CycleResponseDTO cycleResponse = new CycleResponseDTO();
        cycleResponse.setPeriodStart(startDate);
        cycleResponse.setPeriodEnd(periodEnd);
        cycleResponse.setOvulationDate(ovulationDate);
        cycleResponse.setNextPeriodDate(nextPeriodDate);
        cycleResponse.setCurrentPhase(currentPhase);

        PhaseRecommendationDTO recommendation=getRecommendation(currentPhase);
        FullCycleResponse response = new FullCycleResponse();
        response.setCycle(cycleResponse);
        response.setRecommendation(recommendation);
        return response;
    }
    //phase logic
    private String getCurrentPhase(LocalDate startDate, int cycleLength, int periodDuration) {
        long days = ChronoUnit.DAYS.between(startDate, LocalDate.now());
        int day = (int) (days % cycleLength);
        if (day < periodDuration) return "Menstrual";
        else if (day < (cycleLength - 14)) return "Follicular";
        else if (day == (cycleLength - 14)) return "Ovulation";
        else return "Luteal";
    }
    //recommendation MVP version
    private PhaseRecommendationDTO getRecommendation(String phase) {
        PhaseRecommendationDTO dto = new PhaseRecommendationDTO();
        dto.setPhase(phase);
        switch (phase) {
            case "Menstrual":
                dto.setWorkouts(List.of("Yoga", "Light walking"));
                dto.setDiet(List.of("Iron rich food", "Spinach", "Dates"));
                break;

            case "Follicular":
                dto.setWorkouts(List.of("Strength training", "HIIT"));
                dto.setDiet(List.of("Protein rich food", "Eggs", "Chicken"));
                break;

            case "Ovulation":
                dto.setWorkouts(List.of("Cardio", "Running"));
                dto.setDiet(List.of("Fruits", "Salads"));
                break;

            case "Luteal":
                dto.setWorkouts(List.of("Light cardio", "Stretching"));
                dto.setDiet(List.of("Magnesium rich food", "Dark chocolate"));
                break;
        }
        return dto;
    }
}
