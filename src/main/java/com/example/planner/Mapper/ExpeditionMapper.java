package com.example.planner.Mapper;

import com.example.planner.DTO.ExpeditionHistoryDto;
import com.example.planner.Model.Expedition;
import com.example.planner.Repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
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
        
        if (expedition.getCrewIds() != null) {
            String[] crewIds = expedition.getCrewIds().replaceAll("[\\[\\]]", "").split(",");
            dto.setCrewCount(crewIds.length);
        }
        
        if (expedition.getRoute() != null) {
            String[] routeIds = expedition.getRoute().replaceAll("[\\[\\]]", "").split(",");
            dto.setSettlementCount(routeIds.length);
        }
        
        return dto;
    }
}
