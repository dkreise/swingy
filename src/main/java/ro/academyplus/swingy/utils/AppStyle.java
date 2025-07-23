package ro.academyplus.swingy.utils;

import javax.swing.*;
import java.awt.*;

// AppStyle.java
public class AppStyle {
    // 🎨 Color palette
    public static final Color BACKGROUND = new Color(30, 30, 30);
    public static final Color ACCENT_GREEN = new Color(34, 139, 34);
    public static final Color PRIMARY_BLUE = new Color(70, 130, 180);
    public static final Color ORANGE = new Color(255, 140, 0);
    public static final Color TEXT_LIGHT = Color.WHITE;

    // 🔤 Fonts
    public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 26);
    public static final Font BUTTON_FONT = new Font("SansSerif", Font.PLAIN, 18);

    public static void switchPanel(JFrame frame, JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static void switchPanel(JFrame frame, JPanel panel1, JPanel panel2) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel1,BorderLayout.CENTER);
        frame.getContentPane().add(panel2, BorderLayout.EAST);
        frame.revalidate();
        frame.repaint();
    }

    // 🧩 Reusable button style
    public static void stylePrimaryButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_BLUE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public static void styleAccentButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(ORANGE);
        button.setForeground(TEXT_LIGHT);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

