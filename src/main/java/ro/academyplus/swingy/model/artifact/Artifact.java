package ro.academyplus.swingy.model.artifact;

public interface Artifact {
    public ArtifactType getType();
    public String getName();
    public int getBonus();
    public int getId();
    public void setId(int id);
}
