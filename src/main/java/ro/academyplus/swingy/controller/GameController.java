package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.view.GameView;
import ro.academyplus.swingy.controller.factories.VillainFactory;
import ro.academyplus.swingy.model.hero.*;
import ro.academyplus.swingy.model.map.*;
import ro.academyplus.swingy.model.villain.Villain;

import javax.swing.*;
import java.util.Map;
import java.util.Random;

public class GameController {
    private final GameView gameView;
    private Hero hero;
    private GameMap gameMap;

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

    public void handleHeroCreation(JTextField nameField, Map<JRadioButton, HeroClass> classButtons) {
        String name = nameField.getText().trim();
        HeroClass selectedClass = null;

        for (Map.Entry<JRadioButton, HeroClass> entry : classButtons.entrySet()) {
            if (entry.getKey().isSelected()) {
                selectedClass = entry.getValue();
                break;
            }
        }

        // // Validation
        // if (selectedClass == null) {
        //     JOptionPane.showMessageDialog(frame, "Please select a hero class.");
        //     return;
        // }

        // if (name.isEmpty()) {
        //     JOptionPane.showMessageDialog(frame, "Hero name cannot be empty.");
        //     return;
        // }

        Hero hero = new Hero(name, selectedClass);
        this.setHero(hero);
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        gameView.showHeroStats(this.hero);
    }

    public void handleGameStart() {
        this.gameMap = new GameMap(hero.getLevel());
        populateVillains();
        gameMap.printVillains();

        gameView.startNewGame(hero, gameMap);
    }

    public void startNewMove() {
        Position heroPosition = gameMap.getHeroPosition();
        boolean victory = gameMap.heroIsAtEdge();
        if (victory) {
            gameView.showMessage("Congratulations! You've reached the edge of the gameMap and won the game!");
            return;
        }
        // int mapSize = gameMap.getSize();
        gameView.askDirection(heroPosition);
    }

    public void handleHeroMovement(Direction direction) {
        Position oldPosition = gameMap.getHeroPosition();
        gameMap.moveHero(direction);
        Position newPosition = gameMap.getHeroPosition();
        boolean hasVillain = gameMap.hasVillainAt(newPosition);
        gameView.updateHeroPosition(newPosition, oldPosition, hasVillain);
    }

    public void tryToRun(Position oldPosition) {
        boolean success = BattleManager.tryLuckToRun();
        if (success) {
            gameView.showMessage("You successfully ran away from the villain!");
            gameMap.setHeroPosition(oldPosition); // Reset to old position
            gameView.updateHeroPosition(oldPosition, oldPosition, false);
        } else {
            gameView.showMessage("You failed to run away! Prepare for battle!");
            startBattle();
        }
    }

    public void startBattle() {
        Villain villain = gameMap.getVillainAt(gameMap.getHeroPosition());
        if (villain == null) {
            gameView.showMessage("No villain at this position to battle!");
            return;
        }
        BattleResult result = BattleManager.simulateBattle(hero, villain);
        if (result.isHeroWon()) {
            gameView.showMessage("You defeated the villain: " + villain.getName() + "!");
            gameMap.removeVillainAt(gameMap.getHeroPosition()); 
            // add experience and maybe a artifact
            startNewMove(); // Continue the game
        } else {
            gameView.showMessage("You were defeated by the villain: " + villain.getName() + "!");
            gameView.showMessage("GAME OVER! Your hero " + hero.getName() + " has fallen.");
        }
    }

    public void populateVillains() {
        Random random = new Random();
        int size = gameMap.getSize();
        int heroLevel = hero.getLevel();
        int numberOfVillains = size + random.nextInt(size / 2); // e.g. ~40 villains for 39x39 gameMap

        for (int i = 0; i < numberOfVillains; ) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            Position position = new Position(x, y);

            // Avoid center (hero spawn) and occupied tiles
            if (gameMap.hasVillainAt(position) || gameMap.isCenter(position)) {
                continue;
            }

            Villain villain = VillainFactory.createRandomVillain(heroLevel);
            gameMap.placeVillain(position, villain);
            i++;
        }
    }
}
