package com.example.contact.models.entitiies;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "contactId")
    private Contact contact;
}
