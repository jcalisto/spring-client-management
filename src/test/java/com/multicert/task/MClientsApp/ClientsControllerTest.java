package com.multicert.task.MClientsApp;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.multicert.task.MClientsApp.controllers.ClientsController;
import com.multicert.task.MClientsApp.models.Client;
import com.multicert.task.MClientsApp.service.ClientService;

@WebMvcTest(ClientsController.class)
public class ClientsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClientService service;
	
	@Test
	public void testHomeDefault() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(content().string(containsString("Get Started")));
	}
	
	@Test
	public void testClientsListing() throws Exception {
		List<Client> clients = new ArrayList<Client>();
		clients.add(new Client("John Heath", "223000561", "964583502", "St. Coniston Garth 30"));
		clients.add(new Client("Peter Heath", "223000562", "964583503", "St. Coniston Garth 30"));
		clients.add(new Client("Marta Heath", "223000563", "964583504", "St. Coniston Garth 30"));
		clients.add(new Client("Lucius Heath", "223000564", "964583505", "St. Coniston Garth 30"));
		clients.add(new Client("Hannah Heath", "223000565", "964583506", "St. Coniston Garth 30"));
		when(service.findAll()).thenReturn(clients);
		this.mockMvc.perform(get("/clients")).andExpect(status().isOk())
				.andExpect(content().string(containsString("John Heath")))
				.andExpect(content().string(containsString("Peter Heath")))
				.andExpect(content().string(containsString("Marta Heath")))
				.andExpect(content().string(containsString("Lucius Heath")))
				.andExpect(content().string(containsString("Hannah Heath")));
	}
	
	@Test
	public void testClientAppearsWhenSearched() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("filter", "John");
	
		List<Client> clients = new ArrayList<Client>();
		Client client = new Client("John Heath", "223000561", "964583502", "St. Coniston Garth 30");
		clients.add(client);
		
		when(service.findByNameContaining("John")).thenReturn(clients);
		when(service.findByNif("John")).thenReturn(new ArrayList<>());
		this.mockMvc.perform(get("/clients/search").params(requestParams))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("John Heath")));
	}
	
	
	@Test
	public void testClientAppearsWhenAdded() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("name", "John");
		requestParams.add("nif", "223000561");
		requestParams.add("phone", "964583502");
		requestParams.add("address", "St. Coniston Garth 30");
		
		List<Client> clients = new ArrayList<Client>();
		Client client = new Client("John Heath", "223000561", "964583502", "St. Coniston Garth 30");
		clients.add(client);
		
		when(service.addClient("John Heath", "223000561", "964583502", "St. Coniston Garth 30")).thenReturn(client);
		when(service.findAll()).thenReturn(clients);
		this.mockMvc.perform(post("/clients/add").params(requestParams))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("John Heath")));
	}
	
	@Test
	public void testErrorWhenAddingInvalidClient() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("name", "");
		requestParams.add("nif", "223000561");
		requestParams.add("phone", "964583502");
		requestParams.add("address", "St. Coniston Garth 30");
		
		when(service.addClient("", "223000561", "964583502", "St. Coniston Garth 30")).thenThrow(MockitoException.class);
		when(service.findAll()).thenReturn(new ArrayList<>());
		
		this.mockMvc.perform(post("/clients/add").params(requestParams))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Error adding client, invalid arguments")));
	}

}
