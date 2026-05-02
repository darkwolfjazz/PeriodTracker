package com.periodTracker.controller;

import com.periodTracker.dto.AiChatRequestDTO;
import com.periodTracker.dto.AiChatResponseDTO;
import com.periodTracker.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    @Autowired
    private AiService aiService;

    @PostMapping("/chat")
    public ResponseEntity<AiChatResponseDTO>chat(@RequestBody AiChatRequestDTO request){
        return ResponseEntity.ok(aiService.chat(request));
    }




}
