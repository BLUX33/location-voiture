package ui;

import database.DatabaseInitializer;
import model.Client;
import model.Reservation;
import model.Voiture;
import service.ClientService;
import service.ReservationService;
import service.VoitureService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MainGUI extends JFrame {

    private VoitureService voitureService = new VoitureService();
    private ClientService clientService = new ClientService();
    private ReservationService reservationService = new ReservationService();
    
    private JTable tableVoitures, tableClients, tableReservations;
    private DefaultTableModel modeleVoitures, modeleClients, modeleReservations;

    public MainGUI() {
        DatabaseInitializer.initialize();

        setTitle("Gestion de Location de Voitures - Système Complet");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Voitures", creerPanelVoitures());
        tabbedPane.addTab("Clients", creerPanelClients()); 
        tabbedPane.addTab("Réservations", creerPanelReservations());

        add(tabbedPane);
    }

    // ==========================================
    // 1. ONGLET "VOITURES" (AVEC MODIFIER / SUPPRIMER)
    // ==========================================
    private JPanel creerPanelVoitures() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] colonnes = {"ID", "Marque", "Modèle", "Immatriculation", "Catégorie", "Prix/Jour", "Statut"};
        modeleVoitures = new DefaultTableModel(colonnes, 0);
        tableVoitures = new JTable(modeleVoitures);
        panel.add(new JScrollPane(tableVoitures), BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        JButton btnActualiser = new JButton("Actualiser");
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");

        btnActualiser.addActionListener(e -> chargerVoitures());
        btnAjouter.addActionListener(e -> afficherFormulaireAjoutVoiture());
        btnModifier.addActionListener(e -> modifierVoiture());
        btnSupprimer.addActionListener(e -> supprimerVoiture());

        panelBoutons.add(btnActualiser); panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier); panelBoutons.add(btnSupprimer);
        panel.add(panelBoutons, BorderLayout.SOUTH);

        chargerVoitures();
        return panel;
    }

    private void chargerVoitures() {
        modeleVoitures.setRowCount(0); 
        for (Voiture v : voitureService.listerVoitures()) {
            modeleVoitures.addRow(new Object[]{v.getId(), v.getMarque(), v.getModele(), v.getImmatriculation(), v.getCategorie(), v.getPrixParJour(), v.getStatut()});
        }
    }

    private void afficherFormulaireAjoutVoiture() {
        JTextField txtMarque = new JTextField(); JTextField txtModele = new JTextField();
        JTextField txtImmat = new JTextField();  JTextField txtCat = new JTextField();
        JTextField txtPrix = new JTextField();

        Object[] formulaire = {"Marque :", txtMarque, "Modèle :", txtModele, "Immatriculation :", txtImmat, "Catégorie :", txtCat, "Prix par jour (DH) :", txtPrix};

        if (JOptionPane.showConfirmDialog(this, formulaire, "Nouvelle Voiture", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                voitureService.ajouterVoiture(new Voiture(txtMarque.getText(), txtModele.getText(), txtImmat.getText(), txtCat.getText(), Double.parseDouble(txtPrix.getText())));
                chargerVoitures(); 
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Le prix doit être un nombre."); }
        }
    }

    private void modifierVoiture() {
        int ligne = tableVoitures.getSelectedRow();
        if (ligne == -1) { JOptionPane.showMessageDialog(this, "Veuillez sélectionner une voiture dans le tableau."); return; }
        
        int id = (int) modeleVoitures.getValueAt(ligne, 0);
        Voiture v = voitureService.trouverParId(id);

        JTextField txtMarque = new JTextField(v.getMarque()); JTextField txtModele = new JTextField(v.getModele());
        JTextField txtImmat = new JTextField(v.getImmatriculation()); JTextField txtCat = new JTextField(v.getCategorie());
        JTextField txtPrix = new JTextField(String.valueOf(v.getPrixParJour()));

        Object[] formulaire = {"Marque :", txtMarque, "Modèle :", txtModele, "Immatriculation :", txtImmat, "Catégorie :", txtCat, "Prix par jour :", txtPrix};

        if (JOptionPane.showConfirmDialog(this, formulaire, "Modifier Voiture", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                v.setMarque(txtMarque.getText()); v.setModele(txtModele.getText());
                v.setImmatriculation(txtImmat.getText()); v.setCategorie(txtCat.getText());
                v.setPrixParJour(Double.parseDouble(txtPrix.getText()));
                voitureService.modifierVoiture(v);
                chargerVoitures();
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Prix invalide."); }
        }
    }

    private void supprimerVoiture() {
        int ligne = tableVoitures.getSelectedRow();
        if (ligne == -1) { JOptionPane.showMessageDialog(this, "Veuillez sélectionner une voiture dans le tableau."); return; }
        int id = (int) modeleVoitures.getValueAt(ligne, 0);
        if (JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cette voiture ?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            voitureService.supprimerVoiture(id);
            chargerVoitures();
        }
    }

    // ==========================================
    // 2. ONGLET "CLIENTS" (AVEC MODIFIER / SUPPRIMER)
    // ==========================================
    private JPanel creerPanelClients() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] colonnes = {"ID", "Nom", "Prénom", "CIN", "Téléphone", "Email"};
        modeleClients = new DefaultTableModel(colonnes, 0);
        tableClients = new JTable(modeleClients);
        panel.add(new JScrollPane(tableClients), BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        JButton btnActualiser = new JButton("Actualiser");
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");

        btnActualiser.addActionListener(e -> chargerClients());
        btnAjouter.addActionListener(e -> afficherFormulaireAjoutClient());
        btnModifier.addActionListener(e -> modifierClient());
        btnSupprimer.addActionListener(e -> supprimerClient());

        panelBoutons.add(btnActualiser); panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier); panelBoutons.add(btnSupprimer);
        panel.add(panelBoutons, BorderLayout.SOUTH);

        chargerClients();
        return panel;
    }

    private void chargerClients() {
        modeleClients.setRowCount(0); 
        for (Client c : clientService.listerClients()) {
            modeleClients.addRow(new Object[]{c.getid(), c.getnom(), c.getprenom(), c.getcin(), c.gettelephone(), c.getemail()});
        }
    }

    private void afficherFormulaireAjoutClient() {
        JTextField txtNom = new JTextField(); JTextField txtPrenom = new JTextField();
        JTextField txtCin = new JTextField(); JTextField txtTel = new JTextField();
        JTextField txtEmail = new JTextField();

        Object[] formulaire = {"Nom :", txtNom, "Prénom :", txtPrenom, "CIN :", txtCin, "Téléphone :", txtTel, "Email :", txtEmail};

        if (JOptionPane.showConfirmDialog(this, formulaire, "Nouveau Client", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            clientService.ajouterClient(new Client(txtNom.getText(), txtPrenom.getText(), txtCin.getText(), txtTel.getText(), txtEmail.getText()));
            chargerClients(); 
        }
    }

    private void modifierClient() {
        int ligne = tableClients.getSelectedRow();
        if (ligne == -1) { JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client dans le tableau."); return; }
        
        int id = (int) modeleClients.getValueAt(ligne, 0);
        Client c = clientService.trouverParId(id);

        JTextField txtNom = new JTextField(c.getnom()); JTextField txtPrenom = new JTextField(c.getprenom());
        JTextField txtCin = new JTextField(c.getcin()); JTextField txtTel = new JTextField(c.gettelephone());
        JTextField txtEmail = new JTextField(c.getemail());

        Object[] formulaire = {"Nom :", txtNom, "Prénom :", txtPrenom, "CIN :", txtCin, "Téléphone :", txtTel, "Email :", txtEmail};

        if (JOptionPane.showConfirmDialog(this, formulaire, "Modifier Client", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            c.setenom(txtNom.getText()); 
            c.setprenom(txtPrenom.getText());
            c.setcin(txtCin.getText()); 
            c.settelephone(txtTel.getText()); 
            c.setemail(txtEmail.getText());
            
            clientService.modifierClient(c);
            chargerClients();
        }
    }

    private void supprimerClient() {
        int ligne = tableClients.getSelectedRow();
        if (ligne == -1) { JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client dans le tableau."); return; }
        int id = (int) modeleClients.getValueAt(ligne, 0);
        if (JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer ce client ?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            clientService.supprimerClient(id);
            chargerClients();
        }
    }

    // ==========================================
    // 3. ONGLET "RÉSERVATIONS"
    // ==========================================
    private JPanel creerPanelReservations() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] colonnes = {"ID", "Client", "Voiture", "Début", "Fin", "Montant (DH)", "Statut"};
        modeleReservations = new DefaultTableModel(colonnes, 0);
        tableReservations = new JTable(modeleReservations);
        panel.add(new JScrollPane(tableReservations), BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        JButton btnActualiser = new JButton("Actualiser");
        JButton btnAjouter = new JButton("Créer Réservation");
        JButton btnTerminer = new JButton("Terminer la sélectionnée");

        btnActualiser.addActionListener(e -> chargerReservations());
        btnAjouter.addActionListener(e -> afficherFormulaireAjoutReservation());
        
        btnTerminer.addActionListener(e -> {
            int ligne = tableReservations.getSelectedRow();
            if (ligne != -1) {
                terminerReservation((int) modeleReservations.getValueAt(ligne, 0));
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réservation d'abord.");
            }
        });

        panelBoutons.add(btnActualiser); 
        panelBoutons.add(btnAjouter); 
        panelBoutons.add(btnTerminer);
        panel.add(panelBoutons, BorderLayout.SOUTH);

        chargerReservations();
        return panel;
    }

    private void chargerReservations() {
        modeleReservations.setRowCount(0); 
        for (Reservation r : reservationService.listerReservations()) {
            
            // 1. Check if the client still exists before getting their name
            String cInfo = "Client Supprimé";
            if (r.getclient() != null) {
                cInfo = r.getclient().getnom() + " " + r.getclient().getprenom();
            }
            
            // 2. Check if the car still exists before getting its details
            String vInfo = "Voiture Supprimée";
            if (r.getvoiture() != null) {
                vInfo = r.getvoiture().getMarque() + " (" + r.getvoiture().getImmatriculation() + ")";
            }
            
            // 3. Add the row safely
            modeleReservations.addRow(new Object[]{r.getid(), cInfo, vInfo, r.getDateDebut(), r.getDateFin(), r.getMontantTotal(), r.getStatut()});
        }
    }

    private void afficherFormulaireAjoutReservation() {
        JTextField txtClientId = new JTextField(); JTextField txtVoitureId = new JTextField();
        JTextField txtDateDebut = new JTextField("YYYY-MM-DD"); JTextField txtDateFin = new JTextField("YYYY-MM-DD");

        Object[] formulaire = {"ID Client :", txtClientId, "ID Voiture :", txtVoitureId, "Date début :", txtDateDebut, "Date fin :", txtDateFin};

        if (JOptionPane.showConfirmDialog(this, formulaire, "Nouvelle Réservation", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Client client = clientService.trouverParId(Integer.parseInt(txtClientId.getText()));
                Voiture voiture = voitureService.trouverParId(Integer.parseInt(txtVoitureId.getText()));

                if (client == null || voiture == null) { JOptionPane.showMessageDialog(this, "Client ou Voiture introuvable !"); return; }
                if (voiture.getStatut() == Voiture.Statut.LOUEE) { JOptionPane.showMessageDialog(this, "Cette voiture est déjà louée !"); return; }

                LocalDate debut = LocalDate.parse(txtDateDebut.getText());
                LocalDate fin = LocalDate.parse(txtDateFin.getText());
                double montant = reservationService.calculerMontant(debut, fin, voiture.getPrixParJour());
                
                reservationService.creerReservation(new Reservation(client, voiture, debut, fin, montant));
                chargerReservations(); 
                chargerVoitures(); 
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erreur de saisie (Vérifiez les IDs et les dates)."); }
        }
    }

    private void terminerReservation(int id) {
        List<Reservation> liste = reservationService.listerReservations();
        for (Reservation r : liste) {
            if (r.getid() == id) {
                if (r.getStatut() != Reservation.Statut.EN_COURS) {
                    JOptionPane.showMessageDialog(this, "Cette réservation est déjà terminée ou annulée.");
                    return;
                }
                reservationService.terminerReservation(r);
                chargerReservations();
                chargerVoitures();
                JOptionPane.showMessageDialog(this, "Réservation terminée ! Voiture libérée.");
                return;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI fenetrePrincipale = new MainGUI();
            fenetrePrincipale.setVisible(true); 
        });
    }
}