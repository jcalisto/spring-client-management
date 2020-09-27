package com.multicert.task.MClientsApp.repository;

import com.multicert.task.MClientsApp.models.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
	List<Client> findByNameContaining(String name);
	List<Client> findByNif(String nif);
}