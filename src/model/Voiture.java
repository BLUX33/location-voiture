package model;

public class Voiture {
	public enum Statut{
		DISPONIBLE,
		LOUEE
	}
	private int id;
	private String marque;
	private String modele;
	private String immatriculation;
	private String categorie;
	private double Prixparjour;
	private Statut statut;
	//CONSTRUCTEUR SANS ID : POUR INSERTION
	public Voiture(String marque,String modele,String immatriculation,String categorie,double Prixparjour) {
		this.marque = marque;
		this.modele = modele;
		this.immatriculation = immatriculation;
		this.categorie = categorie;
		this.Prixparjour = Prixparjour;
		this.statut = statut.DISPONIBLE;
	}
	//CONSTRUCTEUR COMPLET: POUR LECTURE DEPUIS DB
	public Voiture(int id,String marque,String modele,String immatriculation,String categorie,double Prixparjour,Statut statut) {
		this.id = id;
		this.marque = marque;
		this.modele = modele;
		this.immatriculation = immatriculation;
		this.categorie = categorie;
		this.Prixparjour = Prixparjour;
		this.statut = statut;
	}
	// Getters
    public int getId()                  { return id; }
    public String getMarque()           { return marque; }
    public String getModele()           { return modele; }
    public String getImmatriculation()  { return immatriculation; }
    public String getCategorie()        { return categorie; }
    public double getPrixParJour()      { return Prixparjour; }
    public Statut getStatut()           { return statut; }

    // Setters
    public void setId(int id)                        { this.id = id; }
    public void setMarque(String marque)             { this.marque = marque; }
    public void setModele(String modele)             { this.modele = modele; }
    public void setImmatriculation(String immat)     { this.immatriculation = immat; }
    public void setCategorie(String categorie)       { this.categorie = categorie; }
    public void setPrixParJour(double prixParJour)   { this.Prixparjour = prixParJour; }
    public void setStatut(Statut statut)             { this.statut = statut; }
    @Override
    public String toString() {
        return marque + " " + modele + 
               " (immatriculation: " + immatriculation + ")" +
               " | Prix/J: " + Prixparjour +
               " | Statut: " + statut;
    }
}

