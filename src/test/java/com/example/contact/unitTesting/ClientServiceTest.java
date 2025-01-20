package com.example.contact.unitTesting;

import com.example.contact.models.dtos.ClientDto;
import com.example.contact.models.dtos.ContactDto;
import com.example.contact.models.entitiies.Client;
import com.example.contact.models.entitiies.Contact;
import com.example.contact.models.mappers.mappersImpl.ClientMapperDto;
import com.example.contact.models.mappers.mappersImpl.ContactMapperDto;
import com.example.contact.repositories.ClientRepository;
import com.example.contact.repositories.ContactRepository;
import com.example.contact.services.servicesImpl.ClientServiceImpl;
import com.example.contact.services.servicesImpl.ContactServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private ClientMapperDto clientDtoMapperDto;
    @Mock
    private ContactMapperDto contactDtoMapperDto;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @InjectMocks
    private ContactServiceImpl contactServiceImpl;

    private Client client;
    private Contact contact;
    private ClientDto clientDto;
    private ContactDto contactDto;

    @BeforeEach
    public void init() {
        client = new Client();
        contact = new Contact();
        contact.setId(1l);
        contact.setPhone("999000");
        contact.setPhone("999000");
        contact.setEmail("mail.ru");
        client.setId(1l);
        client.setName("name");
        client.setLastName("last");
        client.setContact(contact);

        contactDto = new ContactDto(contact.getId(), contact.getPhone(), contact.getEmail());
        clientDto = new ClientDto(client.getId(), client.getName(), client.getLastName(), contactDto);
        System.out.println(contactDto);
        System.out.println(clientDto);
    }

    @Test
    @Order(1)
    public void saveClientTest() {
        given(contactRepository.save(contact)).willReturn(contact);
        given(clientRepository.save(client)).willReturn(client);
        given(contactDtoMapperDto.mapToDto(contact)).willReturn(contactDto);
        given(clientDtoMapperDto.mapToDto(client)).willReturn(clientDto);
        contactServiceImpl.create(contactDtoMapperDto.mapToDto(contact));
        clientServiceImpl.create(clientDtoMapperDto.mapToDto(client));

        verify(clientRepository).save(any(Client.class));
        assertThat(client.getId()).isGreaterThan(0);
    }
    @Test
    @Order(2)
    public void getClientByIdTest() {
        given(contactRepository.findById(contact.getId())).willReturn(Optional.of(contact));
        given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));
        given(contactDtoMapperDto.mapToDto(contact)).willReturn(contactDto);
        given(clientDtoMapperDto.mapToDto(client)).willReturn(clientDto);
        ClientDto existingSupplier = clientServiceImpl.findById(client.getId());

        verify(clientRepository).findById(client.getId());
        assertThat(existingSupplier).isNotNull();
    }

    @Test
    @Order(3)
    public void updateClientTest() {
        given(contactRepository.findById(contact.getId())).willReturn(Optional.of(contact));
        given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));
        given(contactDtoMapperDto.mapToDto(contact)).willReturn(contactDto);
        given(clientDtoMapperDto.mapToDto(client)).willReturn(clientDto);
        clientDto.setName("updateName");
        clientDto.setLastName("updateAddress");
        contactDto.setPhone("000000");
        contactDto.setEmail("updateemail.ru");
        clientDto.setContactDto(contactDto);
        given(contactRepository.save(contact)).willReturn(contact);
        given(clientRepository.save(client)).willReturn(client);
        contactServiceImpl.update(contactDto);
        clientServiceImpl.update(clientDto);


        verify(clientRepository).save(any(Client.class));
        assertThat(client.getName()).isEqualTo("updateName");
        assertThat(client.getLastName()).isEqualTo("updateAddress");
        assertThat(client.getContact().getPhone()).isEqualTo("000000");
        assertThat(client.getContact().getEmail()).isEqualTo("updateemail.ru");
    }

    @Test
    @Order(4)
    public void deleteSupplierTest() {
        willDoNothing().given(clientRepository).deleteById(client.getId());
        clientServiceImpl.delete(client.getId());

        then(clientRepository).should().deleteById(client.getId());
        Optional<Client> supplier = clientRepository.findById(1l);
        assertThat(supplier).isEmpty();
    }

}
