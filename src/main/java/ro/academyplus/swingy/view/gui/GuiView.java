package ro.academyplus.swingy.view.gui;

import ro.academyplus.swingy.controller.*;
import ro.academyplus.swingy.utils.AppStyle;
import ro.academyplus.swingy.view.GameView;
import ro.academyplus.swingy.view.gui.panels.*;
import ro.academyplus.swingy.model.hero.*;
import ro.academyplus.swingy.model.villain.*;
import ro.academyplus.swingy.model.map.*;
import ro.academyplus.swingy.model.artifact.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class GuiView implements GameView {
    private GameController controller;
    private Hero hero;
    private GameMap gameMap;
    private JFrame frame;
    private GameMapPanel mapPanel;
    private StatsPanel statsPanel;

    public GuiView() {
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    public void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("Swingy Game");
        // panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        // frame.add(panel);
        frame.setVisible(true);
        showWelcomeMessage();
    }

    @Override
    public void showWelcomeMessage() {
        // Create welcome label
        JLabel label = new JLabel("Welcome to Swingy Game!", SwingConstants.CENTER);
        label.setFont(AppStyle.TITLE_FONT);
        label.setForeground(AppStyle.ACCENT_GREEN);

        // Create start button
        JButton startButton = new JButton("Start");
        AppStyle.stylePrimaryButton(startButton);
        startButton.addActionListener(e -> showMainMenu());

        // Create content panel
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        // content.setBackground(Color.BLACK);
        content.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50)); // padding

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(label);
        content.add(Box.createVerticalStrut(40)); // spacing
        content.add(startButton);

        AppStyle.switchPanel(frame, content);
    }

    @Override
    public void showMainMenu() {
        JLabel label = new JLabel("MENU", SwingConstants.CENTER);
        label.setFont(AppStyle.TITLE_FONT);
        label.setForeground(AppStyle.ACCENT_GREEN);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createButton = new JButton("Create New Hero");
        JButton selectButton = new JButton("Select Existing Hero");
        AppStyle.stylePrimaryButton(createButton);
        AppStyle.stylePrimaryButton(selectButton);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        menuPanel.add(label);
        menuPanel.add(createButton);
        menuPanel.add(selectButton);

        createButton.addActionListener(e -> controller.onHeroCreate());
        selectButton.addActionListener(e -> controller.onHeroSelect());

        AppStyle.switchPanel(frame, menuPanel);
    }

    @Override
    public void startGame() {
        System.out.println("Starting game in GUI mode...");
    }

    @Override
    public void showHeroCreationMenu() {
        JLabel label = new JLabel("CREATE YOUR HERO", SwingConstants.CENTER);
        label.setFont(AppStyle.TITLE_FONT);
        label.setForeground(AppStyle.ACCENT_GREEN);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel classPanel = new JPanel(new GridLayout(0, 1));
        classPanel.add(label);
        ButtonGroup classGroup = new ButtonGroup();
        Map<JRadioButton, HeroClass> classButtons = new HashMap<>();

        for (HeroClass hc : HeroClass.values()) {
            JRadioButton btn = new JRadioButton(HeroClass.getHeroClassStat(hc));
            classButtons.put(btn, hc);
            classGroup.add(btn);
            classPanel.add(btn);
        }

        // Input field for hero name
        JPanel inputPanel = new JPanel();
        JTextField nameField = new JTextField(15);
        inputPanel.add(new JLabel("Hero Name:"));
        inputPanel.add(nameField);

        // Create button
        JButton createButton = new JButton("Create Hero");
        createButton.addActionListener(e -> controller.handleHeroCreation(nameField, classButtons));

        // Put everything together
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.CENTER);
        southPanel.add(createButton, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(classPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);
        // frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showHeroSelectionMenu(List<Hero> heroes) {
        HeroSelectionPanel heroSelectionPanel = new HeroSelectionPanel(heroes, controller);
        AppStyle.switchPanel(frame, heroSelectionPanel);
    }

    @Override
    public void showHeroStats(Hero hero) {
        JPanel statsPanel = createHeroStatsPanel(hero);
        JButton startButton = new JButton("Start Game");
        AppStyle.stylePrimaryButton(startButton);
        startButton.addActionListener(e -> controller.handleGameStart());
        statsPanel.add(startButton);

        AppStyle.switchPanel(frame, statsPanel);
    }

    public JPanel createHeroStatsPanel(Hero hero) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));  // vertical stack

        JLabel labelTitle = new JLabel("HERO STATS", SwingConstants.CENTER);
        labelTitle.setFont(AppStyle.TITLE_FONT);
        labelTitle.setForeground(AppStyle.ACCENT_GREEN);
        labelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(labelTitle);
        panel.add(new JLabel("Name: " + hero.getName()));
        panel.add(new JLabel("Class: " + hero.getHeroClass().getDisplayName()));
        panel.add(new JLabel("Level: " + hero.getLevel()));
        panel.add(new JLabel("Experience: " + hero.getExperience()));
        panel.add(new JLabel("Attack: " + hero.getTotalAttack()));
        panel.add(new JLabel("Defense: " + hero.getTotalDefense()));
        panel.add(new JLabel("Hit Points: " + hero.getTotalHitPoints()));

        panel.add(new JLabel("Weapon: " + 
            (hero.getWeapon() != null ? hero.getWeapon().getName() + " (+" + hero.getWeapon().getBonus() + ")" : "None")));

        panel.add(new JLabel("Armor: " +
            (hero.getArmor() != null ? hero.getArmor().getName() + " (+" + hero.getArmor().getBonus() + ")" : "None")));

        panel.add(new JLabel("Helm: " +
            (hero.getHelm() != null ? hero.getHelm().getName() + " (+" + hero.getHelm().getBonus() + ")" : "None")));

        return panel;
    }

    @Override
    public void startNewGame(Hero hero, GameMap gameMap) {
        this.hero = hero;
        this.gameMap = gameMap;
        Position heroPosition = gameMap.getHeroPosition();
        int mapSize = gameMap.getSize();
        this.mapPanel = new GameMapPanel(this, hero, mapSize, heroPosition.getX(), heroPosition.getY());
        this.statsPanel = new StatsPanel();
        AppStyle.switchPanel(frame, mapPanel, statsPanel);
        statsPanel.updateStats(hero);
        askDirection(heroPosition);
    }

    @Override
    public void askDirection(Position heroPosition) {
        statsPanel.updateStats(hero);
        mapPanel.setMovementEnabled(true);
    }

    public void askToHandleHeroMovement(Direction dir) {
        controller.handleHeroMovement(dir);
    }

    @Override
    public void updateHeroPosition(Position heroPosition, Position oldPosition, boolean hasVillain) {
        statsPanel.updateStats(hero);
        mapPanel.setMovementEnabled(false);
        mapPanel.updateHeroPosition(heroPosition.getX(), heroPosition.getY());
        if (hasVillain) {
            Villain villain = gameMap.getVillainAt(heroPosition);
            int dangerLevel = BattleManager.estimatedDangerLevel(hero, villain);
            int choice = mapPanel.askForBattleChoice(villain, dangerLevel);
            System.out.println("CHOICE: " + choice);
            if (choice != 1) {
                showMessage("You chose to fight the villain! How brave! Let's start the battle...");
                controller.startBattle();
            } else {
                showMessage("You chose to run away! Let's see if you can escape...");
                controller.tryToRun(oldPosition);
            }
        } else {
            controller.startNewMove();
        }
    }

    @Override
    public void notifyAboutArtifactDropped(Artifact artifactDropped) {
        int choice = mapPanel.askForArtifactChoice(artifactDropped);
        
        if (choice == 0) {
            controller.keepNewArtifact(artifactDropped);
        }
    }

    @Override
    public void showMessage(String message) {
        mapPanel.showMessageDialog(message, "!!!");
    }

    public void setController(GameController controller) {
        this.controller = controller;
    } 

    public GameMap getGameMap() {
        return gameMap;
    }
}
