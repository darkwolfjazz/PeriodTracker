package com.periodTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CycleResponseDTO {
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private LocalDate ovulationDate;
    private LocalDate nextPeriodDate;
    private String currentPhase;

}
