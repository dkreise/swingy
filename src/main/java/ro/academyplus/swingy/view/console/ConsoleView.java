package ro.academyplus.swingy.view.console;

import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.model.hero.*;
import ro.academyplus.swingy.model.artifact.*;
import ro.academyplus.swingy.model.map.*;
import ro.academyplus.swingy.view.GameView;

import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class ConsoleView implements GameView {
    private GameController controller;
    private final Scanner scanner = new Scanner(System.in);
    private Hero hero;
    private GameMap gameMap;

    @Override
    public void showWelcomeMessage() {
        String message = "WELCOME TO SWINGY GAME!";
        printTitle(message);
    }

    @Override
    public void showMainMenu() {
        int choice = 0;

        while (choice < 1 || choice > 3) {
            printTitle("MAIN MENU");
            System.out.println("1. Select Existing Hero");
            System.out.println("2. Create New Hero");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice: ");

            choice = checkNextInt(1, 3);
        }

        switch (choice) {
            case 1:
                controller.onHeroSelect();
                break;
            case 2:
                controller.onHeroCreate();
                break;
            case 3:
                scanner.close();
                System.out.println("Exiting the game. Goodbye!");
                System.exit(0);
                break;
        }
    }

    @Override
    public void showHeroCreationMenu() {
        printTitle("HERO CLASSES");
        HeroClass.printHeroClasses();
        int choice = 0;

        while (choice < 1 || choice > HeroClass.values().length) {
            System.out.print("\nSelect a hero class by number: ");
            choice = checkNextInt(1, HeroClass.values().length);
        }

        HeroClass selectedClass = HeroClass.values()[choice - 1];
        System.out.print("\nEnter hero name: ");
        String heroName = scanner.nextLine();
        Hero hero = new Hero(heroName, selectedClass);
        controller.setHero(hero, true);
    }

    @Override
    public void showHeroSelectionMenu(List<Hero> heroes) {
        printTitle("YOUR HEROES");

        int i = 1;
        for (Hero hero: heroes) {
            System.out.println(i + ". " + hero.getName());
            printHeroStats(hero);
            System.out.println();
            i++;
        }

        int choice = 0;
        while (choice < 1 || choice > heroes.size()) {
            System.out.print("Select a hero by it's number: ");
            choice = checkNextInt(1, heroes.size());
        }
        Hero hero = heroes.get(choice - 1);
        controller.setHero(hero, false);
    }

    @Override
    public void showHeroStats(Hero hero) {
        printTitle("STATS FOR HERO - " + hero.getName());
        printHeroStats(hero);
        controller.handleGameStart();
    }

    @Override
    public void startGame() {
        showWelcomeMessage();
        showMainMenu();
    }

    @Override
    public void startNewGame(Hero hero, GameMap gameMap) {
        this.hero = hero;
        this.gameMap = gameMap;
        // int mapSize = gameMap.getSize();
        Position heroPosition = gameMap.getHeroPosition();
        
        // System.out.println("Starting new game with hero: " + hero.getName());
        askDirection(heroPosition);
    }

    @Override
    public void askDirection(Position heroPosition) {
        printTitle("MOVE");
        System.out.println("Size of the map: " + gameMap.getSize() + "x" + gameMap.getSize());
        System.out.println("Your position: " + heroPosition);
        Direction dir = getDirection();
        // System.out.println("You've chosen to go " + dir);
        controller.handleHeroMovement(dir);
    }

    @Override
    public void updateHeroPosition(Position heroPosition, Position oldPosition, boolean hasVillain) {
        // System.out.println("Size of the map: " + mapSize + "x" + mapSize);
        System.out.println("Your position: " + heroPosition);
        if (hasVillain) {
            // System.out.println("You encountered a villain at this position!");
            // System.out.println("Try you luck!");
            printInfoBox(
                "You encountered a villain at this position!",
                "Try your luck!"
            );
            int choice = askForBattleChoice();
            if (choice == 1) {
                System.out.println("You chose to fight the villain! How brave! Let's start the battle...");
                controller.startBattle();
            } else {
                System.out.println("You chose to run away! Let's see if you can escape...");
                controller.tryToRun(oldPosition);
            }
        } else {
            printInfoBox("No villains here, you can move freely.");
            controller.startNewMove();
        }
    }

    @Override
    public void notifyAboutArtifactDropped(Artifact artifactDropped) {
        ArtifactType type = artifactDropped.getType();

        // System.out.println("\nNEW ARTIFACT! \"" + artifactDropped.getName() + "\" (Bonus: " + artifactDropped.getBonus() + ")");
        // System.out.println("Wow! New " + type.toString() + " has dropped!");
        printInfoBox(
            "NEW ARTIFACT! ",
            "\"" + artifactDropped.getName() + "\" (Bonus: " + artifactDropped.getBonus() + ")",
            "Wow! New " + type.toString() + " has dropped!"
        );
                
        Artifact curArtifact = hero.getArtifactByType(type);
        if (curArtifact != null) {
            System.out.println("Currently you have " + type.toString() + " \"" + curArtifact.getName() + "\" (Bonus: " + curArtifact.getBonus() + ").");
        } else {
            System.out.println("Currently you don't have any " + type.toString() + ".");
        }
        System.out.println("What would you like to do with it?");
        int choice = -1;

        while (choice < 1 || choice > 2) {
            System.out.println("1. Keep");
            System.out.println("2. Leave");
            System.out.print("\nEnter your choice: ");
            choice = checkNextInt(1, 2);
        }

        if (choice == 1) {
            controller.keepNewArtifact(artifactDropped);
        }
    }

    public Direction getDirection() {
        Direction dir = null;

        while (dir == null) {
            System.out.print("\nChoose direction [N]orth, [S]outh, [E]ast, [W]est: ");
            String input = getNextToken().toUpperCase();

            if (input.isEmpty()) {
                System.out.println(" no empty please ");
                continue;
            }

            switch (input) {
                case "N" -> { return Direction.NORTH; }
                case "S" -> { return Direction.SOUTH; }
                case "E" -> { return Direction.EAST; }
                case "W" -> { return Direction.WEST; }
                default -> System.out.println("Invalid input. Please enter N/S/E/W.");
            }
        }
        return dir;
    }

    private int askForBattleChoice() {
        int choice = -1;

        while (choice < 1 || choice > 2) {
            System.out.println("1. Fight the villain");
            System.out.println("2. Run away");
            System.out.print("\nEnter your choice: ");
            choice = checkNextInt(1, 2);
        }

        return choice;
    }

    public String getNextToken() {
        String line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
            String token = line.split("\\s+")[0];
            return token;
        }
        return "";
    }

    private int checkNextInt(int min, int max) {
        int input = -1;

        String line = scanner.nextLine().trim();
        try {
            input = Integer.parseInt(line);
            if (input < min || input > max) {
                System.out.println("!!!\nInvalid input. Please enter a valid number.\n!!!");
                return -1; // Invalid input
            }
        } catch (NumberFormatException e) {
            System.out.println("!!!\nInvalid input. Please enter a number.\n!!!");
        }

        return input; // Valid input or -1 if invalid
    }

    private void printHeroStats(Hero hero) {
        // System.out.println("Name: " + hero.getName());
        System.out.println("Class: " + hero.getHeroClass().getDisplayName());
        System.out.println("Level: " + hero.getLevel());
        System.out.println("Experience: " + hero.getExperience());
        System.out.println("Attack: " + hero.getTotalAttack());
        System.out.println("Defense: " + hero.getTotalDefense());
        System.out.println("Hit Points: " + hero.getTotalHitPoints());

        if (hero.getWeapon() != null) {
            System.out.printf("Weapon: %s (Bonus: %d)%n", hero.getWeapon().getName(), hero.getWeapon().getBonus());
        } else {
            System.out.println("Weapon: None");
        }

        if (hero.getArmor() != null) {
            System.out.printf("Armor: %s (Bonus: %d)%n", hero.getArmor().getName(), hero.getArmor().getBonus());
        } else {
            System.out.println("Armor: None");
        }

        if (hero.getHelm() != null) {
            System.out.printf("Helm: %s (Bonus: %d)%n", hero.getHelm().getName(), hero.getHelm().getBonus());
        } else {
            System.out.println("Helm: None");
        }
    }

    @Override
    public void showMessage(String message) {
        printInfoBox(message);
    }

    private void printTitle(String message) {
        int length = message.length();

        System.out.println("\n╔" + "═".repeat(length + 2) + "╗");
        System.out.println("║ " + message + " ║");
        System.out.println("╚" + "═".repeat(length + 2) + "╝\n");
    }

    private void printInfoBox(String... lines) {
        int maxLength = Arrays.stream(lines).mapToInt(String::length).max().orElse(0);
        System.out.println("+" + "-".repeat(maxLength + 2) + "+");
        for (String line : lines) {
            System.out.printf("| %-"+ maxLength +"s |\n", line);
        }
        System.out.println("+" + "-".repeat(maxLength + 2) + "+");
    }
    
    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
