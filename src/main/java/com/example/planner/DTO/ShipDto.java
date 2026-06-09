package com.example.planner.DTO;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public class ShipDto {
    
    private Long id;
    private String name;
    private int maxRowers;
    private int maxCargo;
    private int maxSlaves;
    private int baseSpeed;
    private int foodConsumptionPerPerson;
    private String description;
    
    public ShipDto(){}
    
    public Long getId(){ 
        return id; 
    }
    public void setId(Long id){ 
        this.id = id; 
    }
    
    public String getName(){ 
        return name; 
    }
    public void setName(String name){ 
        this.name = name; 
    }
    
    public int getMaxRowers(){ 
        return maxRowers; 
    }
    public void setMaxRowers(int maxRowers){ 
        this.maxRowers = maxRowers; 
    }
    
    public int getMaxCargo(){ 
        return maxCargo; 
    }
    public void setMaxCargo(int maxCargo){ 
        this.maxCargo = maxCargo; 
    }
    
    public int getMaxSlaves(){ 
        return maxSlaves; 
    }
    public void setMaxSlaves(int maxSlaves){ 
        this.maxSlaves = maxSlaves; 
    }
    
    public int getBaseSpeed(){ 
        return baseSpeed; 
    }
    public void setBaseSpeed(int baseSpeed){ 
        this.baseSpeed = baseSpeed; 
    }
    
    public int getFoodConsumptionPerPerson(){ 
        return foodConsumptionPerPerson; 
    }
    public void setFoodConsumptionPerPerson(int foodConsumptionPerPerson){
        this.foodConsumptionPerPerson = foodConsumptionPerPerson;
    }
    
    public String getDescription(){ 
        return description; 
    }
    public void setDescription(String description){ 
        this.description = description; 
    }
}
