package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.model.hero.Hero;
import ro.academyplus.swingy.model.villain.Villain;
import ro.academyplus.swingy.model.artifact.*;
import ro.academyplus.swingy.controller.factories.*;

import java.util.Random;

public class BattleManager {
    private static final Random random = new Random();

    public static BattleResult simulateBattle(Hero hero, Villain villain) {
        // Add a bit of randomness ("luck")
        int heroScore = hero.getTotalAttack() + hero.getTotalDefense() + random.nextInt(10);
        int villainScore = villain.getAttack() + villain.getDefense() + random.nextInt(10);

        boolean heroWon = heroScore >= villainScore;
        int xp = 0;
        Artifact artifact = null;

        if (heroWon) {
            xp = calculateXP(villain);
            artifact = maybeDropArtifact(villain);
        } else {
            // Optionally reduce HP or mark hero as dead elsewhere
        }

        // BETTER SIMULATION:
        // int heroHp = hero.getTotalHitPoints();
        // int villainHp = villain.getHitPoints();

        // while (heroHp > 0 && villainHp > 0) {
        //     // Hero attacks
        //     int damageToVillain = Math.max(0, hero.getTotalAttack() - villain.getDefense());
        //     villainHp -= damageToVillain;

        //     if (villainHp <= 0) break;

        //     // Villain attacks
        //     int damageToHero = Math.max(0, villain.getAttack() - hero.getTotalDefense());
        //     heroHp -= damageToHero;
        // }

        // return heroHp > 0;

        return new BattleResult(heroWon, xp, artifact);
    }

    private static int calculateXP(Villain villain) {
        return villain.getPower() * 100 + villain.getLevel() * 50;
    }

    private static Artifact maybeDropArtifact(Villain villain) {
        if (random.nextDouble() < 0.5) {
            return ArtifactFactory.generateRandomArtifact(villain.getLevel());
        }
        return null;
    }

    public static boolean tryLuckToRun() {
        // 50% chance to escape
        return random.nextDouble() < 0.5;
    }
}
