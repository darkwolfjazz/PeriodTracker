package com.periodTracker.service;

import com.periodTracker.dto.CycleHistoryDTO;
import com.periodTracker.dto.CycleRequestDTO;
import com.periodTracker.dto.DashboardResponseDTO;
import com.periodTracker.dto.FullCycleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CycleService {
    FullCycleResponse createCycle(CycleRequestDTO request);
    List<CycleHistoryDTO>getCycleHistory();
    DashboardResponseDTO getDashboard();
}
