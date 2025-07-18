package ro.academyplus.swingy.model.artifact;

public class Helm implements Artifact {
    private final String name;
    private final int hitPointsBonus;
    private int id;

    public Helm(String name, int hitPointsBonus) {
        this.name = name;
        this.hitPointsBonus = hitPointsBonus;
    }

    @Override
    public ArtifactType getType() {
        return ArtifactType.HELM;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBonus() {
        return hitPointsBonus;
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
