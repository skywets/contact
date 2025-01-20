package com.example.contact.services;

import com.example.contact.models.dtos.ContactDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {
    void create(ContactDto contactDto);
    void update(ContactDto contactDto);
    void delete(Long id);
    ContactDto findById(Long id);
    ContactDto findByEmail(String email);
    List<ContactDto> findAll();
}
