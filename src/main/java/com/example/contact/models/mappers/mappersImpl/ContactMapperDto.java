package com.example.contact.models.mappers.mappersImpl;

import com.example.contact.models.dtos.ContactDto;
import com.example.contact.models.entitiies.Contact;
import com.example.contact.models.mappers.MapperDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@AllArgsConstructor
public class ContactMapperDto implements MapperDto<ContactDto, Contact, ContactDto> {
    @Override
    public ContactDto mapToDto(Contact entity) {
        ContactDto contactDto = new ContactDto();
        contactDto.setPhone(entity.getPhone());
        contactDto.setEmail(entity.getEmail());
        return contactDto;
    }

    @Override
    public Contact mapToEntities(ContactDto dto) {
        Contact contact = new Contact();
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        return contact;
    }

    @Override
    public List<ContactDto> maptoDtoList(Iterable<Contact> entities) {
        List<ContactDto> contactDtos = new ArrayList<>();
        for (Contact contact : entities) {
            contactDtos.add(mapToDto(contact));
        }
        return contactDtos;
    }

    @Override
    public List<Contact> mapToEntities(Iterable<ContactDto> dtos) {
        List<Contact> contacts = new ArrayList<>();
        for (ContactDto contactDto : dtos) {
            contacts.add(mapToEntities(contactDto));
        }
        return contacts;
    }
}
