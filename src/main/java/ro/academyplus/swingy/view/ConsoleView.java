package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.model.hero.*;
import ro.academyplus.swingy.model.artifact.*;
import ro.academyplus.swingy.model.map.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class ConsoleView implements GameView {
    private GameController controller;
    private final Scanner scanner = new Scanner(System.in);
    private Hero hero;
    private GameMap gameMap;

    @Override
    public void showWelcomeMessage() {
        String message = "Welcome to Swingy Game!";
        int length = message.length();

        System.out.println("+" + "-".repeat(length + 2) + "+");
        System.out.println("| " + message + " |");
        System.out.println("+" + "-".repeat(length + 2) + "+");
    }

    @Override
    public void showMainMenu() {
        int choice = 0;

        while (choice < 1 || choice > 3) {
            System.out.println("MAIN MENU:");
            System.out.println("1. Select Existing Hero");
            System.out.println("2. Create New Hero");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

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
        System.out.println("HERO CLASSES:");
        HeroClass.printHeroClasses();
        int choice = 0;

        while (choice < 1 || choice > HeroClass.values().length) {
            System.out.print("Select a hero class by number: ");
            choice = checkNextInt(1, HeroClass.values().length);
        }

        HeroClass selectedClass = HeroClass.values()[choice - 1];
        System.out.print("Enter hero name: ");
        String heroName = scanner.nextLine();
        Hero hero = new Hero(heroName, selectedClass);
        controller.setHero(hero);
    }

    @Override
    public void showHeroSelectionMenu(List<Hero> heroes) {
        System.out.println("CHOOSE YOUR HERO:");

        int i = 1;
        for (Hero hero: heroes) {
            System.out.print(i + " ");
            showHeroStats(hero);
            System.out.println();
        }
    }

    @Override
    public void showHeroStats(Hero hero) {
        System.out.println("HERO STATS:");
        System.out.println("Name: " + hero.getName());
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

        // TODO: maybe 'Press Enter to start the game'
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
        
        System.out.println("Starting new game with hero: " + hero.getName());
        askDirection(heroPosition);
    }

    @Override
    public void askDirection(Position heroPosition) {
        System.out.println("\nSTARTING NEW MOVE");
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
            System.out.println("You encountered a villain at this position!");
            System.out.println("Try you luck!");
            int choice = askForBattleChoice();
            if (choice == 1) {
                System.out.println("You chose to fight the villain! How brave! Let's start the battle...");
                controller.startBattle();
            } else {
                System.out.println("You chose to run away! Let's see if you can escape...");
                controller.tryToRun(oldPosition);
            }
        } else {
            System.out.println("No villains here, you can move freely.");
            controller.startNewMove();
        }
    }

    @Override
    public void notifyAboutArtifactDropped(Artifact artifactDropped) {
        System.out.println("Wow! New artifact dropped!");
        System.out.println("What would you like to do with it?");
        int choice = -1;

        while (choice < 1 || choice > 2) {
            System.out.println("1. Keep");
            System.out.println("2. Leave");
            System.out.print("Enter your choice: ");
            choice = checkNextInt(1, 2);
        }

        if (choice == 1) {
            controller.keepNewArtifact(artifactDropped);
        }
    }

    public Direction getDirection() {
        Direction dir = null;

        while (dir == null) {
            System.out.print("Choose direction [N]orth, [S]outh, [E]ast, [W]est: ");
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
            System.out.print("Enter your choice: ");
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

    @Override
    public void showMessage(String message) {
        System.out.println(message);
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
