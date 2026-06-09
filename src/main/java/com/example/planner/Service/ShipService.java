package com.example.planner.Service;

import com.example.planner.DTO.ShipDto;
import com.example.planner.Mapper.ShipMapper;
import com.example.planner.Repository.ShipRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Service
public class ShipService {
    
    @Autowired
    private ShipRepository shipRepository;
    
    @Autowired
    private ShipMapper shipMapper;
    
    public List<ShipDto> getAllShips() {
        return shipRepository.findAll().stream()
            .map(shipMapper::toDto)
            .collect(Collectors.toList());
    }
    
    public ShipDto getShipById(Long id) {
        return shipRepository.findById(id)
            .map(shipMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Ship not found"));
    }
}
