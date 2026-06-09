package com.example.planner.DTO;

import com.example.planner.enums.SettlementType;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public class SettlementDto {
        
    private Long id;
    private String name;
    private SettlementType type;
    private double x;
    private double y;
    private int scale;
    private int baseLoot;
    private String description;
    
    public SettlementDto() {}
    
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
    public SettlementType getType(){ 
        return type; 
    }
    public void setType(SettlementType type){ 
        this.type = type; 
    }
    public double getX(){ 
        return x; 
    }
    public void setX(double x){ 
        this.x = x; 
    }
    public double getY(){ 
        return y; 
    }
    public void setY(double y){ 
        this.y = y; 
    }
    public int getScale(){ 
        return scale; 
    }
    public void setScale(int scale){ 
        this.scale = scale; 
    }
    public int getBaseLoot(){ 
        return baseLoot; 
    }
    public void setBaseLoot(int baseLoot){ 
        this.baseLoot = baseLoot; 
    }
    public String getDescription(){ 
        return description; 
    }
    public void setDescription(String description){ 
        this.description = description; 
    }
}

