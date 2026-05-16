package service;

import model.Reservation;
import model.Voiture;
import repository.ReservationRepository;
import repository.VoitureRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservationService {

    private ReservationRepository reservationRepo = new ReservationRepository();
    private VoitureRepository voitureRepo = new VoitureRepository();

    // Calculer le montant total
    public double calculerMontant(LocalDate debut, LocalDate fin, double prixJour) {
        long jours = ChronoUnit.DAYS.between(debut, fin);
        return jours * prixJour;
    }

    // Créer une réservation + changer statut voiture
    public void creerReservation(Reservation r) {
        reservationRepo.save(r);
        Voiture v = r.getvoiture();
        v.setStatut(Voiture.Statut.LOUEE);
        voitureRepo.update(v);
    }

    // Terminer une réservation + libérer la voiture
    public void terminerReservation(Reservation r) {
        r.setStatut(Reservation.Statut.TERMINEE);
        reservationRepo.update(r);
        Voiture v = r.getvoiture();
        v.setStatut(Voiture.Statut.DISPONIBLE);
        voitureRepo.update(v);
    }

    // Annuler une réservation
    public void annulerReservation(Reservation r) {
        r.setStatut(Reservation.Statut.ANNULEE);
        reservationRepo.update(r);
        Voiture v = r.getvoiture();
        v.setStatut(Voiture.Statut.DISPONIBLE);
        voitureRepo.update(v);
    }

    public List<Reservation> listerReservations() {
        return reservationRepo.findAll();
    }
}