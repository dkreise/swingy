package ro.academyplus.swingy.model.villain;

public enum VillainClass {
    GOBLIN("Goblin", 8, 4, 60),
    DARK_FAIRY("Dark Fairy", 10, 3, 50),
    CURSED_MERMAID("Cursed Mermaid", 9, 6, 70),
    VAMPIRE("Vampire", 12, 7, 80),
    DEMON_CAT("Demon Cat", 11, 5, 65),
    GLITCH_PRINCESS("Glitch Princess", 13, 6, 75);

    private final String displayName;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseHp;

    VillainClass(String displayName, int baseAttack, int baseDefense, int baseHp) {
        this.displayName = displayName;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseHp = baseHp;
    }

    public String getDisplayName() {
        return displayName; 
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getBaseHp() {
        return baseHp;
    }
}
