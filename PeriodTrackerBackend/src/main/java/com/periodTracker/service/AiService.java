package com.periodTracker.service;

import com.periodTracker.dto.AiChatRequestDTO;
import com.periodTracker.dto.AiChatResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AiService {

    AiChatResponseDTO chat(AiChatRequestDTO request);


}
