//import database.DatabaseInitializer;
//import model.Client;
//import model.Reservation;
//import model.Voiture;
//import service.ClientService;
//import service.ReservationService;
//import service.VoitureService;

//import java.time.LocalDate;
//import java.util.List;
//import java.util.Scanner;
import ui.MainGUI;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
	SwingUtilities.invokeLater(() -> {
        new MainGUI().setVisible(true);
    });

    /*static Scanner scanner               = new Scanner(System.in);
    static VoitureService voitureService         = new VoitureService();
    static ClientService clientService           = new ClientService();
    static ReservationService reservationService = new ReservationService();

    public static void main(String[] args) {

        DatabaseInitializer.initialize();

        int choix = -1;
        while (choix != 0) {
            afficherMenu();
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1: menuVoitures();     break;
                case 2: menuClients();      break;
                case 3: menuReservations(); break;
                case 0: System.out.println("Au revoir !"); break;
                default: System.out.println("Choix invalide !");
            }
        }
    }

    // ================================
    // MENU PRINCIPAL
    // ================================
    static void afficherMenu() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║   LOCATION DE VOITURES       ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  1. Gérer les Voitures       ║");
        System.out.println("║  2. Gérer les Clients        ║");
        System.out.println("║  3. Gérer les Réservations   ║");
        System.out.println("║  0. Quitter                  ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.print("Votre choix : ");
    }

    // ================================
    // MENU VOITURES
    // ================================
    static void menuVoitures() {
        System.out.println("\n--- VOITURES ---");
        System.out.println("1. Lister toutes les voitures");
        System.out.println("2. Lister voitures disponibles");
        System.out.println("3. Ajouter une voiture");
        System.out.println("4. Modifier une voiture");
        System.out.println("5. Supprimer une voiture");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1: listerVoitures();     break;
            case 2: listerDisponibles();  break;
            case 3: ajouterVoiture();     break;
            case 4: modifierVoiture();    break;
            case 5: supprimerVoiture();   break;
            case 0: break;
            default: System.out.println("❌ Choix invalide !");
        }
    }

    static void listerVoitures() {
        List<Voiture> liste = voitureService.listerVoitures();
        if (liste.isEmpty()) {
            System.out.println("Aucune voiture trouvée.");
            return;
        }
        System.out.println("\n--- Liste des Voitures ---");
        for (Voiture v : liste) {
            System.out.println("[" + v.getId() + "] " + v);
        }
    }

    static void listerDisponibles() {
        List<Voiture> liste = voitureService.listerDisponibles();
        if (liste.isEmpty()) {
            System.out.println("Aucune voiture disponible.");
            return;
        }
        System.out.println("\n--- Voitures Disponibles ---");
        for (Voiture v : liste) {
            System.out.println("[" + v.getId() + "] " + v);
        }
    }

    static void ajouterVoiture() {
        System.out.println("\n--- Ajouter Voiture ---");
        System.out.print("Marque       : "); String marque  = scanner.nextLine();
        System.out.print("Modèle       : "); String modele  = scanner.nextLine();
        System.out.print("Immatriculation : "); String immat = scanner.nextLine();
        System.out.print("Catégorie    : "); String cat    = scanner.nextLine();
        System.out.print("Prix/jour    : "); double prix   = scanner.nextDouble();
        scanner.nextLine();

        Voiture v = new Voiture(marque, modele, immat, cat, prix);
        voitureService.ajouterVoiture(v);
        System.out.println("Voiture ajoutée !");
    }

    static void modifierVoiture() {
        listerVoitures();
        System.out.print("\nID voiture à modifier : ");
        int id = scanner.nextInt(); scanner.nextLine();

        Voiture v = voitureService.trouverParId(id);
        if (v == null) { System.out.println("Voiture introuvable !"); return; }

        System.out.print("Nouveau prix/jour (" + v.getPrixParJour() + ") : ");
        double prix = scanner.nextDouble(); scanner.nextLine();
        v.setPrixParJour(prix);

        voitureService.modifierVoiture(v);
        System.out.println("Voiture modifiée !");
    }

    static void supprimerVoiture() {
        listerVoitures();
        System.out.print("\nID voiture à supprimer : ");
        int id = scanner.nextInt(); scanner.nextLine();

        voitureService.supprimerVoiture(id);
        System.out.println("Voiture supprimée !");
    }

    // ================================
    // MENU CLIENTS
    // ================================
    static void menuClients() {
        System.out.println("\n--- CLIENTS ---");
        System.out.println("1. Lister tous les clients");
        System.out.println("2. Ajouter un client");
        System.out.println("3. Modifier un client");
        System.out.println("4. Supprimer un client");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1: listerClients();    break;
            case 2: ajouterClient();    break;
            case 3: modifierClient();   break;
            case 4: supprimerClient();  break;
            case 0: break;
            default: System.out.println("❌ Choix invalide !");
        }
    }

    static void listerClients() {
        List<Client> liste = clientService.listerClients();
        if (liste.isEmpty()) {
            System.out.println("Aucun client trouvé.");
            return;
        }
        System.out.println("\n--- Liste des Clients ---");
        for (Client c : liste) {
            System.out.println("[" + c.getid() + "] " + c);
        }
    }

    static void ajouterClient() {
        System.out.println("\n--- Ajouter Client ---");
        System.out.print("Nom       : "); String nom    = scanner.nextLine();
        System.out.print("Prénom    : "); String prenom = scanner.nextLine();
        System.out.print("CIN       : "); String cin    = scanner.nextLine();
        System.out.print("Téléphone : "); String tel    = scanner.nextLine();
        System.out.print("Email     : "); String email  = scanner.nextLine();

        Client c = new Client(nom, prenom, cin, tel, email);
        clientService.ajouterClient(c);
        System.out.println("Client ajouté !");
    }

    static void modifierClient() {
        listerClients();
        System.out.print("\nID client à modifier : ");
        int id = scanner.nextInt(); scanner.nextLine();

        Client c = clientService.trouverParId(id);
        if (c == null) { System.out.println(" Client introuvable !"); return; }

        System.out.print("Nouveau téléphone (" + c.gettelephone() + ") : ");
        String tel = scanner.nextLine();
        c.settelephone(tel);

        System.out.print("Nouvel email (" + c.getemail() + ") : ");
        String email = scanner.nextLine();
        c.setemail(email);

        clientService.modifierClient(c);
        System.out.println("Client modifié !");
    }

    static void supprimerClient() {
        listerClients();
        System.out.print("\nID client à supprimer : ");
        int id = scanner.nextInt(); scanner.nextLine();

        clientService.supprimerClient(id);
        System.out.println("Client supprimé !");
    }

    // ================================
    // MENU RESERVATIONS
    // ================================
    static void menuReservations() {
        System.out.println("\n--- RESERVATIONS ---");
        System.out.println("1. Lister toutes les réservations");
        System.out.println("2. Créer une réservation");
        System.out.println("3. Terminer une réservation");
        System.out.println("4. Annuler une réservation");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1: listerReservations();  break;
            case 2: creerReservation();    break;
            case 3: terminerReservation(); break;
            case 4: annulerReservation();  break;
            case 0: break;
            default: System.out.println("Choix invalide !");
        }
    }

    static void listerReservations() {
        List<Reservation> liste = reservationService.listerReservations();
        if (liste.isEmpty()) {
            System.out.println("Aucune réservation trouvée.");
            return;
        }
        System.out.println("\n--- Liste des Réservations ---");
        for (Reservation r : liste) {
            System.out.println("[" + r.getid() + "] " + r);
        }
    }

    static void creerReservation() {
        System.out.println("\n--- Créer Réservation ---");

        // Afficher clients et voitures disponibles
        listerClients();
        System.out.print("ID client   : ");
        int clientId = scanner.nextInt(); scanner.nextLine();

        listerDisponibles();
        System.out.print("ID voiture  : ");
        int voitureId = scanner.nextInt(); scanner.nextLine();

        Client client   = clientService.trouverParId(clientId);
        Voiture voiture = voitureService.trouverParId(voitureId);

        if (client == null)  { System.out.println("❌ Client introuvable !"); return; }
        if (voiture == null) { System.out.println("❌ Voiture introuvable !"); return; }

        System.out.print("Date début (YYYY-MM-DD) : ");
        LocalDate dateDebut = LocalDate.parse(scanner.nextLine());

        System.out.print("Date fin   (YYYY-MM-DD) : ");
        LocalDate dateFin = LocalDate.parse(scanner.nextLine());

        double montant = reservationService.calculerMontant(
            dateDebut, dateFin, voiture.getPrixParJour()
        );
        System.out.println("Montant total : " + montant + " DH");

        Reservation r = new Reservation(client, voiture, dateDebut, dateFin, montant);
        reservationService.creerReservation(r);
        System.out.println("Réservation créée !");
    }

    static void terminerReservation() {
        listerReservations();
        System.out.print("\nID réservation à terminer : ");
        int id = scanner.nextInt(); scanner.nextLine();

        List<Reservation> liste = reservationService.listerReservations();
        for (Reservation r : liste) {
            if (r.getid() == id) {
                reservationService.terminerReservation(r);
                System.out.println("Réservation terminée ! Voiture libérée.");
                return;
            }
        }
        System.out.println("Réservation introuvable !");
    }

    static void annulerReservation() {
        listerReservations();
        System.out.print("\nID réservation à annuler : ");
        int id = scanner.nextInt(); scanner.nextLine();

        List<Reservation> liste = reservationService.listerReservations();
        for (Reservation r : liste) {
            if (r.getid() == id) {
                reservationService.annulerReservation(r);
                System.out.println("Réservation annulée ! Voiture libérée.");
                return;
            }
        }
        System.out.println("Réservation introuvable !");*/
    }
}