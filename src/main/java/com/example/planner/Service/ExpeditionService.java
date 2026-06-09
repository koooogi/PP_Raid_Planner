package com.example.planner.Service;

import com.example.planner.DTO.ExpeditionHistoryDto;
import com.example.planner.DTO.ExpeditionRequestDto;
import com.example.planner.DTO.SimulationResultDto;
import com.example.planner.Mapper.ExpeditionMapper;
import com.example.planner.Model.Expedition;
import com.example.planner.Model.User;
import com.example.planner.Repository.ExpeditionRepository;
import com.example.planner.Repository.UserRepository;
import com.example.planner.Simulation.ExpeditionSimulator;
import com.example.planner.enums.ExpeditionStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Service
public class ExpeditionService {
    
    @Autowired
    private ExpeditionRepository expeditionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ExpeditionMapper expeditionMapper;
    
    @Autowired
    private ExpeditionSimulator simulator;
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    public Expedition createExpedition(ExpeditionRequestDto request){
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Expedition expedition = new Expedition();
        expedition.setUserId(user.getId());
        expedition.setShipId(request.getShipId());
        
        try{
            expedition.setCrewIds(mapper.writeValueAsString(request.getCrewIds()));
            expedition.setRoute(mapper.writeValueAsString(request.getSettlementIds()));
        }catch (Exception e){
            throw new RuntimeException("Failed to serialize expedition data");
        }
        
        expedition.setStatus(ExpeditionStatus.PLANNED);
        
        return expeditionRepository.save(expedition);
    }
    
    public SimulationResultDto simulateExpedition(Long expeditionId){
        throw new UnsupportedOperationException("Simulation not implemented yet");
    }
    
    public List<ExpeditionHistoryDto> getUserHistory(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return expeditionRepository.findByUserIdOrderByIdDesc(user.getId()).stream()
            .map(expeditionMapper::toHistoryDto)
            .collect(Collectors.toList());
    }
    
    public SimulationResultDto getExpeditionResult(Long expeditionId){
        Expedition expedition = expeditionRepository.findById(expeditionId)
            .orElseThrow(() -> new RuntimeException("Expedition not found"));
        
        if (expedition.getSimulationResult() == null) {
            throw new RuntimeException("Expedition not simulated yet");
        }
        
        try {
            return mapper.readValue(expedition.getSimulationResult(), SimulationResultDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse simulation result");
        }
    }
}
