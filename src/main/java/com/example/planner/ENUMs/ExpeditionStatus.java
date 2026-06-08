package com.example.planner.ENUMs;
/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public enum ExpeditionStatus {
    
    PLANNED("Planned"),
    SIMULATED("Simulated"),
    SUCCESS("Success"),
    FAILED("Failed");
    
    private final String displayName;
    
    ExpeditionStatus(String displayName){
        this.displayName = displayName;
    }
    
    public String getDisplayName(){ 
        return displayName; 
    }
}
