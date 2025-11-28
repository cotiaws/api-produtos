package br.com.cotiinformatica.api_produtos.services;

import br.com.cotiinformatica.api_produtos.components.CryptoComponent;
import br.com.cotiinformatica.api_produtos.components.JwtBearerComponent;
import br.com.cotiinformatica.api_produtos.dtos.AutenticarRequest;
import br.com.cotiinformatica.api_produtos.dtos.AutenticarResponse;
import br.com.cotiinformatica.api_produtos.interfaces.UsuarioService;
import br.com.cotiinformatica.api_produtos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CryptoComponent cryptoComponent;
    private final JwtBearerComponent jwtBearerComponent;

    @Override
    public AutenticarResponse Autenticar(AutenticarRequest request) {

        //buscar o usuario no banco através do login e senha
        var result = usuarioRepository
                .find(request.login(), cryptoComponent.getSha256(request.senha()));

        if(result.isEmpty())
            throw new IllegalArgumentException("Acesso negado. Usuário inválido.");

        var usuario = result.get();

        return new AutenticarResponse(
            usuario.getId(),
            usuario.getLogin(),
            LocalDateTime.now(),
            jwtBearerComponent.getExpiration().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime(),
            jwtBearerComponent.generateToken(usuario.getLogin(), "usuario")
        );
    }
}
