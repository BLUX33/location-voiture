package model;

import java.time.LocalDate;

import model.Reservation.Statut;

public class Client {
		
	private int id;
	private String nom;
	private String prenom;
	private String cin;
	private String telephone;
	private String email;
	//CONSTRUCTEUR SANS ID : POUR INSERTION
	public Client(String nom,String prenom,String cin,String telephone,String email) {
		this.nom = nom;
		this.prenom = prenom;
		this.cin = cin;
		this.telephone = telephone;
		this.email = email;
	}
	//CONSTRUCTEUR COMPLET : LECTURE DEPUIS DB
	public Client(int id,String nom,String prenom,String cin,String telephone,String email) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.cin = cin;
		this.telephone = telephone;
		this.email = email;
	}
	
	//getters
	
	public int getid() {return id;}
	public String getnom() { return nom;}
	public String getprenom() { return prenom;}
	public String getcin() { return cin;}
	public String gettelephone() { return telephone;}
	public String getemail() { return email;}
	
	//setters
	public void setId(int id) { this.id = id; }
    public void setenom(String nom) {this.nom = nom;}
    public void setprenom(String prenom)   { this.prenom = prenom; }
    public void setcin(String cin) { this.cin = cin; }
    public void settelephone(String telephone)     { this.telephone = telephone; }
    public void setemail(String email)   { this.email = email; }
    @Override
    public String toString() {
        return nom + " " + prenom + 
               " (CIN: " + cin + ")" +
               " | Tel: " + telephone +
               " | Email: " + email;
    }
}

