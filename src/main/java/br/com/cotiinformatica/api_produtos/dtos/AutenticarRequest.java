package br.com.cotiinformatica.api_produtos.dtos;

public record AutenticarRequest(
        String login, //login do usuário
        String senha //senha do usuário
) {
}
