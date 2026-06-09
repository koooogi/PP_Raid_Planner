package com.example.planner.config;

import com.example.planner.Model.CrewMember;
import com.example.planner.Model.Settlement;
import com.example.planner.Model.VikingShip;
import com.example.planner.Repository.CrewRepository;
import com.example.planner.Repository.SettlementRepository;
import com.example.planner.Repository.ShipRepository;
import com.example.planner.enums.SettlementType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Component
public class DataLoader implements CommandLineRunner{
    
    @Autowired
    private ShipRepository shipRepository;
    
    @Autowired
    private CrewRepository crewRepository;
    
    @Autowired
    private SettlementRepository settlementRepository;
    
    @Override
    public void run(String... args) throws Exception {
        loadShips();
        loadCrewMembers();
        loadSettlements();
    }

    private void loadShips() throws Exception{
        if (shipRepository.count() > 0){
            System.out.println("Ships already loaded, skip.");
            return;
        }
        
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream inputStream = getClass().getResourceAsStream("/data/viking-ships.yaml");
        
        if (inputStream == null) {
            System.out.println("ERROR: viking-ships.yaml not found!");
            return;
        }
        
        Map<String, List<Map<String, Object>>> data = mapper.readValue(inputStream, Map.class);
        List<Map<String, Object>> ships = data.get("ships");
        
        for(Map<String, Object> shipData : ships){
            VikingShip ship = new VikingShip();
            ship.setName((String) shipData.get("name"));
            ship.setMaxRowers((Integer) shipData.get("maxRowers"));
            ship.setMaxCargo((Integer) shipData.get("maxCargo"));
            ship.setMaxSlaves((Integer) shipData.get("maxSlaves"));
            ship.setBaseSpeed((Integer) shipData.get("baseSpeed"));
            ship.setFoodConsumptionPerPerson((Integer) shipData.get("foodConsumptionPerPerson"));
            ship.setDescription((String) shipData.get("description"));
            shipRepository.save(ship);
        }
        
        System.out.println("Loaded " + ships.size() + " ships from YAML");
    }
    
    private void loadCrewMembers() throws Exception{
        if(crewRepository.count() > 0){
            System.out.println("Crew already loaded, skip.");
            return;
        }
        
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream inputStream = getClass().getResourceAsStream("/data/crew-members.yaml");
        
        if(inputStream == null){
            System.out.println("ERROR: crew-members.yaml not found!");
            return;
        }
        
        Map<String, List<Map<String, Object>>> data = mapper.readValue(inputStream, Map.class);
        List<Map<String, Object>> crewList = data.get("crew");
        
        for(Map<String, Object> crewData : crewList){
            CrewMember crew = new CrewMember();
            crew.setName((String) crewData.get("name"));
            crew.setClan((String) crewData.get("clan"));
            crew.setGender((String) crewData.get("gender"));
            crew.setAge((Integer) crewData.get("age"));
            crew.setStrength((Integer) crewData.get("strength"));
            crew.setSupplies((Integer) crewData.get("supplies"));
            crewRepository.save(crew);
        }
        
        System.out.println("Loaded " + crewList.size() + " crew members from YAML.");
    }
    
    private void loadSettlements() throws Exception {
        if (settlementRepository.count() > 0) {
            System.out.println("Settlements already loaded, skip.");
            return;
        }
        
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream inputStream = getClass().getResourceAsStream("/data/settlements.yaml");
        
        if (inputStream == null) {
            System.out.println("ERROR: settlements.yaml not found!");
            return;
        }
        
        Map<String, List<Map<String, Object>>> data = mapper.readValue(inputStream, Map.class);
        List<Map<String, Object>> settlementsList = data.get("settlements");
        
        for (Map<String, Object> settlementData : settlementsList) {
            Settlement settlement = new Settlement();
            settlement.setName((String) settlementData.get("name"));
            settlement.setType(SettlementType.valueOf((String) settlementData.get("type")));
            settlement.setX((Double) settlementData.get("x"));
            settlement.setY((Double) settlementData.get("y"));
            settlement.setScale((Integer) settlementData.get("scale"));
            settlement.setBaseLoot((Integer) settlementData.get("baseLoot"));
            settlement.setSlaveProbability((Double) settlementData.get("slaveProbability"));
            settlement.setMinSlaves((Integer) settlementData.get("minSlaves"));
            settlement.setMaxSlaves((Integer) settlementData.get("maxSlaves"));
            settlement.setDescription((String) settlementData.get("description"));
            settlementRepository.save(settlement);
        }
        
        System.out.println("Loaded " + settlementsList.size() + " settlements from YAML");
    }
}
