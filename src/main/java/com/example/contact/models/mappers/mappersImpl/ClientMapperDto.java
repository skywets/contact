package com.example.contact.models.mappers.mappersImpl;

import com.example.contact.models.dtos.ClientDto;
import com.example.contact.models.dtos.ContactDto;
import com.example.contact.models.entitiies.Client;
import com.example.contact.models.entitiies.Contact;
import com.example.contact.models.mappers.MapperDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ClientMapperDto implements MapperDto<ClientDto, Client, ClientDto> {

    private final MapperDto<ContactDto, Contact, ContactDto> mapperDto;
    @Override
    public ClientDto mapToDto(Client entity) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(entity.getId());
        clientDto.setName(entity.getName());
        clientDto.setLastName(entity.getLastName());
        clientDto.setContactDto(mapperDto.mapToDto(entity.getContact()));
        return clientDto;
    }

    @Override
    public Client mapToEntities(ClientDto dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setLastName(dto.getLastName());
        client.setContact(mapperDto.mapToEntities(dto.getContactDto()));
        return client;
    }

    @Override
    public List<ClientDto> maptoDtoList(Iterable<Client> entities) {
        List<ClientDto> clientDtos = new ArrayList<>();
        for (Client client : entities) {
            clientDtos.add(mapToDto(client));
        }
        return clientDtos;
    }

    @Override
    public List<Client> mapToEntities(Iterable<ClientDto> dtos) {
        List<Client> clients = new ArrayList<>();
        for (ClientDto clientDto : dtos) {
            clients.add(mapToEntities(clientDto));
        }
        return clients;
    }
}
