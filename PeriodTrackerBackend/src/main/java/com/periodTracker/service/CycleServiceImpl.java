package com.periodTracker.service;

import com.periodTracker.dto.*;
import com.periodTracker.entity.Cycle;
import com.periodTracker.entity.Profile;
import com.periodTracker.entity.User;
import com.periodTracker.repository.CycleRepository;
import com.periodTracker.repository.ProfileRepository;
import com.periodTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication value is : " +authentication);
        User user = (User) authentication.getPrincipal();
        System.out.println("Principal is : " +user);
        //fetching profile
        Profile profile=profileRepository
                .findByUserUserId(user.getUserId()).orElseThrow(()->new RuntimeException("Profile not found"));
        int cycleLength = request.getCycleLength() != null
                ? request.getCycleLength()
                : profile.getCycleLength() != null
                ? profile.getCycleLength()
                : 28;

        int periodDuration = request.getPeriodDuration() != null
                ? request.getPeriodDuration()
                : profile.getPeriodDuration() != null
                ? profile.getPeriodDuration()
                : 5;

        profile.setCycleLength(cycleLength);
        profile.setPeriodDuration(periodDuration);
        profileRepository.save(profile);

        LocalDate startDate=request.getLastPeriodDate();
        if(startDate.isAfter(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Period start date cannot be a future date");
        }

        List<Cycle>existingCycle=cycleRepository.findByUserUserIdOrderByStartDateDesc(user.getUserId());
        if(!existingCycle.isEmpty()){
            Cycle latestCycle=existingCycle.get(0);
            if(!startDate.isAfter(latestCycle.getStartDate())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"New Period must be after previous cycle");
            }
        }

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
    private String getCurrentPhase(LocalDate startDate,int cycleLength,int periodDuration){
       long days=ChronoUnit.DAYS.between(startDate,LocalDate.now());
       int day=Math.floorMod((int)days,cycleLength);
       if(day<periodDuration){
           return "Menstrual";
       }else if(day<(cycleLength-14)){
          return "Follicular";
       }else if(day==cycleLength-14){
         return "Ovulation";
       }else{
           return "Luteal";
       }
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
    @Override
    public List<CycleHistoryDTO> getCycleHistory() {
       //Step1:get logged in user
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        //Step 2: Fetch cycles
        List<Cycle>cycles=cycleRepository.findByUserUserIdOrderByStartDateDesc(user.getUserId());
       //Step3 : Convert to DTO
        return cycles.stream().map(cycle -> {
            CycleHistoryDTO dto = new CycleHistoryDTO();
            dto.setPeriodStart(cycle.getStartDate());
            dto.setPeriodEnd(cycle.getEndDate());
            dto.setOvulationDate(cycle.getOvulationDate());
            dto.setNextPeriodDate(cycle.getNextPeriodDate());
            dto.setCycleLength(cycle.getCycleLength());
            dto.setPeriodDuration(cycle.getPeriodDuration());
            return dto;
        }).toList();
    }

    @Override
    public DashboardResponseDTO getDashboard() {
        // 1. Get logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // 2. Get latest cycle
        List<Cycle> cycles = cycleRepository
                .findByUserUserIdOrderByStartDateDesc(user.getUserId());

        System.out.println("Cycle count:" +cycles.size());
        if (cycles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No cycle data");
        }

        Cycle latestCycle = cycles.get(0);

        // 3. Calculate day of cycle
        long days = ChronoUnit.DAYS.between(latestCycle.getStartDate(), LocalDate.now());
        int dayOfCycle = Math.floorMod((int)days, latestCycle.getCycleLength());
        // 4. Detect phase
        String phase = getCurrentPhase(
                latestCycle.getStartDate(),
                latestCycle.getCycleLength(),
                latestCycle.getPeriodDuration()
        );
        // 5. Get recommendations
        PhaseRecommendationDTO recommendation = getRecommendation(phase);
        // 6. Build response
        DashboardResponseDTO dto = new DashboardResponseDTO();
        dto.setPeriodStart(latestCycle.getStartDate());
        dto.setPeriodEnd(latestCycle.getEndDate());
        dto.setOvulationDate(latestCycle.getOvulationDate());
        dto.setNextPeriodDate(latestCycle.getNextPeriodDate());
        dto.setDayOfCycle(dayOfCycle);
        dto.setCurrentPhase(phase);
        dto.setWorkouts(recommendation.getWorkouts());
        dto.setDiet(recommendation.getDiet());
        return dto;
    }
}
