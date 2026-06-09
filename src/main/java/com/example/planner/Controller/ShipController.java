package com.example.planner.Controller;

import com.example.planner.DTO.ShipDto;
import com.example.planner.Service.ShipService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/ships")
@Tag(name = "Ships", description = "Ship control")
public class ShipController {
    
    @Autowired
    private ShipService shipService;
    
    @GetMapping
    @Operation(summary = "Get all ships")
    public ResponseEntity<List<ShipDto>> getAllShips(){
        return ResponseEntity.ok(shipService.getAllShips());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get ship by ID")
    public ResponseEntity<ShipDto> getShipById(@PathVariable Long id){
        return ResponseEntity.ok(shipService.getShipById(id));
    }
}
