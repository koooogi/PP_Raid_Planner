package com.example.planner.Simulation;

import com.example.planner.DTO.SimulationResultDto;
import com.example.planner.Model.CrewMember;
import com.example.planner.Model.Expedition;
import com.example.planner.Model.Settlement;
import com.example.planner.Model.VikingShip;
import com.example.planner.Repository.CrewRepository;
import com.example.planner.Repository.SettlementRepository;
import com.example.planner.Repository.ShipRepository;
import com.example.planner.enums.ExpeditionStatus;
import com.example.planner.enums.LootType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpeditionSimulator {
    
    private static final int MAX_DAYS = 60;
    private static final double MIN_SPEED = 0.5;
    
    @Autowired
    private ShipRepository shipRepository;
    
    @Autowired
    private CrewRepository crewRepository;
    
    @Autowired
    private SettlementRepository settlementRepository;
    
    private final Random random = new Random();
    
    public SimulationResultDto simulate(Expedition expedition){
        SimulationResultDto result = new SimulationResultDto();
        result.setFeasible(true);
        result.setStatus(ExpeditionStatus.SIMULATED);
        
        try{
            VikingShip ship = shipRepository.findById(expedition.getShipId())
                .orElseThrow(() -> new RuntimeException("Ship not found"));
            
            List<CrewMember> crew = getCrewMembers(expedition.getCrewIds());
            List<Settlement> route = getRoute(expedition.getRoute());
            
            if(route.isEmpty()){
                return fail(result, "No settlements selected");
            }
            
            if(crew.isEmpty()){
                return fail(result, "No crew selected");
            }
            
            int crewCount = crew.size();
            int totalSupplies = crew.stream().mapToInt(CrewMember::getSupplies).sum();
            int maxCargo = ship.getMaxCargo();
            int maxSlaves = ship.getMaxSlaves();
            
            double totalDays = 0;
            double totalDistance = 0;
            int totalLootValue = 0;
            Map<String, Integer> lootByType = new HashMap<>();
            int totalSlavesCaptured = 0;
            int currentCargo = 0;
            int currentSlaves = 0;
            int currentSupplies = totalSupplies;
            int successfulRaids = 0;
            
            for(int i = 0; i < route.size(); i++){
                Settlement settlement = route.get(i);
                
                double distance;
                if(i == 0){
                    distance = calculateDistance(0, 0, settlement.getX(), settlement.getY());
                }else{
                    Settlement prev = route.get(i - 1);
                    distance = calculateDistance(prev.getX(), prev.getY(), settlement.getX(), settlement.getY());
                }
                totalDistance += distance;
                
                double speed = calculateSpeed(ship.getBaseSpeed(), crewCount, ship.getMaxRowers());
                double daysToReach = distance / speed;
                totalDays += daysToReach;
                
                if(totalDays > MAX_DAYS){
                    return fail(result, "Expedition exceeds " + MAX_DAYS + " days");
                }
                
                // Рабы тоже расходуют припасы
                int totalPeople = crewCount + currentSlaves;
                int suppliesUsed = (int) (daysToReach * totalPeople * ship.getFoodConsumptionPerPerson());
                currentSupplies -= suppliesUsed;
                
                if(currentSupplies < 0){
                    return fail(result, "Not enough supplies to reach " + settlement.getName());
                }
                
                RaidResult raid = simulateRaid(settlement, crewCount, currentCargo, maxCargo, currentSlaves, maxSlaves);
                
                if(raid.isSuccess()){
                    successfulRaids++;
                    
                    int freeCargoSpace = maxCargo - currentCargo;
                    int actualLoot = Math.min(raid.getLootValue(), freeCargoSpace);
                    currentCargo += actualLoot;
                    totalLootValue += actualLoot;
                    
                    int freeSlaveSpace = maxSlaves - currentSlaves;
                    int actualSlaves = Math.min(raid.getSlaves(), freeSlaveSpace);
                    currentSlaves += actualSlaves;
                    totalSlavesCaptured += actualSlaves;
                    
                    if (raid.getLootValue() > 0) {
                        double proportion = (double) actualLoot / raid.getLootValue();
                        for(Map.Entry<LootType, Integer> entry : raid.getLootByType().entrySet()){
                            int proportionalLoot = (int) (proportion * entry.getValue());
                            if (proportionalLoot > 0) {
                                lootByType.merge(entry.getKey().name(), proportionalLoot, Integer::sum);
                            }
                        }
                    }
                    
                    currentSupplies += raid.getFoodLoot();
                }
            }
            
            if(successfulRaids == 0){
                return fail(result, "No successful raids");
            }
            
            result.setTotalDays((int) Math.ceil(totalDays));
            result.setTotalDistance(totalDistance);
            result.setAvgSpeed(totalDistance / totalDays);
            result.setTotalLootValue(totalLootValue);
            result.setLootByType(lootByType);
            result.setTotalSlaves(totalSlavesCaptured);
            result.setTotalSuppliesConsumed(totalSupplies - currentSupplies);
            result.setTotalSuppliesAvailable(totalSupplies);
            result.setStatus(ExpeditionStatus.SUCCESS);
            
        }catch(Exception e){
            result.setFeasible(false);
            result.setStatus(ExpeditionStatus.FAILED);
            result.setFailureReason("Simulation error: " + e.getMessage());
        }
        
        return result;
    }
    
    private List<CrewMember> getCrewMembers(String crewIdsJson){
        try{
            if (crewIdsJson == null || crewIdsJson.trim().isEmpty() || "[]".equals(crewIdsJson.trim())) {
                return new ArrayList<>();
            }
            String idsStr = crewIdsJson.replaceAll("[\\[\\]]", "").trim();
            if (idsStr.isEmpty()) {
                return new ArrayList<>();
            }
            
            List<Long> crewIds = Arrays.stream(idsStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
            return crewRepository.findAllById(crewIds);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    private List<Settlement> getRoute(String routeJson){
        try{
            if (routeJson == null || routeJson.trim().isEmpty() || "[]".equals(routeJson.trim())) {
                return new ArrayList<>();
            }
            String idsStr = routeJson.replaceAll("[\\[\\]]", "").trim();
            if (idsStr.isEmpty()) {
                return new ArrayList<>();
            }
            
            List<Long> settlementIds = Arrays.stream(idsStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
            return settlementRepository.findAllById(settlementIds);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    private double calculateDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    private double calculateSpeed(int baseSpeed, int crewCount, int maxRowers){
        double speed = baseSpeed * Math.min(1.0, (double) crewCount / maxRowers);
        return Math.max(MIN_SPEED, speed);
    }
    
    private RaidResult simulateRaid(Settlement settlement, int fightersCount, int currentCargo, int maxCargo, int currentSlaves, int maxSlaves){
        RaidResult result = new RaidResult();
        
        double successProb = calculateSuccessProbability(fightersCount, settlement.getScale());
        boolean success = random.nextDouble() < successProb;
        result.setSuccess(success);
        
        if (!success) {
            return result;
        }
        
        int freeSpace = maxCargo - currentCargo;
        int freeSlaveSpace = maxSlaves - currentSlaves;
        
        if (freeSpace <= 0 && freeSlaveSpace <= 0) {
            result.setLootValue(0);
            result.setSlaves(0);
            return result;
        }
        
        double multiplier = 0.7 + random.nextDouble() * 0.8;
        int totalLoot = (int) (settlement.getBaseLoot() * multiplier);
        totalLoot = Math.min(totalLoot, freeSpace);
        
        Map<LootType, Integer> lootByType = new HashMap<>();
        int remaining = totalLoot;
        
        LootType[] types = {LootType.GOLD, LootType.SILVER, LootType.WEAPONS, LootType.JEWELRY};
        for(LootType type : types){
            int amount = (int) (totalLoot * (0.1 + random.nextDouble() * 0.3));
            if (amount > remaining) amount = remaining;
            if (amount > 0) {
                lootByType.put(type, amount);
                remaining -= amount;
            }
        }
        
        int foodLoot = (int) (totalLoot * (0.1 + random.nextDouble() * 0.2));
        
        result.setLootValue(totalLoot);
        result.setLootByType(lootByType);
        result.setFoodLoot(foodLoot);
        
        if(random.nextDouble() < settlement.getSlaveProbability()){
            int slaves = settlement.getMinSlaves() + random.nextInt(settlement.getMaxSlaves() - settlement.getMinSlaves() + 1);
            slaves = Math.min(slaves, freeSlaveSpace);
            result.setSlaves(slaves);
        }
        
        return result;
    }
    
    private double calculateSuccessProbability(int fightersCount, int settlementScale){
        if (settlementScale <= 0) settlementScale = 1;
        double baseProb = 0.2;
        double crewFactor = Math.min(1.0, (double) fightersCount / settlementScale);
        double randomFactor = random.nextDouble() * 0.3;
        double prob = baseProb + crewFactor * 0.5 + randomFactor;
        return Math.min(0.95, Math.max(0.05, prob));
    }
    
    private SimulationResultDto fail(SimulationResultDto result, String reason){
        result.setFeasible(false);
        result.setStatus(ExpeditionStatus.FAILED);
        result.setFailureReason(reason);
        return result;
    }
    
    private static class RaidResult{
        private boolean success;
        private int lootValue;
        private Map<LootType, Integer> lootByType = new HashMap<>();
        private int foodLoot;
        private int slaves;
        
        public boolean isSuccess(){ 
            return success; 
        }
        public void setSuccess(boolean success){ 
            this.success = success; 
        }
       
        public int getLootValue(){ 
            return lootValue; 
        }
        public void setLootValue(int lootValue){ 
            this.lootValue = lootValue; 
        }
        
        public Map<LootType, Integer> getLootByType(){ 
            return lootByType; 
        }
        public void setLootByType(Map<LootType, Integer> lootByType){ 
            this.lootByType = lootByType; 
        }
        
        public int getFoodLoot(){ 
            return foodLoot; 
        }
        public void setFoodLoot(int foodLoot){ 
            this.foodLoot = foodLoot; 
        }
        
        public int getSlaves(){ 
            return slaves; 
        }
        public void setSlaves(int slaves){ 
            this.slaves = slaves; 
        }
    }
}