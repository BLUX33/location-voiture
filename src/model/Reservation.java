package model;

import java.time.LocalDate;

public class Reservation {
	public enum Statut {
        EN_COURS, TERMINEE, ANNULEE
    }
	private int id;
	private Client client;
	private Voiture voiture;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private double montantTotal;
    private Statut statut;
    //CONSTRUCTEUR SANS ID
    public Reservation(Client client,Voiture voiture,LocalDate dateDebut,LocalDate dateFin,double montantTotal) {
		this.client = client;
		this.voiture = voiture;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.montantTotal = montantTotal;
        this.statut = Statut.EN_COURS;
	}
    //CONSTRUCTEUR COMPLET
    public Reservation(int id,Client client,Voiture voiture,LocalDate dateDebut,LocalDate dateFin,double montantTotal,Statut statut) {
		this.id = id;
    	this.client = client;
		this.voiture = voiture;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.montantTotal = montantTotal;
        this.statut = statut;
	}
    //GETTERS
    public int getid() {return id;}
    public Client getclient() {return client;}
    public Voiture getvoiture() {return voiture;}
    public LocalDate getDateDebut() {return dateDebut;}
    public LocalDate getDateFin() {return dateFin;}
    public double getMontantTotal() {return montantTotal;}
    public Statut getStatut() {return statut;}
    //SETTERS
    public void setId(int id) { this.id = id; }
    public void setClient(Client client) {this.client = client;}
    public void setVoiture(Voiture voiture)   { this.voiture = voiture; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin)     { this.dateFin = dateFin; }
    public void setMontantTotal(double montant)   { this.montantTotal = montant; }
    public void setStatut(Statut statut)          { this.statut = statut; }
    
    // Calculer la durée en jours

    public long getDureeJours() {
        return dateDebut.until(dateFin).getDays();
    }
    @Override
    public String toString() {
        return "Reservation #" + id + " | " + client + " | " 
               + voiture + " | " + dateDebut + " → " + dateFin 
               + " | " + montantTotal + " DH";
    }

    
}
