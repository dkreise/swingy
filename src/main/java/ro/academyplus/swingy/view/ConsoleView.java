package ro.academyplus.swingy.view;

public class ConsoleView implements GameView {

    @Override
    public void startGame() {
        System.out.println("Starting game in console mode...");
        // Additional logic to start the game can be added here
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
    
}
