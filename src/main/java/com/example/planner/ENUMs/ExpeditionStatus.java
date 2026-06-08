/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public enum ExpeditionStatus {
    
    PLANNED("Запланирован"),
    SIMULATED("Симулирован"),
    SUCCESS("Успешен"),
    FAILED("Провален");
    
    private final String displayName;
    
    ExpeditionStatus(String displayName){
        this.displayName = displayName;
    }
    
    public String getDisplayName(){ 
        return displayName; 
    }
}
