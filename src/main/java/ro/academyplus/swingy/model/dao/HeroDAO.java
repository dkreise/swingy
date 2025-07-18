package ro.academyplus.swingy.model.dao;

import ro.academyplus.swingy.model.hero.Hero;
import ro.academyplus.swingy.model.hero.HeroClass;
import ro.academyplus.swingy.model.artifact.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.ArrayList;

public class HeroDAO {
    private final Connection connection;
    private final ArtifactDAO artifactDAO;

    public HeroDAO(Connection connection, ArtifactDAO artifactDAO) {
        this.connection = connection;
        this.artifactDAO = artifactDAO;
    }
    
    public int insertHero(Hero hero) throws SQLException {
        Integer weaponId = null, armorId = null, helmId = null;

        if (hero.getWeapon() != null) {
            weaponId = artifactDAO.insertArtifact(hero.getWeapon());
        }
        if (hero.getArmor() != null) {
            armorId = artifactDAO.insertArtifact(hero.getArmor());
        }
        if (hero.getHelm() != null) {
            helmId = artifactDAO.insertArtifact(hero.getHelm());
        }

        String sql = "INSERT INTO heroes (name, heroClass, level, experience, attack, defense, hitPoints, weapon_id, armor_id, helm_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, hero.getName());
            stmt.setString(2, hero.getHeroClass().getDisplayName());
            stmt.setInt(3, hero.getLevel());
            stmt.setInt(4, hero.getExperience());
            stmt.setInt(5, hero.getAttack());
            stmt.setInt(6, hero.getDefense());
            stmt.setInt(7, hero.getHitPoints());
            stmt.setObject(8, weaponId, Types.INTEGER);
            stmt.setObject(9, armorId, Types.INTEGER);
            stmt.setObject(10, helmId, Types.INTEGER);

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // return hero ID
            } else {
                throw new SQLException("Failed to insert hero.");
            }
        }
    }

    public Hero getHeroById(int id) throws SQLException {
        String sql = "SELECT * FROM heroes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HeroClass heroClass = HeroClass.valueOf(rs.getString("heroClass"));
                String name = rs.getString("name");
                int level = rs.getInt("level");
                int experience = rs.getInt("experience");
                int attack = rs.getInt("attack");
                int defense = rs.getInt("defense");
                int hitPoints = rs.getInt("hitPoints");

                Artifact weapon = artifactDAO.getArtifactById(rs.getInt("weapon_id"));
                Artifact armor = artifactDAO.getArtifactById(rs.getInt("armor_id"));
                Artifact helm = artifactDAO.getArtifactById(rs.getInt("helm_id"));

                return new Hero(name, heroClass, level, experience, attack, defense, hitPoints, (Weapon) weapon, (Armor) armor, (Helm) helm);
            }
        }
        return null; // Hero not found
    }

    public List<Hero> getAllHeroes() throws SQLException {
        List<Hero> heroes = new ArrayList<>();
        String sql = "SELECT * FROM heroes";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HeroClass heroClass = HeroClass.fromDisplayName(rs.getString("heroClass"));
                String name = rs.getString("name");
                int level = rs.getInt("level");
                int experience = rs.getInt("experience");
                int attack = rs.getInt("attack");
                int defense = rs.getInt("defense");
                int hitPoints = rs.getInt("hitPoints");

                Artifact weapon = artifactDAO.getArtifactById(rs.getInt("weapon_id"));
                Artifact armor = artifactDAO.getArtifactById(rs.getInt("armor_id"));
                Artifact helm = artifactDAO.getArtifactById(rs.getInt("helm_id"));

                Hero hero = new Hero(name, heroClass, level, experience, attack, defense, hitPoints, (Weapon) weapon, (Armor) armor, (Helm) helm);
                heroes.add(hero);
            }
        }
        return heroes;
    }
}
