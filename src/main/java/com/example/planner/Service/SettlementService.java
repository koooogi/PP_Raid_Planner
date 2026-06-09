package com.example.planner.Service;

import com.example.planner.DTO.SettlementDto;
import com.example.planner.Mapper.SettlementMapper;
import com.example.planner.Repository.SettlementRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Service
public class SettlementService {
    
    @Autowired
    private SettlementRepository settlementRepository;
    
    @Autowired
    private SettlementMapper settlementMapper;
    
    public List<SettlementDto> getAllSettlements(){
        return settlementRepository.findAll().stream()
            .map(settlementMapper::toDto)
            .collect(Collectors.toList());
    }
    
    public SettlementDto getSettlementById(Long id){
        return settlementRepository.findById(id)
            .map(settlementMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Settlement not found"));
    }
}
