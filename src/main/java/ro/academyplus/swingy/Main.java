package ro.academyplus.swingy;

import ro.academyplus.swingy.view.*;

public class Main {
    // private final static GameView gameView;

    public static void main(String[] args) {
        GameView gameView;

        if (args.length == 0) {
            System.out.println("Usage: java -jar swingy.jar [console|gui]");
            return;
        }

        switch (args[0]) {
            case "console":
                System.out.println("Launching console version...");
                gameView = new ConsoleView();
                break;
            case "gui":
                System.out.println("Launching GUI version...");
                gameView = new GuiView();
                break;
            default:
                System.out.println("Invalid mode: " + args[0]);
                return;
        }

        if (gameView != null) {
            gameView.startGame();
            gameView.showMessage("Welcome to Swingy!");
        }
    }
}
