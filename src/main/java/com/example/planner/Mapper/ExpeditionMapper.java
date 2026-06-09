package com.example.planner.Mapper;

import com.example.planner.DTO.ExpeditionHistoryDto;
import com.example.planner.Model.Expedition;
import com.example.planner.Repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpeditionMapper {
    
    @Autowired
    private ShipRepository shipRepository;
    
    public ExpeditionHistoryDto toHistoryDto(Expedition expedition) {
        if (expedition == null) return null;
        
        ExpeditionHistoryDto dto = new ExpeditionHistoryDto();
        dto.setId(expedition.getId());
        dto.setStatus(expedition.getStatus());
        dto.setTotalDays(expedition.getTotalDays());
        dto.setTotalLoot(expedition.getTotalLoot());
        dto.setTotalSlaves(expedition.getTotalSlaves());
        dto.setFailureReason(expedition.getFailureReason());
        
        if (expedition.getShipId() != null) {
            shipRepository.findById(expedition.getShipId()).ifPresent(ship -> {
                dto.setShipName(ship.getName());
            });
        }
        
        if (expedition.getCrewIds() != null && !expedition.getCrewIds().isEmpty() && !"[]".equals(expedition.getCrewIds())) {
            String idsStr = expedition.getCrewIds().replaceAll("[\\[\\]]", "").trim();
            if (!idsStr.isEmpty()) {
                String[] crewIds = idsStr.split(",");
                dto.setCrewCount(crewIds.length);
            } else {
                dto.setCrewCount(0);
            }
        } else {
            dto.setCrewCount(0);
        }
        
        if (expedition.getRoute() != null && !expedition.getRoute().isEmpty() && !"[]".equals(expedition.getRoute())) {
            String idsStr = expedition.getRoute().replaceAll("[\\[\\]]", "").trim();
            if (!idsStr.isEmpty()) {
                String[] routeIds = idsStr.split(",");
                dto.setSettlementCount(routeIds.length);
            } else {
                dto.setSettlementCount(0);
            }
        } else {
            dto.setSettlementCount(0);
        }
        
        return dto;
    }
}