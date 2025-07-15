package ro.academyplus.swingy.model.hero;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import ro.academyplus.swingy.model.artifact.*;

public class Hero {

    @NotBlank(message = "Hero name must not be blank")
    @Size(min = 2, max = 15, message = "Hero name must be between 2 and 15 characters")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Hero name must contain only letters and numbers")
    private String name;

    private HeroClass heroClass;
    private int level;
    private int experience;
    private int attack;
    private int defense;
    private int hitPoints;
    private Weapon weapon;
    private Armor armor;
    private Helm helm;

    public Hero(String name, HeroClass heroClass) {
        this.name = name;
        this.heroClass = heroClass;
        this.level = 1;
        this.experience = 0;
        this.attack = heroClass.getBaseAttack();
        this.defense = heroClass.getBaseDefense();
        this.hitPoints = heroClass.getBaseHitPoints();
        this.weapon = null;
        this.armor = null;
        this.helm = null;
    }

    // TODO: constructor for loading from file - existing heros

    public void addExperience(int xp) {
        this.experience += xp;
        if (newLevel()) {
            this.level++;
        }
    }

    private boolean newLevel() {
        int xpNeeded = level * 1000 + (level - 1) * (level - 1) * 450;
        if (experience >= xpNeeded) {
            return true;
        } else {
            return false;
        }
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void setHelm(Helm helm) {
        this.helm = helm;
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

    public HeroClass getHeroClass() {
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

    public Weapon getWeapon() {
        return weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public Helm getHelm() {
        return helm;
    }

    // public void showHeroStats() {
    //     System.out.println("Hero Name: " + name);
    //     System.out.println("Hero Class: " + heroClass.getDisplayName());
    //     System.out.println("Level: " + level);
    //     System.out.println("Experience: " + experience);
    //     System.out.println("Attack: " + getTotalAttack());
    //     System.out.println("Defense: " + getTotalDefense());
    //     System.out.println("Hit Points: " + getTotalHitPoints());
    //     if (weapon != null) {
    //         System.out.println("Weapon: " + weapon.getName() + " (Bonus: " + weapon.getBonus() + ")");
    //     }
    //     if (armor != null) {
    //         System.out.println("Armor: " + armor.getName() + " (Bonus: " + armor.getBonus() + ")");
    //     }
    //     if (helm != null) {
    //         System.out.println("Helm: " + helm.getName() + " (Bonus: " + helm.getBonus() + ")");
    //     }
    // }
}
