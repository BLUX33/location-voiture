package database;
import java.sql.*;

public class DatabaseInitializer {
	public static void initialize() {
		try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            //TABLE VOITURE
            stmt.execute("CREATE TABLE IF NOT EXISTS voitures (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "marque TEXT NOT NULL," +
                    "modele TEXT NOT NULL," +
                    "immatriculation TEXT UNIQUE NOT NULL," +
                    "categorie TEXT," +
                    "prixParJour REAL NOT NULL," +
                    "statut TEXT DEFAULT 'DISPONIBLE')");
            //TABLE CLIENT
            stmt.execute("CREATE TABLE IF NOT EXISTS clients (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nom TEXT NOT NULL," +
                    "prenom TEXT NOT NULL," +
                    "cin TEXT UNIQUE NOT NULL," +
                    "telephone TEXT," +
                    "email TEXT)");
            //TABLE RESERVATION
            stmt.execute("CREATE TABLE IF NOT EXISTS reservations (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "clientId INTEGER NOT NULL," +
                    "voitureId INTEGER NOT NULL," +
                    "dateDebut TEXT NOT NULL," +
                    "dateFin TEXT NOT NULL," +
                    "montantTotal REAL NOT NULL," +
                    "statut TEXT DEFAULT 'EN_COURS'," +
                    "FOREIGN KEY (clientId) REFERENCES clients(id)," +
                    "FOREIGN KEY (voitureId) REFERENCES voitures(id))");
            
            System.out.println("Base de données initialisée.");
            stmt.close();
		}
		catch (SQLException e) {
            System.out.println("Erreur initialisation : " + e.getMessage());
	}

}
}
