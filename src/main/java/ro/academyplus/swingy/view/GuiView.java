package ro.academyplus.swingy.view;

import javax.swing.*;
import java.awt.*;

public class GuiView implements GameView {
    private JFrame frame;
    // private JPanel panel;

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
        JLabel label = new JLabel("Welcome to Swingy Game!", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 22));
        label.setForeground(new Color(34, 139, 34)); // dark green color

        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        welcomePanel.add(label, BorderLayout.CENTER);

        // Remove old content and add welcome panel
        // frame.getContentPane().removeAll();
        frame.add(welcomePanel);
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
    
}
