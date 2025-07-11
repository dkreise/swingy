package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;

import java.util.Scanner;

public class ConsoleView implements GameView {
    private GameController controller;

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
        Scanner scanner = new Scanner(System.in);
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
                    // close the scanner to avoid resource leak
                    scanner.close();
                    controller.onHeroSelect();
                    
                } else if (choice == 2) {
                    scanner.close();
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
    public void startGame() {
        showWelcomeMessage();
        showMainMenu();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
    
    public void setController(GameController controller) {
        this.controller = controller;
    }
}
