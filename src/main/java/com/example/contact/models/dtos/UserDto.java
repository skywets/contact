package com.example.contact.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
