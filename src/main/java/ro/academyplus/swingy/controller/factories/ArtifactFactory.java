package ro.academyplus.swingy.controller.factories;

import ro.academyplus.swingy.model.artifact.*;

import java.util.Random;

public class ArtifactFactory {
    private static final Random random = new Random();

    public static Artifact generateRandomArtifact(int level) {
        ArtifactType type = randomType();
        String name = randomName(type);
        int bonus = level + random.nextInt(5);

        return switch (type) {
            case WEAPON -> new Weapon(name, bonus);
            case ARMOR -> new Armor(name, bonus);
            case HELM -> new Helm(name, bonus);
        };
    }

    private static ArtifactType randomType() {
        ArtifactType[] types = ArtifactType.values();
        return types[random.nextInt(types.length)];
    }

    private static String randomName(ArtifactType type) {
        return switch (type) {
            case WEAPON -> randomFrom("Rose Blade", "Whisper Fang", "Candy Katana", "Sugar Spear");
            case ARMOR -> randomFrom("Velvet Armor", "Crystal Cloak", "Ribbon Suit", "Petal Shield");
            case HELM -> randomFrom("Tiara of Doom", "Bubble Helm", "Fluffy Crown", "Feather Cap");
        };
    }

    private static String randomFrom(String... options) {
        return options[random.nextInt(options.length)];
    }
}
