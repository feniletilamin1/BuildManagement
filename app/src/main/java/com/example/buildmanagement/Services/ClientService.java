package com.example.buildmanagement.Services;

import com.example.buildmanagement.Models.Client;

import java.util.List;


public interface ClientService {
    List<Client> findAll();
    Client findOneById(int id);
    int create(Client client);
    void update(Client client);
    void delete(int id);
}
