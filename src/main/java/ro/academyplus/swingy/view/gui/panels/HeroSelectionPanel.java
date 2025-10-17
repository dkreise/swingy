package ro.academyplus.swingy.view.gui.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import ro.academyplus.swingy.utils.AppStyle;
import java.awt.*;
import javax.swing.text.html.HTMLEditorKit;
import java.util.List;
import ro.academyplus.swingy.model.hero.Hero;
import ro.academyplus.swingy.controller.GameController;

public class HeroSelectionPanel extends JPanel {
    private JList<String> heroList;
    private JEditorPane heroInfo;
    private JButton selectButton;
    private List<Hero> heroes;
    private GameController controller;

    public HeroSelectionPanel(List<Hero> heroes, GameController controller) {
        this.heroes = heroes;
        this.controller = controller;

        setLayout(new BorderLayout());

        // Create list of hero names with model
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Hero hero : heroes) {
            listModel.addElement(hero.getName());
        }

    heroList = new JList<>(listModel);
    heroList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    heroList.setVisibleRowCount(10);
    // Increase font size for the list items and use custom renderer
    Font listFont = new Font("SansSerif", Font.BOLD, 24);
        heroList.setFont(listFont);
        heroList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setFont(listFont);
                lbl.setBorder(new EmptyBorder(6, 6, 6, 6));
                return lbl;
            }
        });
    JScrollPane listScroll = new JScrollPane(heroList);
    TitledBorder listBorder = BorderFactory.createTitledBorder("Heroes");
    listBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 18));
    listBorder.setTitleColor(AppStyle.ACCENT_GREEN);
    listScroll.setBorder(listBorder);
        listScroll.setPreferredSize(new Dimension(220, 300)); // make left column wider

        // Hero info panel
    // Use an HTML editor pane so we can style text size and spacing
    heroInfo = new JEditorPane();
    heroInfo.setEditable(false);
    heroInfo.setContentType("text/html");
    HTMLEditorKit kit = new HTMLEditorKit();
    heroInfo.setEditorKit(kit);
    heroInfo.setBorder(new EmptyBorder(8, 8, 8, 8));
    JScrollPane infoScroll = new JScrollPane(heroInfo);
    TitledBorder infoBorder = BorderFactory.createTitledBorder("Hero Stats");
    infoBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 18));
    infoBorder.setTitleColor(AppStyle.ACCENT_GREEN);
    infoScroll.setBorder(infoBorder);

        // Use JSplitPane to allow adjustable sizing
    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroll, infoScroll);
        split.setResizeWeight(0.25); // leave more space for the right side
        split.setOneTouchExpandable(true);
        add(split, BorderLayout.CENTER);

        // Select button in a bottom panel
        selectButton = new JButton("Select Hero");
        selectButton.setEnabled(false);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        bottom.add(selectButton);
        add(bottom, BorderLayout.SOUTH);

        // Listener to update info when a hero is selected
        heroList.addListSelectionListener(e -> {
            int index = heroList.getSelectedIndex();
            if (index >= 0) {
                Hero selectedHero = heroes.get(index);
                heroInfo.setText(getHeroStatsHtml(selectedHero));
                selectButton.setEnabled(true);
            } else {
                heroInfo.setText("");
                selectButton.setEnabled(false);
            }
        });

        // Double-click or Enter to select
        heroList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = heroList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        controller.setHero(heroes.get(index), false);
                    }
                }
            }
        });

        heroList.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "select");
        heroList.getActionMap().put("select", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int index = heroList.getSelectedIndex();
                if (index >= 0) controller.setHero(heroes.get(index), false);
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

    private String getHeroStatsHtml(Hero hero) {
        StringBuilder sb = new StringBuilder();
    sb.append("<html><body style='font-family: SansSerif; font-size:16pt;'>");
    sb.append("<h2 style='margin:0 0 8px 0; font-size:18pt; color:#222;'>").append(hero.getName()).append("</h2>");
        sb.append("<div style='margin-bottom:6px;'><strong>Class:</strong> ").append(hero.getHeroClass().getDisplayName()).append("</div>");
        sb.append("<div><strong>Level:</strong> ").append(hero.getLevel()).append("</div>");
        sb.append("<div><strong>Experience:</strong> ").append(hero.getExperience()).append("</div>");
        sb.append("<div><strong>Attack:</strong> ").append(hero.getTotalAttack()).append("</div>");
        sb.append("<div><strong>Defense:</strong> ").append(hero.getTotalDefense()).append("</div>");
        sb.append("<div><strong>Hit Points:</strong> ").append(hero.getTotalHitPoints()).append("</div>");

        sb.append("<hr style='margin:8px 0;' />");

        if (hero.getWeapon() != null) {
            sb.append("<div><strong>Weapon:</strong> ").append(hero.getWeapon().getName())
            .append(" (Bonus: ").append(hero.getWeapon().getBonus()).append(")</div>");
        } else {
            sb.append("<div><strong>Weapon:</strong> None</div>");
        }

        if (hero.getArmor() != null) {
            sb.append("<div><strong>Armor:</strong> ").append(hero.getArmor().getName())
            .append(" (Bonus: ").append(hero.getArmor().getBonus()).append(")</div>");
        } else {
            sb.append("<div><strong>Armor:</strong> None</div>");
        }

        if (hero.getHelm() != null) {
            sb.append("<div><strong>Helm:</strong> ").append(hero.getHelm().getName())
            .append(" (Bonus: ").append(hero.getHelm().getBonus()).append(")</div>");
        } else {
            sb.append("<div><strong>Helm:</strong> None</div>");
        }

        sb.append("</body></html>");
        return sb.toString();
    }
}
