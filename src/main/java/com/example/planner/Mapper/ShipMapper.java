package com.example.planner.Mapper;

import com.example.planner.DTO.ShipDto;
import com.example.planner.Model.VikingShip;
import org.springframework.stereotype.Component;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
@Component
public class ShipMapper {
    
        public ShipDto toDto(VikingShip ship){
        if (ship == null) return null;
        
        ShipDto dto = new ShipDto();
        dto.setId(ship.getId());
        dto.setName(ship.getName());
        dto.setMaxRowers(ship.getMaxRowers());
        dto.setMaxCargo(ship.getMaxCargo());
        dto.setMaxSlaves(ship.getMaxSlaves());
        dto.setBaseSpeed(ship.getBaseSpeed());
        dto.setFoodConsumptionPerPerson(ship.getFoodConsumptionPerPerson());
        dto.setDescription(ship.getDescription());
        
        return dto;
    }
}
