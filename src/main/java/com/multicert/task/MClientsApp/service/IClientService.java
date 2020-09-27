package com.multicert.task.MClientsApp.service;

import com.multicert.task.MClientsApp.models.Client;

import java.util.List;

public interface IClientService {
	
    List<Client> findAll();
    List<Client> findByNameContaining(String name);
    List<Client> findByNif(String nif);
    void deleteClientById(Long id);
    Client addClient(String name, String nif, String phone, String address);
}