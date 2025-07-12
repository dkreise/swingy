package ro.academyplus.swingy.model.map;

public class GameMap {
    private final int size;
    private Position heroPosition;

    public GameMap(int heroLevel) {
        this.size = (heroLevel - 1) * 5 + 10 - (heroLevel % 2);
        int mid = size / 2 + 1;
        this.heroPosition = new Position(mid, mid);
        // TODO: generate villain positions randomly
    }
}