package service;
import repository.VoitureRepository;
import model.Voiture;
import java.util.List;

public class VoitureService {
	private VoitureRepository repo = new VoitureRepository();

    public void ajouterVoiture(Voiture v)     { repo.save(v); }
    public void modifierVoiture(Voiture v)    { repo.update(v); }
    public void supprimerVoiture(int id)      { repo.delete(id); }
    public List<Voiture> listerVoitures()     { return repo.findAll(); }
    public List<Voiture> listerDisponibles()  { return repo.findDisponibles(); }
    public Voiture trouverParId(int id)       { return repo.findById(id); }
}
