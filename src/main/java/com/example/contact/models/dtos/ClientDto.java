package com.example.contact.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ClientDto {
    private Long id;
    private String name;
    private String lastName;
    private ContactDto contactDto;

}
