package com.example.contact.integrationTesting;

import com.example.contact.models.dtos.UserDto;
import com.example.contact.projectConfig.securityConfig.SecurityConfig;
import com.example.contact.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    UserDto userDto;

    @BeforeAll
    public void init() {
        userDto = new UserDto();
        userDto.setId(1l);
        userDto.setName("Ozodbek");
        userDto.setPassword("123");
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testRegisterUser() throws Exception {
        ResultActions result = mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        result.andExpect(status().isOk());
    }
}
