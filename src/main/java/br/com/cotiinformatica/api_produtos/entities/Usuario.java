package br.com.cotiinformatica.api_produtos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 25, unique = true, nullable = false)
    private String login;

    @Column(length = 100, nullable = false)
    private String senha;
}

