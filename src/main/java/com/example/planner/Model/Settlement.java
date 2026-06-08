package com.example.planner.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.example.planner.ENUMs.SettlementType;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
@Entity
@Table(name = "settlements")
public class Settlement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private SettlementType type;
    
    private double x;
    private double y;
    private int scale;
    
    @Column(name = "base_loot")
    private int baseLoot = 1000;
    
    @Column(name = "slave_probability")
    private double slaveProbability = 0.3;
    
    @Column(name = "min_slaves")
    private int minSlaves = 0;
    
    @Column(name = "max_slaves")
    private int maxSlaves = 10;
    
    @Column(length = 500)
    private String description;
    
    public Settlement() {}
    
    public Settlement(String name, SettlementType type, double x, double y, int scale, int baseLoot) {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.baseLoot = baseLoot;
    }
    
    public Long getId(){ 
        return id; 
    }
    public String getName(){ 
        return name; 
    }
    public SettlementType getType(){ 
        return type; 
    }
    public double getX(){ 
        return x; 
    }
    public double getY(){ 
        return y; 
    }
    public int getScale(){ 
        return scale; 
    }
    public int getBaseLoot(){ 
        return baseLoot; 
    }
    public double getSlaveProbability(){ 
        return slaveProbability; 
    }
    public int getMinSlaves(){ 
        return minSlaves; 
    }
    public int getMaxSlaves(){ 
        return maxSlaves; 
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
    public void setType(SettlementType type){ 
        this.type = type; 
    }
    public void setX(double x){ 
        this.x = x; 
    }
    public void setY(double y){ 
        this.y = y; 
    }
    public void setScale(int scale){ 
        this.scale = scale; 
    }
    public void setBaseLoot(int baseLoot){ 
        this.baseLoot = baseLoot; 
    }
    public void setSlaveProbability(double slaveProbability){ 
        this.slaveProbability = slaveProbability; 
    }
    public void setMinSlaves(int minSlaves){ 
        this.minSlaves = minSlaves; 
    }
    public void setMaxSlaves(int maxSlaves){ 
        this.maxSlaves = maxSlaves; 
    }
    public void setDescription(String description){ 
        this.description = description; 
    }
}
