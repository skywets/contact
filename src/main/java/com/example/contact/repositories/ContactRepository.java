package com.example.contact.repositories;

import com.example.contact.models.entitiies.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
    Contact findByEmail(String email);
}
