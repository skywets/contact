package com.example.contact.services.servicesImpl;

import com.example.contact.exception.NotFoundException;
import com.example.contact.models.dtos.ClientDto;
import com.example.contact.models.entitiies.Client;
import com.example.contact.models.entitiies.Contact;
import com.example.contact.models.mappers.MapperDto;
import com.example.contact.repositories.ClientRepository;
import com.example.contact.repositories.ContactRepository;
import com.example.contact.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clientService")
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ContactRepository contactRepository;
    private final MapperDto<ClientDto, Client, ClientDto> mapperDto;

    @Override
    public void create(ClientDto clientDto) {
        Client client = new Client();
        Contact contact = new Contact();
        client.setName(clientDto.getName());
        client.setLastName(clientDto.getLastName());
        contact.setPhone(clientDto.getContactDto().getPhone());
        contact.setEmail(clientDto.getContactDto().getEmail());
        client.setContact(contact);
        contactRepository.save(contact);
        clientRepository.save(client);
    }

    @Override
    public void update(ClientDto clientDto) {
        Client client = getByIdOrElseThrow(clientDto.getId());
        Contact contact = client.getContact();
        contact.setPhone(clientDto.getContactDto().getPhone());
        contact.setEmail(clientDto.getContactDto().getEmail());
        client.setName(clientDto.getName());
        client.setLastName(clientDto.getLastName());
        client.setContact(contact);
        contactRepository.save(contact);
        clientRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public ClientDto findById(Long id) {
        return mapperDto.mapToDto(getByIdOrElseThrow(id));
    }

    @Override
    public ClientDto findByName(String name) {
        return mapperDto.mapToDto(clientRepository.findByName(name));
    }

    @Override
    public List<ClientDto> findAll() {
        return mapperDto.maptoDtoList(clientRepository.findAll());
    }

    private Client getByIdOrElseThrow(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("The client doesn't exist"));
    }
}
