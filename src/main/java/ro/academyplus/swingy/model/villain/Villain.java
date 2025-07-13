package ro.academyplus.swingy.model.villain;

public class Villain {
    VillainClass type;
    int power;
    String name;
    int level;
    private int attack;
    private int defense;
    private int hitPoints;

    public Villain(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public Villain(VillainClass type, int level, int power, int attack, int defense, int hitPoints) {
        this.type = type;
        this.level = level;
        this.power = power;
        this.attack = attack;
        this.defense = defense;
        this.hitPoints = hitPoints;
        this.name = type.name();
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
}
