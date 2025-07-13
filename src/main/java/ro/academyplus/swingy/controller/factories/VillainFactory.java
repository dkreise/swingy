package ro.academyplus.swingy.controller.factories;

import ro.academyplus.swingy.model.villain.Villain;
import ro.academyplus.swingy.model.villain.VillainClass;

import java.util.Random;

public class VillainFactory {
    private static final Random random = new Random();

    public static Villain createRandomVillain(int heroLevel) {
        VillainClass type = randomVillainClass();
        int level = generateLevelNearHero(heroLevel);
        int power = 1 + random.nextInt(5);

        // Stats can be generated based on level, type, etc.
        int attack = level * 3 + random.nextInt(5);
        int defense = level * 2 + random.nextInt(4);
        int hp = 20 + level * 5 + random.nextInt(10);

        return new Villain(type, level, power, attack, defense, hp);
    }

    private static VillainClass randomVillainClass() {
        VillainClass[] types = VillainClass.values();
        return types[random.nextInt(types.length)];
    }

    private static int generateLevelNearHero(int heroLevel) {
        int offset = random.nextInt(3) - 1; // -1, 0, or +1
        return Math.max(1, heroLevel + offset);
    }
}
