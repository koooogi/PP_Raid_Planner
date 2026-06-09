package com.example.planner.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ExpeditionRequestDto {
    
    @JsonProperty("shipId")
    private Long shipId;
    
    @JsonProperty("crewIds")
    private List<Long> crewIds;
    
    @JsonProperty("settlementIds")
    private List<Long> settlementIds;
    
    public ExpeditionRequestDto() {}
    
    public Long getShipId() { 
        return shipId; 
    }
    public void setShipId(Long shipId) { 
        this.shipId = shipId; 
    }
    
    public List<Long> getCrewIds() { 
        return crewIds; 
    }
    public void setCrewIds(List<Long> crewIds) { 
        this.crewIds = crewIds; 
    }
    
    public List<Long> getSettlementIds() { 
        return settlementIds; 
    }
    public void setSettlementIds(List<Long> settlementIds) { 
        this.settlementIds = settlementIds; 
    }
}