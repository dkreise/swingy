package ro.academyplus.swingy.view.gui.panels;

import ro.academyplus.swingy.model.hero.Hero;
import ro.academyplus.swingy.model.artifact.Artifact;
import ro.academyplus.swingy.model.artifact.ArtifactType;
import ro.academyplus.swingy.model.map.*;
import ro.academyplus.swingy.view.gui.GuiView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameMapPanel extends JPanel {
    private GuiView guiView;
    private final Hero hero;
    private final int mapSize;
    private final int tileSize = 40; // px
    private int heroX;
    private int heroY;
    private boolean movementEnabled = false;

    public GameMapPanel(GuiView guiView, Hero hero, int mapSize, int startX, int startY) {
        this.guiView = guiView;
        this.hero = hero;
        this.mapSize = mapSize;
        this.heroX = startX;
        this.heroY = startY;

        setPreferredSize(new Dimension(mapSize * tileSize, mapSize * tileSize));
        setBackground(Color.WHITE);

        setFocusable(true);
        requestFocusInWindow();

        setupKeyBindings();
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");

        actionMap.put("moveUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (movementEnabled) {
                    guiView.askToHandleHeroMovement(Direction.NORTH);
                }
            }
        });
        actionMap.put("moveDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (movementEnabled) {
                    guiView.askToHandleHeroMovement(Direction.SOUTH);
                }
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (movementEnabled) {
                    guiView.askToHandleHeroMovement(Direction.WEST);
                }
            }
        });
        actionMap.put("moveRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (movementEnabled) {
                    guiView.askToHandleHeroMovement(Direction.EAST);
                }
            }
        });
    }

    public void updateHeroPosition(int x, int y) {
        this.heroX = x;
        this.heroY = y;
        repaint();
    }

    public void setMovementEnabled(boolean enabled) {
        this.movementEnabled = enabled;
    }

    public int askForBattleChoice() {
        int choice = JOptionPane.showOptionDialog(
            this,
            "You encountered a villain at this position! What do you want to do?",
            "Villain!",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[] {"Fight", "Run"},
            "Fight"
        );
        return choice;
    }

    public int askForArtifactChoice(Artifact artifactDropped) {
        ArtifactType type = artifactDropped.getType();
        Artifact curArtifact = hero.getArtifactByType(type);

        String question = "What would you like to do with it?";
        String curArtifactInfo = "";
        if (curArtifact != null) {
            curArtifactInfo ="Currently you have " + type.toString() + " \"" + curArtifact.getName() + "\" (Bonus: " + curArtifact.getBonus() + ").";
        } else {
            curArtifactInfo = "Currently you don't have any " + type.toString() + ".";
        }

        String message = String.format(
            "Wow! New %s has dropped!\n\"%s\" (Bonus: %d)\n\n%s\n%s",
            type.toString(),
            artifactDropped.getName(),
            artifactDropped.getBonus(),
            curArtifactInfo,
            question
        );

        int choice = JOptionPane.showOptionDialog(
            this,
            message,
            "New Artifact!",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[] {"Keep", "Leave"},
            "Keep"
        );
        return choice;
    }

    public void showMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(
            SwingUtilities.getWindowAncestor(this),
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= mapSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, mapSize * tileSize); // vertical
            g.drawLine(0, i * tileSize, mapSize * tileSize, i * tileSize); // horizontal
        }

        // Draw hero
        g.setColor(Color.BLUE);
        g.fillOval(heroX * tileSize + 10, heroY * tileSize + 10, tileSize - 20, tileSize - 20);
    }
}
