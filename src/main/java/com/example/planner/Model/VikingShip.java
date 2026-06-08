import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Entity
@Table(name = "viking_ships")
public class VikingShip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @Column(name = "max_rowers")
    private int maxRowers = 10;
    
    @Column(name = "max_cargo")
    private int maxCargo = 8000;
    
    @Column(name = "max_slaves")
    private int maxSlaves = 20;
    
    @Column(name = "base_speed")
    private int baseSpeed = 10;
    
    @Column(name = "food_consumption_per_person")
    private int foodConsumptionPerPerson = 2;
    
    @Column(length = 500)
    private String description;
    
    public VikingShip() {}
    
    public VikingShip(String name, int maxRowers, int maxCargo, int maxSlaves, int baseSpeed, int foodConsumptionPerPerson, String description){
        this.name = name;
        this.maxRowers = maxRowers;
        this.maxCargo = maxCargo;
        this.maxSlaves = maxSlaves;
        this.baseSpeed = baseSpeed;
        this.foodConsumptionPerPerson = foodConsumptionPerPerson;
        this.description = description;
    }
    
    public Long getId(){ 
        return id; 
    }
    public String getName(){ 
        return name; 
    }
    public int getMaxRowers(){ 
        return maxCargo; 
    }
    public int getMaxSlaves(){ 
        return maxSlaves; 
    }
    public int getBaseSpeed(){ 
        return baseSpeed; 
    }
    public int getFoodConsumptionPerPerson(){ 
        return foodConsumptionPerPerson; 
    }
    public String getDescription(){ 
        return description; 
    }
    
    public void setId(Long id){ 
        this.id = id; 
    }
    public void setName(String name){ 
        this.name = name; 
    }
    public void setMaxRowers(int maxRowers){ 
        this.maxRowers = maxRowers; 
    }
    public void setMaxCargo(int maxCargo){ 
        this.maxCargo = maxCargo; 
    }
    public void setMaxSlaves(int maxSlaves){ 
        this.maxSlaves = maxSlaves; 
    }
    public void setBaseSpeed(int baseSpeed){ 
        this.baseSpeed = baseSpeed; 
    }
    public void setFoodConsumptionPerPerson(int foodConsumptionPerPerson){ 
        this.foodConsumptionPerPerson = foodConsumptionPerPerson; 
    }
    public void setDescription(String description){ 
        this.description = description; 
    }
}
