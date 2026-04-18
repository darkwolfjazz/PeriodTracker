package com.periodTracker.service;

import com.periodTracker.dto.CycleRequestDTO;
import com.periodTracker.dto.FullCycleResponse;
import org.springframework.stereotype.Service;

@Service
public interface CycleService {
    FullCycleResponse createCycle(CycleRequestDTO request);

}
