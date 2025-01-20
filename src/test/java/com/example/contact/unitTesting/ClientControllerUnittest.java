package com.example.contact.unitTesting;

import com.example.contact.controllers.ClientController;
import com.example.contact.models.dtos.ClientDto;
import com.example.contact.models.dtos.ContactDto;
import com.example.contact.services.ClientService;
import com.example.contact.utils.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext
public class ClientControllerUnittest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;
    ClientDto clientDto;
    ContactDto contactDto;

    @BeforeEach
    public void init() {
        clientDto = new ClientDto();
        contactDto = new ContactDto();
        contactDto.setPhone("7778889999");
        contactDto.setEmail("mail.ru");
        clientDto.setId(1l);
        clientDto.setName("client");
        clientDto.setLastName("lastName");
        clientDto.setContactDto(contactDto);
        ClientController clientcontroller = mock(ClientController.class);
    }

    @Test
    @Order(1)
    @WithMockCustomUser(username = "ozod", password = "1234")
    public void saveClientTest() throws Exception {
        doNothing().when(clientService).create(isA(ClientDto.class));
        ResultActions result = mockMvc.perform(post("/client/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        verify(clientService, times(1)).create(clientDto);

        result.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @WithMockCustomUser(username = "ozod", password = "1234")
    public void getClientByIdTest() throws Exception {
        given(clientService.findById(clientDto.getId())).willReturn(clientDto);
        ResultActions result = mockMvc.perform(get("/client/{id}", clientDto.getId()));
        verify(clientService, times(1)).findById(clientDto.getId());
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(clientDto.getName())))
                .andExpect(jsonPath("$.lastName", is(clientDto.getLastName())))
                .andExpect(jsonPath("$.contactDto.phone", is(clientDto.getContactDto().getPhone())))
                .andExpect(jsonPath("$.contactDto.email", is(clientDto.getContactDto().getEmail())));

    }

    @Test
    @Order(3)
    @WithMockCustomUser(username = "ozod", password = "1234")
    public void updateClientTest() throws Exception {
        given(clientService.findById(clientDto.getId())).willReturn(clientDto);
        clientDto.setName("newName");
        clientDto.setLastName("newLastName");
        contactDto.setPhone("99888777");
        contactDto.setEmail("newmail.ru");
        clientDto.setContactDto(contactDto);
        doNothing().when(clientService).update(isA(ClientDto.class));

        ResultActions result = mockMvc.perform(put("/client/update", clientDto)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        verify(clientService).update(clientDto);
        result.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @WithMockCustomUser(username = "ozod", password = "1234")
    public void deleteClientTest() throws Exception {
        willDoNothing().given(clientService).delete(clientDto.getId());
        ResultActions result = mockMvc.perform(delete("/client/{id}", clientDto.getId()));
        verify(clientService).delete(clientDto.getId());
        result.andDo(print())
                .andExpect(status().isOk());

    }
}
