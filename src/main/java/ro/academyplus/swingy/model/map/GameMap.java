package ro.academyplus.swingy.model.map;

import ro.academyplus.swingy.model.villain.Villain;

import java.util.Map;
import java.util.HashMap;

public class GameMap {
    private final int size;
    private Position heroPosition;
    private final Map<Position, Villain> villains = new HashMap<>();

    public GameMap(int heroLevel) {
        this.size = (heroLevel - 1) * 5 + 10 - (heroLevel % 2);
        int mid = size / 2;
        this.heroPosition = new Position(mid, mid);
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

    public boolean hasVillainAt(Position position) {
        return villains.containsKey(position);
    }

    public void placeVillain(Position position, Villain villain) {
        villains.put(position, villain);
    }

    public Villain getVillainAt(Position position) {
        return villains.get(position);
    }

    public void removeVillainAt(Position position) {
        villains.remove(position);
    }

    public boolean isCenter(Position position) {
        int mid = size / 2;
        return position.getX() == mid && position.getY() == mid;
    }

    public void printVillains() {
        System.out.println("Villains on the map:");
        for (Map.Entry<Position, Villain> entry : villains.entrySet()) {
            Position pos = entry.getKey();
            Villain villain = entry.getValue();
            System.out.printf("Position: %s, Villain: %s (Level: %d, Power: %d)\n",
                    pos, villain.getName(), villain.getLevel(), villain.getPower());
        }
    }
}