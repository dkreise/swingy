package ro.academyplus.swingy.view;

public class ConsoleView implements GameView {

    @Override
    public void showWelcomeMessage() {
        String message = "Welcome to Swingy Game!";
        int length = message.length();

        System.out.println("+" + "-".repeat(length + 2) + "+");
        System.out.println("| " + message + " |");
        System.out.println("+" + "-".repeat(length + 2) + "+");
    }

    @Override
    public void startGame() {
        showWelcomeMessage();
        // Additional logic to start the game can be added here
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
    
}
