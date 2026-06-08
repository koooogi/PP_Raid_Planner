/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public enum LootType {
    
    GOLD("Золото", 1.0),
    SILVER("Серебро", 0.8),
    WEAPONS("Оружие", 0.6),
    JEWELRY("Драгоценности", 1.2),
    SUPPLIES("Припасы", 0.5),
    SLAVES("Рабы", 0.7);
    
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
