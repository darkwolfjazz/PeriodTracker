package com.periodTracker.controller;

import com.periodTracker.dto.CycleRequestDTO;
import com.periodTracker.dto.FullCycleResponse;
import com.periodTracker.service.CycleService;
import com.periodTracker.service.CycleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cycle")
public class CycleController {
    @Autowired
    private CycleService cycleService;

    @PostMapping("/create")
    public ResponseEntity<FullCycleResponse>createCycle(@RequestBody CycleRequestDTO request){
        return ResponseEntity.ok(cycleService.createCycle(request));
    }
}
