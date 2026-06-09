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
        
        System.out.println("=== CREATE EXPEDITION ===");
        System.out.println("Request shipId: " + request.getShipId());
        System.out.println("Request crewIds: " + request.getCrewIds());
        System.out.println("Request settlementIds: " + request.getSettlementIds());
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Expedition expedition = new Expedition();
        expedition.setUserId(user.getId());
        expedition.setShipId(request.getShipId());
        
        try{
            String crewIdsJson = mapper.writeValueAsString(request.getCrewIds());
            String routeJson = mapper.writeValueAsString(request.getSettlementIds());
            String paramsJson = mapper.writeValueAsString(request);
            
            System.out.println("CrewIds JSON: " + crewIdsJson);
            System.out.println("Route JSON: " + routeJson);
            
            expedition.setCrewIds(crewIdsJson);
            expedition.setRoute(routeJson);
            expedition.setSimulationParams(paramsJson);
        }catch (Exception e){
            System.err.println("Failed to serialize: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to serialize expedition data");
        }
        
        expedition.setStatus(ExpeditionStatus.PLANNED);
        
        Expedition saved = expeditionRepository.save(expedition);
        System.out.println("Saved expedition with ID: " + saved.getId());
        System.out.println("Saved shipId: " + saved.getShipId());
        System.out.println("Saved crewIds: " + saved.getCrewIds());
        System.out.println("Saved route: " + saved.getRoute());
        
        return saved;
    }
    
    public SimulationResultDto simulateExpedition(Long expeditionId){
        
        Expedition expedition = expeditionRepository.findById(expeditionId)
            .orElseThrow(() -> new RuntimeException("Expedition not found"));
        
        System.out.println("=== RETRIEVED EXPEDITION ===");
        System.out.println("Expedition ID: " + expedition.getId());
        System.out.println("Ship ID from DB: " + expedition.getShipId());
        System.out.println("Crew IDs from DB: " + expedition.getCrewIds());
        System.out.println("Route from DB: " + expedition.getRoute());
        
        SimulationResultDto result = simulator.simulate(expedition);
        
        expedition.setStatus(result.getStatus());
        expedition.setTotalDays(result.getTotalDays());
        expedition.setTotalLoot(result.getTotalLootValue());
        expedition.setTotalSlaves(result.getTotalSlaves());
        expedition.setFailureReason(result.getFailureReason());
        
        try{
            expedition.setSimulationResult(mapper.writeValueAsString(result));
        }catch (Exception e){
            throw new RuntimeException("Failed to serialize simulation result");
        }
        
        expeditionRepository.save(expedition);
        
        return result;
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