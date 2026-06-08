
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
@Table(name = "crew_members")
public class CrewMember {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String clan;
    private String gender;
    private int age;
    
    @Column(name = "strength")
    private int strength = 10;
    
    @Column(name = "supplies")
    private int supplies = 30;
    
    public CrewMember() {}
    
    public CrewMember(String name, String clan, String gender, int age, int strength) {
        this.name = name;
        this.clan = clan;
        this.gender = gender;
        this.age = age;
        this.strength = strength;
    }
    
    public Long getId(){ 
        return id; 
    }
    public String getName(){ 
        return name; 
    }
    public String getClan(){ 
        return clan; 
    }
    public String getGender(){ 
        return gender; 
    }
    public int getAge(){ 
        return age; 
    }
    public int getStrength(){ 
        return strength; 
    }
    public int getSupplies(){ 
        return supplies; 
    }
    
    public void setId(Long id){ 
        this.id = id; 
    }
    public void setName(String name){ 
        this.name = name; 
    }
    public void setClan(String clan){ 
        this.clan = clan; 
    }
    public void setGender(String gender){ 
        this.gender = gender; 
    }
    public void setAge(int age){ 
        this.age = age; 
    }
    public void setStrength(int strength){ 
        this.strength = strength; 
    }
    public void setSupplies(int supplies){ 
        this.supplies = supplies; 
    }
}
