package br.com.cotiinformatica.api_produtos.dtos;

import java.util.UUID;

public record ProdutoResponse(
        UUID id,
        String nome,
        Double preco,
        Integer quantidade
) {
}
