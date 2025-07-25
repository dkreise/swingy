package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.model.hero.*;
import ro.academyplus.swingy.model.map.*;
import ro.academyplus.swingy.model.artifact.*;

import java.util.List;
import java.util.ArrayList;

public interface GameView {
    void setController(GameController controller);
    void showWelcomeMessage();
    void startGame();
    void showMainMenu();
    void showHeroCreationMenu();
    void showHeroSelectionMenu(List<Hero> heroes);
    void showHeroStats(Hero hero);
    void startNewGame(Hero hero, GameMap gameMap);
    void askDirection(Position heroPosition);
    void updateHeroPosition(Position heroPosition, Position oldPosition, boolean hasVillain);
    void notifyAboutArtifactDropped(Artifact artifactDropped);
    void showMessage(String message);
}
