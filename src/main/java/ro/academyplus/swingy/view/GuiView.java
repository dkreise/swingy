package ro.academyplus.swingy.view;

import ro.academyplus.swingy.controller.GameController;

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
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        label.setForeground(new Color(34, 139, 34));

        // Create start button
        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        startButton.setBackground(new Color(60, 179, 113)); // medium sea green
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);

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

        // Display
        frame.getContentPane().removeAll();
        frame.add(content);
        frame.revalidate();
        frame.repaint();
    }


    // @Override
    // public void showWelcomeMessage() {
    //     JLabel label = new JLabel("Welcome to Swingy Game!", SwingConstants.CENTER);
    //     label.setFont(new Font("SansSerif", Font.BOLD, 22));
    //     label.setForeground(new Color(34, 139, 34)); // dark green color

    //     JPanel welcomePanel = new JPanel(new BorderLayout());
    //     welcomePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
    //     welcomePanel.add(label, BorderLayout.CENTER);

    //     JButton startButton = new JButton("Start Game");
    //     startButton.addActionListener(e -> showMainMenu());
    //     welcomePanel.add(startButton, BorderLayout.SOUTH);
    //     welcomePanel.setBackground(Color.BLACK);
    //     welcomePanel.setForeground(Color.WHITE);

    //     frame.add(welcomePanel);
    //     frame.revalidate();
    //     frame.repaint();
    // }

    @Override
    public void showMainMenu() {
        JButton createButton = new JButton("Create New Hero");
        JButton selectButton = new JButton("Select Existing Hero");

        JPanel menuPanel = new JPanel();
        // menuPanel.setLayout(new GridLayout(2, 1));
        menuPanel.add(createButton);
        menuPanel.add(selectButton);

        createButton.addActionListener(e -> controller.onHeroCreate());
        selectButton.addActionListener(e -> controller.onHeroSelect());

        frame.getContentPane().removeAll();
        frame.add(menuPanel);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void startGame() {
        System.out.println("Starting game in GUI mode...");
        // Additional logic to start the game can be added here
    }

    @Override
    public void showMessage(String message) {
        System.out.println("GUI Message: " + message);
        // Logic to display the message in a GUI dialog can be added here
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }    
}
