package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;

public interface GameView {
    void setController(GameController controller);
    void showWelcomeMessage();
    void startGame();
    void showMainMenu();
    void showMessage(String message);
}
