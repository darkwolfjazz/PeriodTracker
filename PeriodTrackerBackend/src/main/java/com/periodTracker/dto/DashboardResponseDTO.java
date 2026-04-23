package com.periodTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDTO {

    private LocalDate periodStart;
    private LocalDate periodEnd;
    private LocalDate ovulationDate;
    private LocalDate nextPeriodDate;
    private int dayOfCycle;
    private String currentPhase;
    private List<String> workouts;
    private List<String> diet;


}
