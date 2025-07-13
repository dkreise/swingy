package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.model.artifact.Artifact;

public class BattleResult {
    private final boolean heroWon;
    private final int xpGained;
    private final Artifact artifactDropped;

    public BattleResult(boolean heroWon, int xpGained, Artifact artifactDropped) {
        this.heroWon = heroWon;
        this.xpGained = xpGained;
        this.artifactDropped = artifactDropped;
    }

    public boolean isHeroWon() {
        return heroWon;
    }

    public int getXpGained() {
        return xpGained;
    }

    public Artifact getArtifactDropped() {
        return artifactDropped;
    }
}

