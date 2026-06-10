package com.example.planner.Service;

import com.example.planner.DTO.ExpeditionRequestDto;
import com.example.planner.Model.Expedition;
import com.example.planner.Model.User;
import com.example.planner.Repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@SpringBootTest
@Transactional //откат изменений после тестов
public class ExpeditionServiceTest {
    
    @Autowired
    private ExpeditionService expeditionService;
    
    @Autowired
    private UserRepository userRepository;
    
    private String testUsername;
    
    @BeforeEach
    void setUp(){
        
        User existingUser = userRepository.findAll().stream().findFirst().orElse(null);
        
        testUsername = existingUser.getUsername();
        
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(testUsername, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    //INT-TEST1 - Creating expedition
    @Test
    @DisplayName("Creating an expedition and verifying its storage in the database")
    void testCreateExpedition(){
        
        ExpeditionRequestDto request = new ExpeditionRequestDto();
        request.setShipId(Long.valueOf(1));
        request.setCrewIds(Arrays.asList(Long.valueOf(1), Long.valueOf(2), Long.valueOf(3)));
        request.setSettlementIds(Arrays.asList(Long.valueOf(1), Long.valueOf(2)));
        
        Expedition expedition = expeditionService.createExpedition(request);
        
        assertNotNull(expedition);
        assertNotNull(expedition.getId());
        assertEquals(1L, expedition.getShipId());
        assertTrue(expedition.getCrewIds().contains("1"));
        assertTrue(expedition.getRoute().contains("1"));
        assertNotNull(expedition.getSimulationParams());
    }
    
    //INT-TEST2 - Simulating expedition
    @Test
    @DisplayName("Launching the expedition simulation returns a result.")
    void testSimulateExpedition(){
        
        ExpeditionRequestDto request = new ExpeditionRequestDto();
        request.setShipId(Long.valueOf(1));
        request.setCrewIds(Arrays.asList(Long.valueOf(1), Long.valueOf(2), Long.valueOf(3)));
        request.setSettlementIds(Arrays.asList(Long.valueOf(1)));
        
        Expedition expedition = expeditionService.createExpedition(request);
        
        var result = expeditionService.simulateExpedition(expedition.getId());
        
        assertNotNull(result);
        assertNotNull(result.getStatus());
        assertTrue(result.getTotalDays() >= 0);
        assertTrue(result.getTotalLootValue() >= 0);
        assertTrue(result.getTotalSlaves() >= 0);
    }
}
