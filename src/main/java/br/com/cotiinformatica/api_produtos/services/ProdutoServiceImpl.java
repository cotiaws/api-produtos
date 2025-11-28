package br.com.cotiinformatica.api_produtos.services;

import br.com.cotiinformatica.api_produtos.dtos.ProdutoRequest;
import br.com.cotiinformatica.api_produtos.dtos.ProdutoResponse;
import br.com.cotiinformatica.api_produtos.entities.Produto;
import br.com.cotiinformatica.api_produtos.interfaces.ProdutoService;
import br.com.cotiinformatica.api_produtos.repositories.ProdutoRepository;
import br.com.cotiinformatica.api_produtos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ProdutoResponse criar(ProdutoRequest request, String username) {

        //Capturar o usuário autenticado
        var usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido."));

        //Preencher os dados do produto
        var produto = new Produto();
        produto.setNome(request.nome());
        produto.setPreco(BigDecimal.valueOf(request.preco()));
        produto.setQuantidade(request.quantidade());
        produto.setUsuarioCriacao(usuario);

        //Gravando o produto no banco de dados
        produtoRepository.save(produto);

        //Retornando os dados do produto cadastrado
        return toResponse(produto);
    }

    @Override
    public ProdutoResponse alterar(UUID id, ProdutoRequest request, String username) {

        //Verificar usuário autenticado
        var usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido."));

        //Buscar produto
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        // PATCH → Atualiza apenas se o valor foi enviado
        if (request.nome() != null && !request.nome().isBlank()) {
            produto.setNome(request.nome());
        }

        if (request.preco() != null && request.preco() > 0) {
            produto.setPreco(BigDecimal.valueOf(request.preco()));
        }

        if (request.quantidade() != null && request.quantidade() >= 0) {
            produto.setQuantidade(request.quantidade());
        }

        produto.setUsuarioAlteracao(usuario);

        produtoRepository.save(produto);

        //Retornando os dados do produto atualizado
        return toResponse(produto);
    }

    @Override
    public ProdutoResponse excluir(UUID id, String username) {

        //Verificar usuário autenticado
        var usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido."));

        //Buscar produto
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        produto.setUsuarioExclusao(usuario);

        produtoRepository.delete(produto);

        return toResponse(produto);
    }

    @Override
    public Page<ProdutoResponse> consultar(int page, int size) {

        if(page >= 1) page--;

        if(size > 25)
            throw new IllegalArgumentException("A consulta deve ser de no máximo 25 produtos.");

        var pageable = PageRequest.of(page, size);

        var produtos = produtoRepository.findAll(pageable);

        //Converter Page<Produto> -> Page<ProdutoResponse>
        var responseList = produtos
                .stream()
                .map(this::toResponse)
                .toList();

        return new PageImpl<>(responseList, pageable, produtos.getTotalElements());
    }

    @Override
    public ProdutoResponse obterPorId(UUID id) {

        //Buscar produto
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        return toResponse(produto);
    }

    // Método para converter entidade → DTO
    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco().doubleValue(),
                produto.getQuantidade()
        );
    }
}
