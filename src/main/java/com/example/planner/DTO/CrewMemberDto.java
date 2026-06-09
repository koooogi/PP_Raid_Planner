package com.example.planner.DTO;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public class CrewMemberDto {

    private Long id;
    private String name;
    private String clan;
    private String gender;
    private int age;
    private int strength;
    private int supplies;
    
    public CrewMemberDto(){}
    
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
    public String getClan(){ 
        return clan; 
    }
    public void setClan(String clan){ 
        this.clan = clan; 
    }
    
    public String getGender(){ 
        return gender; 
    }
    public void setGender(String gender){ 
        this.gender = gender; 
    }
    public int getAge(){ 
        return age; 
    }
    public void setAge(int age){ 
        this.age = age; 
    }
    public int getStrength(){ 
        return strength; 
    }
    public void setStrength(int strength){ 
        this.strength = strength; 
    }
    public int getSupplies(){ 
        return supplies; 
    }
    public void setSupplies(int supplies){ 
        this.supplies = supplies; 
    }
}
