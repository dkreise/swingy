package ro.academyplus.swingy.model.dao;

import ro.academyplus.swingy.model.artifact.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtifactDAO {
    private final Connection connection;

    public ArtifactDAO(Connection connection) {
        this.connection = connection;
    }

    public int insertArtifact(Artifact artifact) throws SQLException {
        String sql = "INSERT INTO artifacts (type, name, bonus) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, artifact.getType().toString()); // or just artifact.getType() if it's already a string
            stmt.setString(2, artifact.getName());
            stmt.setInt(3, artifact.getBonus());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // return the auto-generated ID
            } else {
                throw new SQLException("Failed to retrieve artifact ID.");
            }
        }
    }

    public Artifact getArtifactById(int id) throws SQLException {
        String sql = "SELECT * FROM artifacts WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ArtifactType type = ArtifactType.valueOf(rs.getString("type"));
                String name = rs.getString("name");
                int bonus = rs.getInt("bonus");
                switch (type) {
                    case WEAPON:
                        return new Weapon(name, bonus);
                    case ARMOR:
                        return new Armor(name, bonus);
                    case HELM:
                        return new Helm(name, bonus);
                    default:
                        throw new SQLException("Unknown artifact type: " + type);
                }
            }
        }
        return null;
    }
}
