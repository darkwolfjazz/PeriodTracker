package com.periodTracker.service;

import com.periodTracker.dto.AiChatRequestDTO;
import com.periodTracker.dto.AiChatResponseDTO;
import com.periodTracker.entity.Cycle;
import com.periodTracker.entity.Profile;
import com.periodTracker.entity.User;
import com.periodTracker.exceptions.AppExceptions;
import com.periodTracker.repository.CycleRepository;
import com.periodTracker.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {


    @Value("${openrouter.api.url}")
    private String apiUrl;

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public AiChatResponseDTO chat(AiChatRequestDTO request) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        Profile profile=profileRepository.findByUserUserId(user.getUserId()).orElseThrow();
       List<Cycle>cycles=cycleRepository.findByUserUserIdOrderByStartDateDesc(user.getUserId());
       if(cycles.isEmpty()){
           throw new AppExceptions(404,"Please complete your cycle setup first");
       }
       Cycle latestCycle=cycles.get(0);
        long days= ChronoUnit.DAYS.between(latestCycle.getStartDate(), LocalDate.now());
        int dayofCycle=Math.floorMod((int)days, latestCycle.getCycleLength());
        String phase=getCurrentPhase(dayofCycle,latestCycle);
        String prompt = String.format("""
You are Aura, a warm female wellness companion.
Use user's cycle context.
Reply in:
- maximum 50 words
- 2 to 4 short sentences
- simple conversational English
- use 1 emoji max
Never give medical diagnosis.
If symptoms seem severe, suggest consulting a doctor.
User context:
Age: %s
Current phase: %s
Day of cycle: %s
User message:
%s
""",
                profile.getAge(),
                profile.getHeight(),
                profile.getWeight(),
                phase,
                dayofCycle,
                request.getMessage());
          String aiReply=callLLM(prompt);
          AiChatResponseDTO response=new AiChatResponseDTO();
          response.setResponse(aiReply);
          return response;
    }
    private String getCurrentPhase(int day,Cycle cycle){
      if(day< cycle.getPeriodDuration()){
          return "Menstrual";
      }else if(day<(cycle.getCycleLength()-14)){
          return "Follicular";
      }else if(day==(cycle.getCycleLength()-14)){
          return "Ovulation";
      }
      return "Luteal";
    }

    private String callLLM(String prompt){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        Map<String,Object> body = new HashMap<>();
        body.put("model", "openai/gpt-oss-120b");
        List<Map<String,String>> messages = List.of(Map.of("role", "user", "content", prompt));
        body.put("messages", messages);
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body,headers);
        Map response = restTemplate.postForObject(apiUrl,entity,Map.class);
        List choices = (List) response.get("choices");
        Map choice = (Map) choices.get(0);
        Map message =(Map) choice.get("message");
        return message.get("content").toString();

    }

}
