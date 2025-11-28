package br.com.cotiinformatica.api_produtos.controllers;

import br.com.cotiinformatica.api_produtos.dtos.AutenticarRequest;
import br.com.cotiinformatica.api_produtos.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuarioService usuarioService;

    @PostMapping("autenticar")
    public ResponseEntity<?> autenticar(@RequestBody AutenticarRequest request) {
        try {
            var response = usuarioService.Autenticar(request);
            return ResponseEntity.ok().body(response);
        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
