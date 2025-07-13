package ro.academyplus.swingy.model.map;

public class GameMap {
    private final int size;
    private Position heroPosition;

    public GameMap(int heroLevel) {
        this.size = (heroLevel - 1) * 5 + 10 - (heroLevel % 2);
        int mid = size / 2;
        this.heroPosition = new Position(mid, mid);
        // TODO: generate villain positions randomly
    }

    public boolean moveHero(Direction dir) {
        int x = heroPosition.getX();
        int y = heroPosition.getY();

        if (heroIsAtEdge()) {
            return false;
        }

        switch (dir) {
            case NORTH:
                y--;
                break;
            case SOUTH:
                y++;
                break;
            case EAST:
                x++;
                break;
            case WEST:
                x--;
                break;
        }

        heroPosition = new Position(x, y);
        return true;
    }

    public boolean heroIsAtEdge() {
        int x = heroPosition.getX();
        int y = heroPosition.getY();

        return x == 0 || y == 0 || x == size - 1 || y == size - 1;
    }

    public int getSize() {
        return this.size;
    }

    public Position getHeroPosition() {
        return this.heroPosition;
    }

    public void setHeroPosition(Position heroPosition) {
        this.heroPosition = heroPosition;
    }
}