package com.periodTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullCycleResponse {

    private CycleResponseDTO cycle;
    private PhaseRecommendationDTO recommendation;

}
