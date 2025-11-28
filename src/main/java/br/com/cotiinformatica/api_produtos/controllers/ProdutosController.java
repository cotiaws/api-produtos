package br.com.cotiinformatica.api_produtos.controllers;

import br.com.cotiinformatica.api_produtos.components.JwtBearerComponent;
import br.com.cotiinformatica.api_produtos.dtos.ProdutoRequest;
import br.com.cotiinformatica.api_produtos.interfaces.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutosController {

    private final ProdutoService produtoService;
    private final JwtBearerComponent jwtBearerComponent;

    @PostMapping
    public ResponseEntity<?> post(HttpServletRequest http, @RequestBody ProdutoRequest request) {
        return ResponseEntity.ok(produtoService.criar(request, getUsername((http))));
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> patch(HttpServletRequest http, @PathVariable UUID id, @RequestBody ProdutoRequest request) {
        return ResponseEntity.ok(produtoService.alterar(id, request, getUsername((http))));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(HttpServletRequest http, @PathVariable UUID id) {
        return ResponseEntity.ok(produtoService.excluir(id, getUsername((http))));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int size) {

        return ResponseEntity.ok(produtoService.consultar(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(produtoService.obterPorId(id));
    }

    /**
     * Método utilitário para obter o nome do usuário autenticado
     * a partir do token JWT presente no cabeçalho Authorization.
     */
    private String getUsername(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("Token JWT ausente ou inválido.");
        }

        var token = authorization.replace("Bearer", "").trim();
        return jwtBearerComponent.getUsernameFromToken(token);
    }
}
