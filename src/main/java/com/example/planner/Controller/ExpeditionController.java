package com.example.planner.Controller;

import com.example.planner.DTO.ExpeditionHistoryDto;
import com.example.planner.DTO.ExpeditionRequestDto;
import com.example.planner.DTO.SimulationResultDto;
import com.example.planner.Model.Expedition;
import com.example.planner.Service.ExpeditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expeditions")
@Tag(name = "Expeditions", description = "Expedition control")
public class ExpeditionController {
    
    @Autowired
    private ExpeditionService expeditionService;
    
    @PostMapping
    @Operation(summary = "Create a new expedition")
    public ResponseEntity<Expedition> createExpedition(@RequestBody ExpeditionRequestDto request) {
        System.out.println("Received request: shipId=" + request.getShipId() + ", crewIds=" + request.getCrewIds() + ", settlementIds=" + request.getSettlementIds());
        return ResponseEntity.ok(expeditionService.createExpedition(request));
    }
    
    @PostMapping("/{id}/simulate")
    @Operation(summary = "Simulate an expedition")
    public ResponseEntity<SimulationResultDto> simulateExpedition(@PathVariable Long id) {
        return ResponseEntity.ok(expeditionService.simulateExpedition(id));
    }
    
    @GetMapping("/history")
    @Operation(summary = "Get current user's expedition history")
    public ResponseEntity<List<ExpeditionHistoryDto>> getUserHistory() {
        return ResponseEntity.ok(expeditionService.getUserHistory());
    }
    
    @GetMapping("/{id}/result")
    @Operation(summary = "Get simulation result")
    public ResponseEntity<SimulationResultDto> getExpeditionResult(@PathVariable Long id) {
        return ResponseEntity.ok(expeditionService.getExpeditionResult(id));
    }
}