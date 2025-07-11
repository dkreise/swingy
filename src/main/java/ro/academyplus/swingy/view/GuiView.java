package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.utils.AppStyle;

import javax.swing.*;
import java.awt.*;

public class GuiView implements GameView {
    private GameController controller;
    private JFrame frame;

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
        JButton startButton = new JButton("Start Game");
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
    public void showMessage(String message) {
        System.out.println("GUI Message: " + message);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }    
}
