package com.example.planner.Controller;

import com.example.planner.DTO.SettlementDto;
import com.example.planner.Service.SettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@RestController
@RequestMapping("/api/settlements")
@Tag(name = "Settlements", description = "Settlement control")
public class SettlementController {
    
    @Autowired
    private SettlementService settlementService;
    
    @GetMapping
    @Operation(summary = "Get all settlements")
    public ResponseEntity<List<SettlementDto>> getAllSettlements(){
        return ResponseEntity.ok(settlementService.getAllSettlements());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get settlement by ID")
    public ResponseEntity<SettlementDto> getSettlementById(@PathVariable Long id){
        return ResponseEntity.ok(settlementService.getSettlementById(id));
    }
}
