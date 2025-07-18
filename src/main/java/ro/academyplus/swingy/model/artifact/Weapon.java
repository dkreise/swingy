package ro.academyplus.swingy.model.artifact;

public class Weapon implements Artifact {
    private final String name;
    private final int attackBonus;
    private int id;

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

    @Override
    public int getId() {
        return id;
    }   

    @Override
    public void setId(int id) {
        this.id = id;
    }
}