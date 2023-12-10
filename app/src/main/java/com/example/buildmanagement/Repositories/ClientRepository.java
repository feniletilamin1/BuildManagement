package com.example.buildmanagement.Repositories;

import com.example.buildmanagement.Models.Client;

import java.util.List;


public interface ClientRepository {
    List<Client> findAll();
    Client findOneById(int id);
    int save(Client client);
    int update(Client client);
    int delete(int id);
}
