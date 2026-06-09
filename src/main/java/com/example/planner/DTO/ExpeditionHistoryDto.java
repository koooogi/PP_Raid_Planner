package com.example.planner.DTO;

import com.example.planner.enums.ExpeditionStatus;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public class ExpeditionHistoryDto {
    
    private Long id;
    private String shipName;
    private int crewCount;
    private int settlementCount;
    private ExpeditionStatus status;
    private Integer totalDays;
    private Integer totalLoot;
    private Integer totalSlaves;
    private String failureReason;
    
    public ExpeditionHistoryDto() {}
    
    public Long getId(){ 
        return id; 
    }
    public void setId(Long id){ 
        this.id = id; 
    }
    public String getShipName(){ 
        return shipName; 
    }
    public void setShipName(String shipName){ 
        this.shipName = shipName; 
    }
    public int getCrewCount(){ 
        return crewCount; 
    }
    public void setCrewCount(int crewCount){ 
        this.crewCount = crewCount; 
    }
    public int getSettlementCount(){ 
        return settlementCount; 
    }
    public void setSettlementCount(int settlementCount){ 
        this.settlementCount = settlementCount; 
    }
    public ExpeditionStatus getStatus(){ 
        return status; 
    }
    public void setStatus(ExpeditionStatus status){ 
        this.status = status; 
    }
    public Integer getTotalDays(){ 
        return totalDays; 
    }
    public void setTotalDays(Integer totalDays){ 
        this.totalDays = totalDays; 
    }
    public Integer getTotalLoot(){ 
        return totalLoot; 
    }
    public void setTotalLoot(Integer totalLoot){ 
        this.totalLoot = totalLoot; 
    }
    public Integer getTotalSlaves(){ 
        return totalSlaves; 
    }
    public void setTotalSlaves(Integer totalSlaves){ 
        this.totalSlaves = totalSlaves; 
    }
    public String getFailureReason(){ 
        return failureReason; 
    }
    public void setFailureReason(String failureReason){ 
        this.failureReason = failureReason; 
    }
}
