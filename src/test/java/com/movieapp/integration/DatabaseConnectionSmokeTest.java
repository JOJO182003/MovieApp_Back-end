package com.movieapp.integration;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DatabaseConnectionSmokeTest {

    @Test
    void should_connect_to_neon_database() {
        String url = "jdbc:postgresql://ep-young-boat-a4027g0r-pooler.us-east-1.aws.neon.tech:5432/moviePlanner";
        String username = "moviePlanner_owner";
        String password = "npg_Y9HzwWybKf6n";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            assertTrue(connection.isValid(5), "Connection to Neon DB should be valid");
            System.out.println("✅ Connexion réussie à Neon 🎉");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("❌ Échec de la connexion à la base PostgreSQL Neon: " + e.getMessage());
        }
    }
}
