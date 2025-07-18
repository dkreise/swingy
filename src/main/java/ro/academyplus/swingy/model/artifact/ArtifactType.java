package ro.academyplus.swingy.model.artifact;

public enum ArtifactType {
    HELM("Helm"),
    ARMOR("Armor"),
    WEAPON("Weapon");

    private final String type;

    ArtifactType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
