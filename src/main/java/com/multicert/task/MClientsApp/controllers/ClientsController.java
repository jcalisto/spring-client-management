package com.multicert.task.MClientsApp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.multicert.task.MClientsApp.models.Client;
import com.multicert.task.MClientsApp.service.IClientService;

@Controller
public class ClientsController {

	@Autowired
    private IClientService clientService;

	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/clients")
	public String listClients(Model model) {
		List<Client> clients;
		try {
			clients = (List<Client>) clientService.findAll();
		} catch(Exception e) {	//Todo specify?
			clients = new ArrayList<Client>();
			model.addAttribute("${errorMessage", "Can't fetch Clients.");
		}
		model.addAttribute("clients", clients);
		return "clients";
	}

	@GetMapping("/clients/search")
	public String filterClients(@RequestParam (value="filter", required=false) String filter, Model model) {
		List<Client> clients = clients = new ArrayList<Client>();
		try {
			clients.addAll((List<Client>) clientService.findByNameContaining(filter));
			clients.addAll((List<Client>) clientService.findByNif(filter));
		} catch(Exception e) {	//Todo specify?
			model.addAttribute("errorMessage", "Search text is not valid.");
		}
		model.addAttribute("clients", clients);
		return "clients";
	}

	@RequestMapping(value = "/client/delete", method = RequestMethod.POST)
	public String deleteClient(@RequestParam("clientId") long clientId) {
		clientService.deleteClientById(clientId);
		return "redirect:/clients";
	}
	
	@RequestMapping(value = "/clients/add", method = RequestMethod.POST)
	public String addClient(@RequestParam("name") String name, @RequestParam("nif") String nif, @RequestParam("phone") String phone, @RequestParam("address") String address, Model model) {
		List<Client> clients = clients = new ArrayList<Client>();
		try {
			clientService.addClient(name, nif, phone, address);
		} catch(Exception e) {	//Todo specify?
			model.addAttribute("errorMessage", "Error adding client, invalid arguments");
		}
		clients = (List<Client>) clientService.findAll();
		model.addAttribute("clients", clients);
		return "clients";
	}

}