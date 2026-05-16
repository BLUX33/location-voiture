package repository;

import database.DatabaseConnection;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {

    public void save(Client c) {
        String sql = "INSERT OR IGNORE INTO clients (nom, prenom, cin, telephone, email) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, c.getnom());
            ps.setString(2, c.getprenom());
            ps.setString(3, c.getcin());
            ps.setString(4, c.gettelephone());
            ps.setString(5, c.getemail());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur save client : " + e.getMessage());
        }
    }

    public List<Client> findAll() {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("cin"),
                    rs.getString("telephone"),
                    rs.getString("email")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur findAll client : " + e.getMessage());
        }
        return list;
    }

    public Client findById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("cin"),
                    rs.getString("telephone"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur findById client : " + e.getMessage());
        }
        return null;
    }

    public void update(Client c) {
        String sql = "UPDATE clients SET nom=?, prenom=?, cin=?, telephone=?, email=? WHERE id=?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, c.getnom());
            ps.setString(2, c.getprenom());
            ps.setString(3, c.getcin());
            ps.setString(4, c.gettelephone());
            ps.setString(5, c.getemail());
            ps.setInt(6, c.getid());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur update client : " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur delete client : " + e.getMessage());
        }
    }
}