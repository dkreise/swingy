package ro.academyplus.swingy.model.artifact;

public class Armor implements Artifact {
    private final String name;
    private final int defenseBonus;

    public Armor(String name, int defenseBonus) {
        this.name = name;
        this.defenseBonus = defenseBonus;
    }

    @Override
    public ArtifactType getType() {
        return ArtifactType.ARMOR;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBonus() {
        return defenseBonus;
    }
    
}
