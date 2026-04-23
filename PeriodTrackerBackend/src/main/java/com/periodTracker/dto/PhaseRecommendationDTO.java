package com.periodTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhaseRecommendationDTO {

    private String phase;
    private List<String> workouts;
    private List<String> diet;


}
