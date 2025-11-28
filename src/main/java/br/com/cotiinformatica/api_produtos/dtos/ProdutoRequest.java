package br.com.cotiinformatica.api_produtos.dtos;

public record ProdutoRequest(
        String nome, //nome do produto
        Double preco, //preço do produto
        Integer quantidade //quantidade do produto
) {
}
