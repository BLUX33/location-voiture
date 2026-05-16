package repository;
import database.DatabaseConnection;
import java.sql.*;
import model.Voiture;
import java.util.ArrayList;
import java.util.List;

public class VoitureRepository {
	// Ajouter une voiture
    public void save(Voiture v) {
        String sql = "INSERT OR IGNORE INTO voitures (marque, modele, immatriculation, categorie, prixParJour, statut) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, v.getMarque());
            ps.setString(2, v.getModele());
            ps.setString(3, v.getImmatriculation());
            ps.setString(4, v.getCategorie());
            ps.setDouble(5, v.getPrixParJour());
            ps.setString(6, v.getStatut().name());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur save voiture : " + e.getMessage());
        }
    }
    //Lister tous les voitures
    public List<Voiture> findAll() {
        List<Voiture> list = new ArrayList<>();
        String sql = "SELECT * FROM voitures";
        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Voiture(
                    rs.getInt("id"),
                    rs.getString("marque"),
                    rs.getString("modele"),
                    rs.getString("immatriculation"),
                    rs.getString("categorie"),
                    rs.getDouble("prixParJour"),
                    Voiture.Statut.valueOf(rs.getString("statut"))
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur findAll voiture : " + e.getMessage());
        }
        return list;
    }
 // Trouver par ID
    public Voiture findById(int id) {
        String sql = "SELECT * FROM voitures WHERE id = ?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Voiture(
                    rs.getInt("id"),
                    rs.getString("marque"),
                    rs.getString("modele"),
                    rs.getString("immatriculation"),
                    rs.getString("categorie"),
                    rs.getDouble("prixParJour"),
                    Voiture.Statut.valueOf(rs.getString("statut"))
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur findById voiture : " + e.getMessage());
        }
        return null;
    }

    // Modifier une voiture
    public void update(Voiture v) {
        String sql = "UPDATE voitures SET marque=?, modele=?, immatriculation=?, categorie=?, prixParJour=?, statut=? WHERE id=?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, v.getMarque());
            ps.setString(2, v.getModele());
            ps.setString(3, v.getImmatriculation());
            ps.setString(4, v.getCategorie());
            ps.setDouble(5, v.getPrixParJour());
            ps.setString(6, v.getStatut().name());
            ps.setInt(7, v.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur update voiture : " + e.getMessage());
        }
    }

    // Supprimer une voiture
    public void delete(int id) {
        String sql = "DELETE FROM voitures WHERE id = ?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur delete voiture : " + e.getMessage());
        }
    }

    // Lister voitures disponibles
    public List<Voiture> findDisponibles() {
        List<Voiture> list = new ArrayList<>();
        String sql = "SELECT * FROM voitures WHERE statut = 'DISPONIBLE'";
        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Voiture(
                    rs.getInt("id"),
                    rs.getString("marque"),
                    rs.getString("modele"),
                    rs.getString("immatriculation"),
                    rs.getString("categorie"),
                    rs.getDouble("prixParJour"),
                    Voiture.Statut.valueOf(rs.getString("statut"))
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur findDisponibles : " + e.getMessage());
        }
        return list;
    }
}
