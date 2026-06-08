package com.example.planner.Model;

import com.example.planner.ENUMs.ExpeditionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
@Entity
@Table(name = "expeditions")
public class Expedition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "ship_id")
    private Long shipId;
    
    @Column(columnDefinition = "TEXT")
    private String crewIds;
    
    @Column(columnDefinition = "TEXT")
    private String route;
    
    @Enumerated(EnumType.STRING)
    private ExpeditionStatus status = ExpeditionStatus.PLANNED;
    
    @Column(name = "simulation_params", columnDefinition = "TEXT")
    private String simulationParams;
    
    @Column(name = "simulation_result", columnDefinition = "TEXT")
    private String simulationResult;
    
    @Column(name = "total_days")
    private Integer totalDays;
    
    @Column(name = "total_loot")
    private Integer totalLoot;
    
    @Column(name = "total_slaves")
    private Integer totalSlaves;
    
    @Column(name = "failure_reason")
    private String failureReason;
    
    public Expedition(){}
    
    public Expedition(Long userId, Long shipId, String crewIds, String route){
        this.userId = userId;
        this.shipId = shipId;
        this.crewIds = crewIds;
        this.route = route;
    }
    
    public Long getId(){ 
        return id; 
    }
    public Long getUserId(){ 
        return userId; 
    }
    public Long getShipId(){ 
        return shipId; 
    }
    public String getCrewIds(){ 
        return crewIds; 
    }
    public String getRoute(){ 
        return route; 
    }
    public ExpeditionStatus getStatus(){ 
        return status; 
    }
    public String getSimulationParams(){ 
        return simulationParams; 
    }
    public String getSimulationResult(){ 
        return simulationResult; 
    }
    public Integer getTotalDays(){ 
        return totalDays; 
    }
    public Integer getTotalLoot(){ 
        return totalLoot; 
    }
    public Integer getTotalSlaves(){ 
        return totalSlaves; 
    }
    public String getFailureReason(){ 
        return failureReason; 
    }
    
    public void setId(Long id){ 
        this.id = id; 
    }
    public void setUserId(Long userId){ 
        this.userId = userId; 
    }
    public void setShipId(Long shipId){ 
        this.shipId = shipId; 
    }
    public void setCrewIds(String crewIds){ 
        this.crewIds = crewIds; 
    }
    public void setRoute(String route){ 
        this.route = route; 
    }
    public void setStatus(ExpeditionStatus status){ 
        this.status = status; 
    }
    public void setSimulationParams(String simulationParams){ 
        this.simulationParams = simulationParams; 
    }
    public void setSimulationResult(String simulationResult){ 
        this.simulationResult = simulationResult; 
    }
    public void setTotalDays(Integer totalDays){ 
        this.totalDays = totalDays; 
    }
    public void setTotalLoot(Integer totalLoot){ 
        this.totalLoot = totalLoot; 
    }    
    public void setTotalSlaves(Integer totalSlaves){ 
        this.totalSlaves = totalSlaves; 
    }
    public void setFailureReason(String failureReason){ 
        this.failureReason = failureReason; 
    }
}
