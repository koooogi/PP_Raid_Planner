package com.example.planner.Mapper;

import com.example.planner.DTO.SettlementDto;
import com.example.planner.Model.Settlement;
import org.springframework.stereotype.Component;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
@Component
public class SettlementMapper {

    public SettlementDto toDto(Settlement settlement) {
        if (settlement == null) return null;
        
        SettlementDto dto = new SettlementDto();
        dto.setId(settlement.getId());
        dto.setName(settlement.getName());
        dto.setType(settlement.getType());
        dto.setX(settlement.getX());
        dto.setY(settlement.getY());
        dto.setScale(settlement.getScale());
        dto.setBaseLoot(settlement.getBaseLoot());
        dto.setDescription(settlement.getDescription());
        
        return dto;
    }
    
}
