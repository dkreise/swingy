package ro.academyplus.swingy.view.gui.panels;

import ro.academyplus.swingy.model.hero.Hero;
import ro.academyplus.swingy.model.artifact.*;
import ro.academyplus.swingy.utils.AppStyle;
import ro.academyplus.swingy.view.gui.GuiView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StatsPanel extends JPanel {
    private JLabel labelTitle, nameLabel, levelLabel, xpLabel, weaponLabel, armorLabel, helmLabel;

    public StatsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        labelTitle = new JLabel("HERO STATS");
        labelTitle.setFont(AppStyle.TITLE_FONT);
        labelTitle.setForeground(AppStyle.ACCENT_GREEN);
        
        nameLabel = new JLabel();
        levelLabel = new JLabel();
        xpLabel = new JLabel();
        weaponLabel = new JLabel();
        armorLabel = new JLabel();
        helmLabel = new JLabel();

        add(labelTitle);
        add(nameLabel);
        add(levelLabel);
        add(xpLabel);
        add(weaponLabel);
        add(armorLabel);
        add(helmLabel);
    }

    public void updateStats(Hero hero) {
        nameLabel.setText("<html>&nbsp;<b>Name:</b> " + hero.getName() + "&nbsp;</html>");
        levelLabel.setText("<html>&nbsp;<b>Level:</b> " + hero.getLevel() + "&nbsp;</html>");
        xpLabel.setText("<html>&nbsp;<b>XP:</b> " + hero.getExperience() + "&nbsp;</html>");

        Weapon weapon = hero.getWeapon();
        weaponLabel.setText("<html>&nbsp;<b>Weapon:</b> " + (weapon != null ? "\"" + weapon.getName() + "\" (+" + weapon.getBonus() + ")&nbsp;</html>" : "None&nbsp;</html>"));
        
        Armor armor = hero.getArmor();
        armorLabel.setText("<html>&nbsp;<b>Armor:</b> " + (armor != null ? "\"" + armor.getName() + "\" (+" + armor.getBonus() + ")&nbsp;</html>" : "None&nbsp;</html>"));

        Helm helm = hero.getHelm();
        helmLabel.setText("<html>&nbsp;<b>Helm:</b> " + (helm != null ? "\"" + helm.getName() + "\" (+" + helm.getBonus() + ")&nbsp;</html>" : "None&nbsp;</html>"));       
    }
}
