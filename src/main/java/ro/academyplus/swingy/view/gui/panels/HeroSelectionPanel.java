package ro.academyplus.swingy.view.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import ro.academyplus.swingy.model.hero.Hero;
import ro.academyplus.swingy.controller.GameController;

public class HeroSelectionPanel extends JPanel {
    private JList<String> heroList;
    private JTextArea heroInfo;
    private JButton selectButton;
    private List<Hero> heroes;
    private GameController controller;

    public HeroSelectionPanel(List<Hero> heroes, GameController controller) {
        this.heroes = heroes;
        this.controller = controller;

        setLayout(new BorderLayout());

        // Create list of hero names
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Hero hero : heroes) {
            listModel.addElement(hero.getName());
        }

        heroList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(heroList);
        add(scrollPane, BorderLayout.WEST);

        // Hero info panel
        heroInfo = new JTextArea();
        heroInfo.setEditable(false);
        heroInfo.setLineWrap(true);
        heroInfo.setWrapStyleWord(true);
        add(new JScrollPane(heroInfo), BorderLayout.CENTER);

        // Select button
        selectButton = new JButton("Select Hero");
        selectButton.setEnabled(false);
        add(selectButton, BorderLayout.SOUTH);

        // Listener to update info when a hero is selected
        heroList.addListSelectionListener(e -> {
            int index = heroList.getSelectedIndex();
            if (index >= 0) {
                Hero selectedHero = heroes.get(index);
                heroInfo.setText(getHeroStatsText(selectedHero));
                selectButton.setEnabled(true);
            }
        });

        // On click: set selected hero
        selectButton.addActionListener(e -> {
            int index = heroList.getSelectedIndex();
            if (index >= 0) {
                Hero selectedHero = heroes.get(index);
                controller.setHero(selectedHero, false);
            }
        });
    }

    private String getHeroStatsText(Hero hero) {
        StringBuilder sb = new StringBuilder();

        sb.append("Class: ").append(hero.getHeroClass().getDisplayName()).append("\n");
        sb.append("Level: ").append(hero.getLevel()).append("\n");
        sb.append("Experience: ").append(hero.getExperience()).append("\n");
        sb.append("Attack: ").append(hero.getTotalAttack()).append("\n");
        sb.append("Defense: ").append(hero.getTotalDefense()).append("\n");
        sb.append("Hit Points: ").append(hero.getTotalHitPoints()).append("\n");

        if (hero.getWeapon() != null) {
            sb.append("Weapon: ").append(hero.getWeapon().getName())
            .append(" (Bonus: ").append(hero.getWeapon().getBonus()).append(")\n");
        } else {
            sb.append("Weapon: None\n");
        }

        if (hero.getArmor() != null) {
            sb.append("Armor: ").append(hero.getArmor().getName())
            .append(" (Bonus: ").append(hero.getArmor().getBonus()).append(")\n");
        } else {
            sb.append("Armor: None\n");
        }

        if (hero.getHelm() != null) {
            sb.append("Helm: ").append(hero.getHelm().getName())
            .append(" (Bonus: ").append(hero.getHelm().getBonus()).append(")\n");
        } else {
            sb.append("Helm: None\n");
        }

        return sb.toString();
    }
}
