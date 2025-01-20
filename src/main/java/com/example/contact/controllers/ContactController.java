package com.example.contact.controllers;

import com.example.contact.models.dtos.ContactDto;
import com.example.contact.services.ContactService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@AllArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/add")
    void addContact(@RequestBody ContactDto contactDto) {
        contactService.create(contactDto);
    }

    @PutMapping("/{id}")
    void updateContact(@RequestBody @Valid ContactDto contactDto) {
        contactService.update(contactDto);
    }

    @DeleteMapping("/{id}")
    void deleteContact(@PathVariable Long id) {
        contactService.delete(id);
    }

    @GetMapping("/{id}")
    public ContactDto getById(@PathVariable Long id) {
        return contactService.findById(id);
    }

    @GetMapping("/byEmail")
    public ContactDto getByEmail(@RequestParam String email) {
        return contactService.findByEmail(email);
    }

    @GetMapping()
    public List<ContactDto> getAllContacts() {
        return contactService.findAll();
    }

}
