package ro.academyplus.swingy.controller.factories;

import ro.academyplus.swingy.model.villain.Villain;
import ro.academyplus.swingy.model.villain.VillainClass;

import java.util.Random;

public class VillainFactory {
    private static final Random random = new Random();

    public static Villain createRandomVillain(int heroLevel) {
        VillainClass type = randomVillainClass();
        int level = generateLevelNearHero(heroLevel);
        // int power = 1 + random.nextInt(5);

        // Stats can be generated based on level, type, etc.
        int attack = type.getBaseAttack() + level + random.nextInt(3);
        int defense = type.getBaseDefense() + level + random.nextInt(3);
        int hp = type.getBaseHp() + level * 4 + random.nextInt(6);

        return new Villain(type, level, attack, defense, hp);
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
