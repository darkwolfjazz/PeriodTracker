package com.periodTracker.controller;

import com.periodTracker.dto.CycleHistoryDTO;
import com.periodTracker.dto.CycleRequestDTO;
import com.periodTracker.dto.DashboardResponseDTO;
import com.periodTracker.dto.FullCycleResponse;
import com.periodTracker.service.CycleService;
import com.periodTracker.service.CycleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cycle")
@CrossOrigin("*")
public class CycleController {
    @Autowired
    private CycleService cycleService;

    @PostMapping("/create")
    public ResponseEntity<FullCycleResponse>createCycle(@RequestBody CycleRequestDTO request){
        return ResponseEntity.ok(cycleService.createCycle(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<CycleHistoryDTO>>getHistory(){
        return ResponseEntity.ok(cycleService.getCycleHistory());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDTO>getDashboardData(){
        return ResponseEntity.ok(cycleService.getDashboard());
    }
}
