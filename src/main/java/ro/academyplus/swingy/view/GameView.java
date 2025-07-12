package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.model.hero.*;
import ro.academyplus.swingy.model.map.*;

public interface GameView {
    void setController(GameController controller);
    void showWelcomeMessage();
    void startGame();
    void showMainMenu();
    void showHeroSelectionMenu();
    // void showHeroCreationMenu();
    void showHeroStats(Hero hero);
    void startGameLoop(int mapSize, Position heroPosition);
    void updateHeroPosition(Position heroPosition);
    void showMessage(String message);
}
