package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.view.GameView;

public class GameController {
    private final GameView gameView;

    public GameController(GameView gameView) {
        this.gameView = gameView;
    }

    public void startGame() {
        gameView.startGame();
    }
}
