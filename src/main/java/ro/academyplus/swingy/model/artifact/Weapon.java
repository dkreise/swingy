package ro.academyplus.swingy.model.artifact;

public class Weapon implements Artifact {
    private final String name;
    private final int attackBonus;

    public Weapon(String name, int attackBonus) {
        this.name = name;
        this.attackBonus = attackBonus;
    }

    @Override
    public ArtifactType getType() {
        return ArtifactType.WEAPON;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBonus() {
        return attackBonus;
    }
}