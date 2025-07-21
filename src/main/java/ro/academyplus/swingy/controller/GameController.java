package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.view.GameView;
import ro.academyplus.swingy.controller.factories.VillainFactory;
import ro.academyplus.swingy.model.hero.*;
import ro.academyplus.swingy.model.map.*;
import ro.academyplus.swingy.model.artifact.*;
import ro.academyplus.swingy.model.villain.Villain;
import ro.academyplus.swingy.utils.ValidationUtils;
import ro.academyplus.swingy.model.dao.*;

import javax.swing.*;

import java.sql.Connection;
import java.util.Map;
import java.util.Random;
import java.util.List;

public class GameController {
    private final GameView gameView;
    // private final Connection connection;
    private final HeroDAO heroDAO;
    private final ArtifactDAO artifactDAO;
    private Hero hero;
    private GameMap gameMap;

    public GameController(GameView gameView, Connection connection) {
        this.gameView = gameView;
        this.gameView.setController(this);
        // this.connection = connection;
        this.artifactDAO = new ArtifactDAO(connection);
        this.heroDAO = new HeroDAO(connection, artifactDAO);
    }

    public void startGame() {
        gameView.startGame();
    }

    public void onHeroSelect() {
        // System.out.println("Selecting a hero...\n");
        List<Hero> heroes;
        try {
            heroes = heroDAO.getAllHeroes();
            // gameView.showHeroSelectionMenu(heroes);
        } catch (Exception e) {
            System.out.println("Error fetching heroes: " + e.getMessage());
            return;
        }
        gameView.showHeroSelectionMenu(heroes);
    }

    public void onHeroCreate() {
        // System.out.println("Creating a new hero...\n");
        gameView.showHeroCreationMenu();
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

        Hero hero = new Hero(name, selectedClass);
        this.setHero(hero, true);
    }

    public void setHero(Hero hero, boolean created) {
        if (ValidationUtils.validate(hero)) {
            this.hero = hero;
            if (created) {
                try {
                    int heroId = heroDAO.insertHero(hero);
                    this.hero.setId(heroId);
                    System.out.println("Hero created successfully with ID: " + heroId);
                } catch (Exception e) {
                    System.out.println("Error creating hero: " + e.getMessage());
                    return;
                }
            }
            gameView.showHeroStats(this.hero);
        } else {
            System.out.println("Invalid hero name. Please try again.");
            this.onHeroCreate();
        }
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
            gameView.showMessage("Congratulations! You've reached the edge of the game map and won the game!");
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

            updateXpAndLevel(result);

            Artifact artifactDropped = result.getArtifactDropped();
            if (artifactDropped != null) {
                gameView.notifyAboutArtifactDropped(artifactDropped);
            }

            startNewMove(); // Continue the game
        } else {
            gameView.showMessage("You were defeated by the villain: " + villain.getName() + "!");
            gameView.showMessage("GAME OVER! Your hero " + hero.getName() + " has fallen.");
        }
    }

    public void keepNewArtifact(Artifact artifactDropped) {
        ArtifactType type = artifactDropped.getType();
        System.out.println("type of artifact: " + type);

        int artifactId;
        try {
            artifactId = artifactDAO.insertArtifact(artifactDropped);
            artifactDropped.setId(artifactId);
        } catch (Exception e) {
            System.out.println("Error inserting artifact: " + e.getMessage());
            return;
        }

        switch (type) {
            case WEAPON:
                keepWeapon(artifactId, artifactDropped);
                break;
            case ARMOR:
                keepArmor(artifactId, artifactDropped);
                break;
            case HELM:
                keepHelm(artifactId, artifactDropped);
                break;
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

    public void updateXpAndLevel(BattleResult result) {
        int xpGained = result.getXpGained();
        int curLevel = hero.getLevel();

        hero.addExperience(xpGained);
        try {
            heroDAO.updateExperience(hero.getId(), hero.getExperience());
        } catch (Exception e) {
            System.out.println("Error updating hero experience: " + e.getMessage());
        }
        gameView.showMessage("You gained " + xpGained + " experience points!");

        if (curLevel < hero.getLevel()) {
            gameView.showMessage("NEW LEVEL! Now you are at level " + hero.getLevel());
            try {
                heroDAO.updateLevel(hero.getId(), hero.getLevel());
            } catch (Exception e) {
                System.out.println("Error updating hero level: " + e.getMessage());
            }
        }
    }

    public void keepWeapon(int artifactId, Artifact artifactDropped) {
        int oldId = -1;

        if (hero.getWeapon() != null) {
            oldId = hero.getWeapon().getId();
        }
        hero.setWeapon((Weapon) artifactDropped);
        try {
            heroDAO.updateWeapon(hero.getId(), artifactId);
        } catch (Exception e) {
            System.out.println("Error updating Weapon: " + e.getMessage());
        }
        deleteOldArtifact(oldId);
    }

    public void keepArmor(int artifactId, Artifact artifactDropped) {
        int oldId = -1;

        if (hero.getArmor() != null) {
            oldId = hero.getArmor().getId();
        }
        hero.setArmor((Armor) artifactDropped);
        try {
            heroDAO.updateArmor(hero.getId(), artifactId);
        } catch (Exception e) {
            System.out.println("Error updating Armor: " + e.getMessage());
        }
        deleteOldArtifact(oldId);
    }

    public void keepHelm(int artifactId, Artifact artifactDropped) {
        int oldId = -1;

        if (hero.getHelm() != null) {
            oldId = hero.getHelm().getId();
        }
        hero.setHelm((Helm) artifactDropped);
        try {
            heroDAO.updateHelm(hero.getId(), artifactId);
        } catch (Exception e) {
            System.out.println("Error updating Helm: " + e.getMessage());
        }
        deleteOldArtifact(oldId);
    }

    public void deleteOldArtifact(int oldId) {
        try {
            if (oldId > 0) {
                artifactDAO.deleteArtifact(oldId);
            }
        } catch (Exception e) {
            System.out.println("Error deleting artifact: " + e.getMessage());
        }
    }
}
