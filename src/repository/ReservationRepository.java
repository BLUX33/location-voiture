package repository;

import database.DatabaseConnection;
import model.Client;
import model.Reservation;
import model.Voiture;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    public void save(Reservation r) {
        String sql = "INSERT OR IGNORE INTO reservations (clientId, voitureId, dateDebut, dateFin, montantTotal, statut) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, r.getclient().getid());
            ps.setInt(2, r.getvoiture().getId());
            ps.setString(3, r.getDateDebut().toString());
            ps.setString(4, r.getDateFin().toString());
            ps.setDouble(5, r.getMontantTotal());
            ps.setString(6, r.getStatut().name());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur save reservation : " + e.getMessage());
        }
    }

    public List<Reservation> findAll() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT r.*, " +
                     "c.nom, c.prenom, c.cin, c.telephone, c.email, " +
                     "v.marque, v.modele, v.immatriculation, v.categorie, v.prixParJour, v.statut as vStatut " +
                     "FROM reservations r " +
                     "JOIN clients c ON r.clientId = c.id " +
                     "JOIN voitures v ON r.voitureId = v.id";
        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Client client = new Client(
                    rs.getInt("clientId"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("cin"),
                    rs.getString("telephone"),
                    rs.getString("email")
                );
                Voiture voiture = new Voiture(
                    rs.getInt("voitureId"),
                    rs.getString("marque"),
                    rs.getString("modele"),
                    rs.getString("immatriculation"),
                    rs.getString("categorie"),
                    rs.getDouble("prixParJour"),
                    Voiture.Statut.valueOf(rs.getString("vStatut"))
                );
                list.add(new Reservation(
                    rs.getInt("id"),
                    client,
                    voiture,
                    LocalDate.parse(rs.getString("dateDebut")),
                    LocalDate.parse(rs.getString("dateFin")),
                    rs.getDouble("montantTotal"),
                    Reservation.Statut.valueOf(rs.getString("statut"))
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur findAll reservation : " + e.getMessage());
        }
        return list;
    }

    public void update(Reservation r) {
        String sql = "UPDATE reservations SET statut=? WHERE id=?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, r.getStatut().name());
            ps.setInt(2, r.getid());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur update reservation : " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM reservations WHERE id = ?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur delete reservation : " + e.getMessage());
        }
    }
}