package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.model.hero.Hero;
import ro.academyplus.swingy.model.villain.Villain;
import ro.academyplus.swingy.model.artifact.*;
import ro.academyplus.swingy.controller.factories.*;

import java.util.Random;

public class BattleManager {
    private static final Random random = new Random();

    // public static BattleResult simulateBattle(Hero hero, Villain villain) {
    //     // Add a bit of randomness ("luck")
    //     int heroScore = hero.getTotalAttack() + hero.getTotalDefense() + random.nextInt(10);
    //     int villainScore = villain.getAttack() + villain.getDefense() + random.nextInt(10);

    //     boolean heroWon = heroScore >= villainScore;
    //     int xp = 0;
    //     Artifact artifact = null;

    //     if (heroWon) {
    //         xp = calculateXP(villain);
    //         artifact = maybeDropArtifact(villain);
    //     } else {
    //         // Optionally reduce HP or mark hero as dead elsewhere
    //     }

    //     return new BattleResult(heroWon, xp, artifact);
    // }

    // vil: 19, 12, 87 level:3

    public static BattleResult simulateBattle(Hero hero, Villain villain) {
        int heroHp = hero.getTotalHitPoints();
        int villainHp = villain.getHitPoints();

        StringBuilder battleLog = new StringBuilder();

        while (heroHp > 0 && villainHp > 0) {
            // Hero attacks
            int damageToVillain = Math.max(0, hero.getTotalAttack() - villain.getDefense());
            damageToVillain += random.nextInt(5); // randomness
            villainHp -= damageToVillain;
            battleLog.append("Hero hits for ").append(damageToVillain).append(" dmg\n");

            if (villainHp <= 0) break;

            // Villain attacks
            int damageToHero = Math.max(0, villain.getAttack() - hero.getTotalDefense());
            damageToHero += random.nextInt(5);
            heroHp -= damageToHero;
            battleLog.append("Villain hits for ").append(damageToHero).append(" dmg\n");
        }

        boolean heroWon = heroHp > 0;
        int xp = heroWon ? calculateXP(villain) : 0;
        Artifact artifact = heroWon ? maybeDropArtifact(villain) : null;

        // Optional: Log to console
        System.out.println(battleLog);

        return new BattleResult(heroWon, xp, artifact);
    }

    private static int calculateXP(Villain villain) {
        return 50 * villain.getLevel() + (villain.getAttack() + villain.getDefense()) * 5;
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

    public static int estimatedDangerLevel(Hero hero, Villain villain) {
        double villainPower = villain.getAttack() + villain.getDefense() + villain.getHitPoints();
        double heroPower = hero.getTotalAttack() + hero.getTotalDefense() + hero.getTotalHitPoints();

        double dangerLevel = villainPower / heroPower; // if dangerLevel is close to 1 → evenly matched
        System.out.println("danger: " + dangerLevel);

        if (dangerLevel < 0.8) {
            return 1;
        } else if (dangerLevel < 1) {
            return 2;
        } else {
            return 3;
        }
    }
}
