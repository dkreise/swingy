package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.model.hero.*;

public interface GameView {
    void setController(GameController controller);
    void showWelcomeMessage();
    void startGame();
    void showMainMenu();
    void showHeroSelectionMenu();
    // void showHeroCreationMenu();
    void showHeroStats(Hero hero);
    void showMessage(String message);
}
