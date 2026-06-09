package com.example.planner.DTO;

import java.util.List;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public class ExpeditionRequestDto {
        
    private Long shipId;
    private List<Long> crewIds;
    private List<Long> settlementIds;
    
    public ExpeditionRequestDto() {}
    
    public Long getShipId(){ 
        return shipId; 
    }
    public void setShipId(Long shipId){ 
        this.shipId = shipId; 
    }
    
    public List<Long> getCrewIds(){ 
        return crewIds; 
    }
    public void setCrewIds(List<Long> crewIds){ 
        this.crewIds = crewIds; 
    }
    
    public List<Long> getSettlementIds(){ 
        return settlementIds; 
    }
    public void setSettlementIds(List<Long> settlementIds){ 
        this.settlementIds = settlementIds; 
    }
}
