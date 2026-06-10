package com.example.planner.Simulation;

import com.example.planner.Simulation.ExpeditionSimulator;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@SpringBootTest
@Transactional //откат изменений после тестов
public class ExpeditionSimulatorTest {
    
    private ExpeditionSimulator simulator;
    
    @BeforeEach
    void setUp() {
        simulator = new ExpeditionSimulator();
    }
    
    
    //UNIT1 - Verification of distance calculation
    @Test
    @DisplayName("Distance between (0,0) and (3,4) must be 5")
    void testDistance_ZeroToThreeFour_ShouldBeFive(){
        
        double x1 = 0, y1 = 0;
        double x2 = 3, y2 = 4;
        
        double distance = simulator.testCalculateDistance(x1, y1, x2, y2);
        
        assertEquals(5.0, distance, 0.001);
    }
    
    @Test
    @DisplayName("Distance between point and itself must be zero")
    void testDistance_SamePoint_ShouldBeZero() {
        
        double distance = simulator.testCalculateDistance(10, 20, 10, 20);
        assertEquals(0.0, distance, 0.001);
    }
    
    @Test
    @DisplayName("Distance with negative coordinates")
    void testDistance_NegativeCoordinates(){
        
        double distance = simulator.testCalculateDistance(-2, -3, 4, 5);
        assertEquals(10.0, distance, 0.001);
    }
    
    //UNIT2 - Verification of the speed calculation
    @Test
    @DisplayName("Full crew - max speed")
    void testSpeed_FullCrew_ShouldBeMaxSpeed(){
        
        double speed = simulator.testCalculateSpeed(10, 30, 30);
        assertEquals(10.0, speed, 0.001);
    }
    
    @Test
    @DisplayName("Half crew - half speed")
    void testSpeed_HalfCrew_ShouldBeHalfSpeed(){
        
        double speed = simulator.testCalculateSpeed(10, 15, 30);
        assertEquals(4.67, speed, 0.01);
    }
    
    @Test
    @DisplayName("Min speed not below 0.5")
    void testSpeed_MinimumSpeed_ShouldNotGoBelowZeroPointFive(){
        double speed = simulator.testCalculateSpeed(10, 1, 30);
        assertEquals(0.5, speed, 0.001);
    }
    
    @Test
    @DisplayName("Speed can not exceed base speed")
    void testSpeed_CannotExceedBaseSpeed(){
        
        double speed = simulator.testCalculateSpeed(12, 100, 50);
        assertEquals(12.0, speed, 0.001);
    }
    
    //UNIT3 - Verification of succsess probability
    @Test
    @DisplayName("The probability of success cannot be less than 5%.")
    void testSuccessProbability_ShouldNotBeBelowFivePercent(){
        
        double prob = simulator.testCalculateSuccessProbability(1, 1000);
        assertTrue(prob >= 0.05);
    }
    
    @Test
    @DisplayName("The probability of success cannot exceed 95%.")
    void testSuccessProbability_ShouldNotBeAboveNinetyFivePercent(){
        
        double prob = simulator.testCalculateSuccessProbability(1000, 10);
        assertTrue(prob <= 0.95);
    }
    
    @Test
    @DisplayName("More warriors - higher probability.")
    void testSuccessProbability_MoreFighters_HigherChance(){
        
        double smallTeam = simulator.testCalculateSuccessProbability(10, 50);
        double bigTeam = simulator.testCalculateSuccessProbability(50, 50);
        assertTrue(bigTeam >= smallTeam);
    }
    
    @Test
    @DisplayName("The smaller the scale of the settlement, the higher the probability.")
    void testSuccessProbability_SmallerScale_HigherChance(){
        
        double bigScale = simulator.testCalculateSuccessProbability(50, 100);
        double smallScale = simulator.testCalculateSuccessProbability(50, 20);
        assertTrue(smallScale >= bigScale);
    }
    
    
}
