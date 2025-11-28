package br.com.cotiinformatica.api_produtos.interfaces;

import br.com.cotiinformatica.api_produtos.dtos.ProdutoRequest;
import br.com.cotiinformatica.api_produtos.dtos.ProdutoResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProdutoService {

    ProdutoResponse criar(ProdutoRequest request, String username);

    ProdutoResponse alterar(UUID id, ProdutoRequest request, String username);

    ProdutoResponse excluir(UUID id, String username);

    Page<ProdutoResponse> consultar(int page, int size);

    ProdutoResponse obterPorId(UUID id);
}
