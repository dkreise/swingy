package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.model.hero.*;
import ro.academyplus.swingy.model.map.*;
import ro.academyplus.swingy.model.artifact.*;

public interface GameView {
    void setController(GameController controller);
    void showWelcomeMessage();
    void startGame();
    void showMainMenu();
    void showHeroSelectionMenu();
    // void showHeroCreationMenu();
    void showHeroStats(Hero hero);
    void startNewGame(Hero hero, GameMap gameMap);
    void askDirection(Position heroPosition);
    void updateHeroPosition(Position heroPosition, Position oldPosition, boolean hasVillain);
    void notifyAboutArtifactDropped(Artifact artifactDropped);
    void showMessage(String message);
}
