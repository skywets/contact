package com.example.contact.controllers;

import com.example.contact.models.dtos.ClientDto;
import com.example.contact.models.dtos.UserDto;
import com.example.contact.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Create a client ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created a client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @PostMapping("/add")
    void addClient(@RequestBody ClientDto clientDto) {
        clientService.create(clientDto);
    }

    @Operation(summary = "Update a client ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated a client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @PutMapping("/update")
    void updateClient(@RequestBody ClientDto clientDto) {
        clientService.update(clientDto);
    }

    @Operation(summary = "Delete a client ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })


    @DeleteMapping("/{id}")
    void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }

    @Operation(summary = "Get Client by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Client by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/{id}")
    public ClientDto getById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @Operation(summary = "Get Client by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Client by name",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/byName")
    public ClientDto getByName(@RequestParam String name) {
        return clientService.findByName(name);
    }

    @Operation(summary = "Get list of clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list of clients",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping()
    public List<ClientDto> getAllClients() {
        return clientService.findAll();
    }

}
