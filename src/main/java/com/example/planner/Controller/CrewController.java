package com.example.planner.Controller;

import com.example.planner.DTO.CrewMemberDto;
import com.example.planner.Service.CrewService;
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
@RequestMapping("/api/crew")
@Tag(name = "Crew", description = "Crew control")
public class CrewController {
    
    @Autowired
    private CrewService crewService;
    
    @GetMapping
    @Operation(summary = "Get all crew members")
    public ResponseEntity<List<CrewMemberDto>> getAllCrew(){
        return ResponseEntity.ok(crewService.getAllCrew());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get crew member by ID")
    public ResponseEntity<CrewMemberDto> getCrewMemberById(@PathVariable Long id){
        return ResponseEntity.ok(crewService.getCrewMemberById(id));
    }    
}
