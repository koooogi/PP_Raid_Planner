/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public enum SettlementType {
    
    MONASTERY("Монастырь", 0.7),
    VILLAGE("Деревня", 0.8),
    TOWN("Город", 1.0),
    FORT("Форт", 1.3),
    PORT("Порт", 1.1);
    
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
