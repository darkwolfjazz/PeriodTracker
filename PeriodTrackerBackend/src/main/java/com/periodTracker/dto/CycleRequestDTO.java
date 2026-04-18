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
public class CycleRequestDTO {
    private Long userId;
    private LocalDate lastPeriodDate;
    // optional (if user wants to override defaults)
    private Integer cycleLength;
    private Integer periodDuration;


}
