package com.multicert.task.MClientsApp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.multicert.task.MClientsApp.models.Client;
import com.multicert.task.MClientsApp.repository.ClientRepository;
import com.multicert.task.MClientsApp.service.ClientService;

@RunWith(SpringRunner.class) 
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ClientServiceTests {

	@Autowired
	private ClientService clientService;

	@MockBean
	private ClientRepository repository;


	@Test
	public void testFindAllReturnsClientsList() {
		when(repository.findAll()).thenReturn(Stream.of(new Client("John Heath", "223000561", "964583501", "St. Coniston Garth 30"), new Client("Peter Heath", "223000562", "964583501", "St. Coniston Garth 30")).collect(Collectors.toList()));
		assertEquals(2, clientService.findAll().size());
	}
	
	@Test
	public void testDeleteUser() {
		clientService.deleteClientById(1L);
		verify(repository).deleteById(1L);
	}
	
	@Test
	public void testAddUserWithCorrectArguments() {
		Client client = new Client("John Heath", "223000561", "964583501", "St. Coniston Garth 30");
		when(repository.save(client)).thenReturn(client);
		assertEquals(client, clientService.addClient("John Heath", "223000561", "964583501", "St. Coniston Garth 30"));
	}
	
	
	@Test
	public void testAddUserWithEmptyNIF() {
		when(repository.save(any())).thenThrow(MockitoException.class);		//Todo specify exception?
		Assertions.assertThrows(MockitoException.class, () -> {
			clientService.addClient("John", "", "964583501", "St. Coniston Garth 30");
		  });
	}
	
	@Test
	public void testFindClientByName() {
		List<Client> clientsList = new ArrayList<Client>();
		Client client1 = new Client("John Heath", "223000561", "964583501", "St. Coniston Garth 30");
		Client client2 = new Client("Peter Heath", "223000563", "964583502", "St. Coniston Garth 30");
		clientsList.add(client1);
		clientsList.add(client2);
		when(repository.findByNameContaining(any())).thenReturn(clientsList);
		assertEquals(clientsList, clientService.findByNameContaining("Heath"));
	}
	
	@Test
	public void testFindClientByNif() {
		List<Client> clientsList = new ArrayList<Client>();
		Client client = new Client("John Heath", "223000561", "964583501", "St. Coniston Garth 30");
		clientsList.add(client);
		when(repository.findByNif(any())).thenReturn(clientsList);
		assertEquals(clientsList, clientService.findByNif("223000561"));
	}

}
