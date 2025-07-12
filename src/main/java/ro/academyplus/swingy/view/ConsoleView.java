package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.model.hero.*;
import ro.academyplus.swingy.model.artifact.*;
import ro.academyplus.swingy.model.map.*;

import java.util.Scanner;

public class ConsoleView implements GameView {
    private GameController controller;
    private final Scanner scanner = new Scanner(System.in);

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

        while (choice != 1 && choice != 2) {
            System.out.println("MAIN MENU:");
            System.out.println("1. Select Existing Hero");
            System.out.println("2. Create New Hero");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 1) {
                    controller.onHeroSelect();
                } else if (choice == 2) {
                    controller.onHeroCreate();
                } else if (choice == 3) {
                    scanner.close();
                    System.out.println("Exiting the game. Goodbye!");
                    System.exit(0);
                } else {
                    System.out.println("!!!\nInvalid choice. Please try again.\n!!!");
                }
            } else {
                System.out.println("!!!\nInvalid input. Please enter a number.\n!!!");
                scanner.next(); // Clear invalid input
            }
        }
    }

    @Override
    public void showHeroSelectionMenu() {
        System.out.println("HERO CLASSES:");
        HeroClass.printHeroClasses();
        int choice = 0;

        while (choice < 1 || choice > HeroClass.values().length) {
            System.out.print("Select a hero class by number: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > HeroClass.values().length) {
                    System.out.println("!!!\nInvalid choice. Please try again.\n!!!");
                }
            } else {
                System.out.println("!!!\nInvalid input. Please enter a number.\n!!!");
                scanner.next(); // Clear invalid input
            }
        }

        HeroClass selectedClass = HeroClass.values()[choice - 1];
        System.out.print("Enter hero name: ");
        String heroName = scanner.next();
        Hero hero = new Hero(heroName, selectedClass);
        controller.setHero(hero);
    }

    @Override
    public void showHeroStats(Hero hero) {
        System.out.println("HERO STATS:");
        System.out.printf("Name: %s%n", hero.getName());
        System.out.printf("Class: %s%n", hero.getHeroClass().getDisplayName());
        System.out.printf("Level: %d%n", hero.getLevel());
        System.out.printf("Experience: %d%n", hero.getExperience());
        System.out.printf("Attack: %d%n", hero.getTotalAttack());
        System.out.printf("Defense: %d%n", hero.getTotalDefense());
        System.out.printf("Hit Points: %d%n", hero.getTotalHitPoints());

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
    public void startGameLoop(int mapSize, Position heroPosition) {
        System.out.println("\nSTARTING THE GAME");
        System.out.println("Size of the map: " + mapSize + "x" + mapSize);
        System.out.println("Your position: " + heroPosition);
        Direction dir = askDirection();
        System.out.println("You've chosen to go " + dir);
    }

    public Direction askDirection() {
        Direction dir = null;

        while (dir == null) {
            System.out.print("Choose direction [N]orth, [S]outh, [E]ast, [W]est: ");
            

            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim().toUpperCase();
                if (input.isEmpty()) {
                    System.out.println(" no empty please ");
                    continue;
                }
                switch (input.charAt(0)) {
                    case 'N' -> { return Direction.NORTH; }
                    case 'S' -> { return Direction.SOUTH; }
                    case 'E' -> { return Direction.EAST; }
                    case 'W' -> { return Direction.WEST; }
                    default -> System.out.println("Invalid input. Please enter N/S/E/W.");
                }
            }
        }
        return dir;
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
