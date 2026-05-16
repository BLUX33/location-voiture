package database;

import java.sql.*;

public class DatabaseConnection {
	private static final String URL = "jdbc:sqlite:LocationVoiture.db";
    private static Connection connection = null;
    
    public static Connection getConnection() {
    	try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
        return connection;
    }
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erreur fermeture : " + e.getMessage());
        }
    }
    	

}
