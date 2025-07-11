package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.view.GameView;
import ro.academyplus.swingy.model.hero.*;

public class GameController {
    private final GameView gameView;
    private Hero hero;

    public GameController(GameView gameView) {
        this.gameView = gameView;
        this.gameView.setController(this);
    }

    public void startGame() {
        gameView.startGame();
    }

    public void onHeroSelect() {
        System.out.println("Selecting a hero...\n");
    }

    public void onHeroCreate() {
        System.out.println("Creating a new hero...\n");
        gameView.showHeroSelectionMenu();
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        gameView.showHeroStats(this.hero);
    }
}
