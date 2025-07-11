package ro.academyplus.swingy.model.hero;

public enum HeroClass {
    WARRIOR("Warrior", 10, 8, 100),
    MAGE("Mage", 15, 4, 70),
    ROGUE("Rogue", 12, 6, 80),
    VALKYRIE("Valkyrie", 11, 9, 95),
    ENCHANTRESS("Enchantress", 14, 5, 75),
    MOONBLADE("Moonblade", 13, 6, 85);

    private final String displayName;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseHitPoints;

    HeroClass(String displayName, int baseAttack, int baseDefense, int baseHitPoints) {
        this.displayName = displayName;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseHitPoints = baseHitPoints;
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

    public int getBaseHitPoints() {
        return baseHitPoints;
    }
}
