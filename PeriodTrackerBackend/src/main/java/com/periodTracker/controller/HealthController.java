package com.periodTracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {


    @GetMapping()
    public String checkHealth(){
        return "Sab Changaa sii !";

    }


}
