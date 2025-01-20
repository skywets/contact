package com.example.contact.services;

import com.example.contact.models.dtos.ClientDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    void create(ClientDto clientDto);
    void update(ClientDto clientDto);
    void delete(Long id);
    ClientDto findById(Long id);
    ClientDto findByName(String name);
    List<ClientDto> findAll();
}
