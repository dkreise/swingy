package ro.academyplus.swingy.model.hero;

import ro.academyplus.swingy.model.artifact.*;

public class Hero {
    private String name;
    private String heroClass;
    private int level;
    private int experience;
    private int attack;
    private int defense;
    private int hitPoints;
    private Weapon weapon;
    private Armor armor;
    private Helm helm;

    public Hero(String name, String heroClass) {
        this.name = name;
        this.heroClass = heroClass;
        this.level = 0;
        this.experience = 0;
    }

    public int getTotalAttack() {
        int weaponBonus = (weapon != null) ? weapon.getBonus() : 0;
        return attack + weaponBonus;
    }

    public int getTotalDefense() {
        int armorBonus = (armor != null) ? armor.getBonus() : 0;
        return defense + armorBonus;
    }

    public int getTotalHitPoints() {
        int helmBonus = (helm != null) ? helm.getBonus() : 0;
        return hitPoints + helmBonus;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }
}
