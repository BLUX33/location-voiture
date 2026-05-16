package service;
import repository.ClientRepository;
import model.Client;
import java.util.List;

public class ClientService {
	private ClientRepository repo = new ClientRepository();

    public void ajouterClient(Client c)    { repo.save(c); }
    public void modifierClient(Client c)   { repo.update(c); }
    public void supprimerClient(int id)    { repo.delete(id); }
    public List<Client> listerClients()    { return repo.findAll(); }
    public Client trouverParId(int id)     { return repo.findById(id); }
}
