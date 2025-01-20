package com.example.contact.repositories;

import com.example.contact.models.entitiies.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByName(String name);
}
