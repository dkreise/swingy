package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.view.GameView;

public class GameController {
    private final GameView gameView;

    public GameController(GameView gameView) {
        this.gameView = gameView;
        this.gameView.setController(this);
    }

    public void startGame() {
        gameView.startGame();
    }

    public void onHeroSelect() {
        System.out.println("Selecting a hero...");
    }

    public void onHeroCreate() {
        System.out.println("Creating a new hero...");
    }
}
