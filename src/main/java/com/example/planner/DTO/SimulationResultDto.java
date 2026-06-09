package com.example.planner.DTO;

import com.example.planner.enums.ExpeditionStatus;
import java.util.Map;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public class SimulationResultDto {
    
    private boolean feasible;
    private ExpeditionStatus status;
    private String failureReason;
    private int totalDays;
    private double totalDistance;
    private double avgSpeed;
    private int totalLootValue;
    private Map<String, Integer> lootByType;
    private int totalSlaves;
    private int totalSuppliesConsumed;
    private int totalSuppliesAvailable;
    
    public SimulationResultDto(){}
    
    public boolean isFeasible(){ 
        return feasible; 
    }
    public void setFeasible(boolean feasible){ 
        this.feasible = feasible; 
    }
    
    public ExpeditionStatus getStatus(){ 
        return status; 
    }
    public void setStatus(ExpeditionStatus status){ 
        this.status = status; 
    }
    
    public String getFailureReason(){ 
        return failureReason; 
    }
    public void setFailureReason(String failureReason){ 
        this.failureReason = failureReason; 
    }
    
    public int getTotalDays(){ 
        return totalDays; 
    }
    public void setTotalDays(int totalDays){ 
        this.totalDays = totalDays; 
    }
    
    public double getTotalDistance(){ 
        return totalDistance; 
    }
    public void setTotalDistance(double totalDistance){ 
        this.totalDistance = totalDistance; 
    }
    
    public double getAvgSpeed(){ 
        return avgSpeed; 
    }
    public void setAvgSpeed(double avgSpeed){ 
        this.avgSpeed = avgSpeed; 
    }
    
    public int getTotalLootValue(){ 
        return totalLootValue; 
    }
    public void setTotalLootValue(int totalLootValue){ 
        this.totalLootValue = totalLootValue; 
    }
    
    public Map<String, Integer> getLootByType(){ 
        return lootByType; 
    }
    public void setLootByType(Map<String, Integer> lootByType){ 
        this.lootByType = lootByType; 
    }
    
    public int getTotalSlaves(){ 
        return totalSlaves; 
    }
    public void setTotalSlaves(int totalSlaves){ 
        this.totalSlaves = totalSlaves; 
    }
    
    public int getTotalSuppliesConsumed(){ 
        return totalSuppliesConsumed; 
    }
    public void setTotalSuppliesConsumed(int totalSuppliesConsumed){
        this.totalSuppliesConsumed = totalSuppliesConsumed;
    }
    
    public int getTotalSuppliesAvailable() { return totalSuppliesAvailable; }
    public void setTotalSuppliesAvailable(int totalSuppliesAvailable){
        this.totalSuppliesAvailable = totalSuppliesAvailable;
    }
}
