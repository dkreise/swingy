package ro.academyplus.swingy.controller;

import ro.academyplus.swingy.view.GameView;
import ro.academyplus.swingy.model.hero.*;

import javax.swing.*;
import java.util.Map;
import java.util.HashMap;

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
}
