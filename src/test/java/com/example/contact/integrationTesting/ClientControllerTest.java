package com.example.contact.integrationTesting;

import com.example.contact.models.dtos.ClientDto;
import com.example.contact.models.dtos.ContactDto;
import com.example.contact.models.entitiies.Client;
import com.example.contact.models.entitiies.Contact;
import com.example.contact.models.entitiies.User;
import com.example.contact.models.mappers.mappersImpl.ClientMapperDto;
import com.example.contact.models.mappers.mappersImpl.ContactMapperDto;
import com.example.contact.projectConfig.securityConfig.SecurityConfig;
import com.example.contact.repositories.ClientRepository;
import com.example.contact.repositories.ContactRepository;
import com.example.contact.repositories.UserRepository;
import com.example.contact.utils.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactMapperDto contactMapperDto;
    @Autowired
    private ClientMapperDto clientMapperDto;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ContactRepository contactRepository;

    @BeforeAll
    public void init() {
        clientRepository.deleteAll();
        userRepository.deleteAll();
        User user = new User();
        user.setName("Ozodbek");
        user.setPassword("123");
        userRepository.save(user);
    }

    @BeforeEach
    public void beforeMethod() {
        Optional<User> optionalUser = userRepository.findByName("Ozodbek");
        optionalUser.ifPresent(user -> {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });
    }


    @Test
    @Order(1)
    public void saveClientTest() throws Exception {
        ClientDto clientDto = new ClientDto();
        ContactDto contactDto = new ContactDto();
        contactDto.setPhone("999");
        contactDto.setEmail("email.ru");
        clientDto.setName("header");
        clientDto.setLastName("lastname");
        clientDto.setContactDto(contactDto);
        ResultActions result = mockMvc.perform(post("/client/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));
        result.andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @WithMockCustomUser(username = "Ozodbek", password = "123")
    public void getClientByNameTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/client/byName")
                .queryParam("name", "header")).andReturn();
        Client client = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Client.class);

        this.mockMvc.perform(get("/client/{id}", client.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    ResultActions getClientByIdTest(Long id) throws Exception {
        return (mockMvc.perform(get("/client/{id}", id)));
    }

    @Test
    @Order(4)
    public void updateClientTest() throws Exception {
        Client client = clientRepository.findByName("header");
        Contact contact = contactRepository.findByEmail("email.ru");
        contact.setEmail("updatemail.ru");
        contact.setPhone("8889991111");
        ClientDto clientDto = clientMapperDto.mapToDto(client);
        clientDto.setName("update");
        clientDto.setLastName("update");
        clientDto.setContactDto(contactMapperDto.mapToDto(contact));

        this.mockMvc.perform(put("/client/update", clientDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/client/byName", clientDto.getName()).param("name", "update"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(clientDto.getName())))
                .andExpect(jsonPath("$.lastName", is(clientDto.getLastName())))
                .andExpect(jsonPath("$.contactDto.email", is(clientDto.getContactDto().getEmail())))
                .andExpect(jsonPath("$.contactDto.phone", is(clientDto.getContactDto().getPhone())));
    }

    @Test
    @Order(5)
    public void deleteClientTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/client/byName")
                .queryParam("name", "update")).andReturn();
        Client client = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Client.class);
        var requestBuilder = MockMvcRequestBuilders.delete("/client/{id}", client.getId());
        ResultActions result = mockMvc.perform(requestBuilder);
        result.andExpect(status().isOk());
    }
}
