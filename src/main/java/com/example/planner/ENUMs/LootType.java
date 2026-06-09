package com.example.planner.enums;
/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public enum LootType {
    
    GOLD("Gold", 1.0),
    SILVER("Silver", 0.8),
    WEAPONS("Weapon", 0.6),
    JEWELRY("Jewelry", 1.2),
    SUPPLIES("Supplies", 0.5),
    SLAVES("Slaves", 0.7);
    
    private final String displayName;
    private final double valueModifier;
    
    LootType(String displayName, double valueModifier){
        this.displayName = displayName;
        this.valueModifier = valueModifier;
    }
    
    public String getDisplayName(){ 
        return displayName; 
    }
    public double getValueModifier(){ 
        return valueModifier; 
    }
}
