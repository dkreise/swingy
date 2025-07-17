package ro.academyplus.swingy;

import ro.academyplus.swingy.view.*;
import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.utils.DatabaseManager;

import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.SQLException;
// import java.sql.Statement;

public class Main {
    // private final static GameView gameView;

    public static void main(String[] args) {
        GameView gameView;
        DatabaseManager dbManager;

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

        try {
            dbManager = new DatabaseManager();
            System.out.println("✅ Connection to db established.");
        } catch (SQLException e) {
            System.out.println("❌ Connection to db failed: " + e.getMessage());
        }

        GameController gameController = new GameController(gameView);
        gameController.startGame();

        // close the scanner if using ConsoleView
        if (gameView instanceof ConsoleView) {
            ((ConsoleView) gameView).closeScanner();
        }
    }
}
