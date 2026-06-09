package com.example.planner.enums;
/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public enum SettlementType {
    
    MONASTERY("Monastery", 0.7),
    VILLAGE("Village", 0.8),
    TOWN("Town", 1.0),
    FORT("Fort", 1.3),
    PORT("Port", 1.1);
    
    private String displayName;
    private double difficultyModifier;
    
    SettlementType(String displayName, double difficultyModifier){
        this.displayName = displayName;
        this.difficultyModifier = difficultyModifier;
    }
    
    public String getDisplayName(){
        return displayName;
    }
    
    public double getDifficultyModifier(){
        return difficultyModifier;
    }
}
