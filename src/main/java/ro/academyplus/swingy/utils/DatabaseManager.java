package ro.academyplus.swingy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:swingy.db";
    private Connection connection;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        createTables();
    }

    private void createTables() throws SQLException {
        String createHeroes = """
            CREATE TABLE IF NOT EXISTS heroes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                heroClass TEXT NOT NULL,
                level INTEGER,
                experience INTEGER,
                attack INTEGER,
                defense INTEGER,
                hitPoints INTEGER,
                weapon_id INTEGER,
                armor_id INTEGER,
                helm_id INTEGER,
                FOREIGN KEY (weapon_id) REFERENCES artifacts(id),
                FOREIGN KEY (armor_id) REFERENCES artifacts(id),
                FOREIGN KEY (helm_id) REFERENCES artifacts(id)
            );
        """;

        String createArtifacts = """
            CREATE TABLE IF NOT EXISTS artifacts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                type TEXT NOT NULL,
                name TEXT NOT NULL,
                bonus INTEGER NOT NULL
            );
        """;

        Statement stmt = connection.createStatement();
        stmt.execute(createHeroes);
        stmt.execute(createArtifacts);
        stmt.close();
    }

    public Connection getConnection() {
        return connection;
    }
}
