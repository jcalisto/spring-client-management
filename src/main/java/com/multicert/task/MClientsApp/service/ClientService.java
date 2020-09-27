package com.multicert.task.MClientsApp.service;

import com.multicert.task.MClientsApp.models.Client;
import com.multicert.task.MClientsApp.repository.ClientRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository repository;


    @Override
    public List<Client> findAll() {
        List<Client> clients = (List<Client>) repository.findAll();
        return clients;
    }

    
    @Override
    public List<Client> findByNameContaining(String name) {
        Assert.notNull(name, "Name can't be null");
        List<Client> clients = (List<Client>) repository.findByNameContaining(name);
        return clients;
    }

    @Override
    public List<Client> findByNif(String nif) {
        Assert.notNull(nif, "NIF can't be null");
        List<Client> clients = (List<Client>) repository.findByNif(nif);
        return clients;
    }
    
    

    @Override
    public void deleteClientById(Long id) {
        Objects.requireNonNull(id);
        repository.deleteById(id);
    }


	@Override
	public Client addClient(String name, String nif, String phone, String address) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(nif);
		Objects.requireNonNull(phone);
		Objects.requireNonNull(address);
		
		Client client = new Client(name, nif, phone, address);
		return repository.save(client);
	}
}