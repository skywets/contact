package com.example.contact.services.servicesImpl;

import com.example.contact.exception.NotFoundException;
import com.example.contact.models.dtos.ContactDto;
import com.example.contact.models.entitiies.Contact;
import com.example.contact.models.mappers.MapperDto;
import com.example.contact.repositories.ContactRepository;
import com.example.contact.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactService")
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final MapperDto<ContactDto, Contact, ContactDto> mapperDto;

    @Override
    public void create(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setPhone(contactDto.getPhone());
        contact.setEmail(contactDto.getEmail());
        contactRepository.save(contact);
    }

    @Override
    public void update(ContactDto contactDto) {
        Contact contact = getByIdOrElseThrow(contactDto.getId());
        contact.setPhone(contactDto.getPhone());
        contact.setEmail(contactDto.getEmail());
        contactRepository.save(contact);
    }

    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public ContactDto findById(Long id) {
        return mapperDto.mapToDto(getByIdOrElseThrow(id));
    }

    @Override
    public ContactDto findByEmail(String email) {
        return mapperDto.mapToDto(contactRepository.findByEmail(email));
    }

    @Override
    public List<ContactDto> findAll() {
        return mapperDto.maptoDtoList(contactRepository.findAll());
    }

    private Contact getByIdOrElseThrow(Long id) {
        return contactRepository.findById(id).orElseThrow(
                () -> new NotFoundException("The contact doesn't exist"));
    }
}
