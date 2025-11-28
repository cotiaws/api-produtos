package br.com.cotiinformatica.api_produtos;

import br.com.cotiinformatica.api_produtos.components.CryptoComponent;
import br.com.cotiinformatica.api_produtos.components.JwtBearerComponent;
import br.com.cotiinformatica.api_produtos.dtos.AutenticarRequest;
import br.com.cotiinformatica.api_produtos.entities.Usuario;
import br.com.cotiinformatica.api_produtos.repositories.UsuarioRepository;
import br.com.cotiinformatica.api_produtos.services.UsuarioServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceUnitTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    CryptoComponent cryptoComponent;

    @Mock
    JwtBearerComponent jwtBearerComponent;

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Test
    @DisplayName("Deve autenticar um usuário com sucesso.")
    void deveAutenticarUmUsuarioComSucesso() {

        var request = new AutenticarRequest("admin", "123");

        var senhaCriptografada = "ABC123";
        when(cryptoComponent.getSha256("123")).thenReturn(senhaCriptografada);

        var usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setLogin("admin");

        when(usuarioRepository.find("admin", senhaCriptografada))
                .thenReturn(Optional.of(usuario));

        when(jwtBearerComponent.getExpiration())
                .thenReturn(Date.from(Instant.now().plusSeconds(3600)));

        when(jwtBearerComponent.generateToken("admin", "usuario"))
                .thenReturn("TOKEN-123");

        var response = usuarioService.Autenticar(request);

        assertNotNull(response);
        assertEquals(usuario.getId(), response.id());
        assertEquals("admin", response.login());
        assertNotNull(response.dataHoraAcesso());
        assertNotNull(response.dataHoraExpiracao());
        assertEquals("TOKEN-123", response.accessToken());
    }

    @Test
    @DisplayName("Deve lançar exceção quando acesso negado.")
    void deveLancarExcecaoQuandoAcessoNegado() {

        var request = new AutenticarRequest("admin", "123");

        when(cryptoComponent.getSha256("123")).thenReturn("XYZ");
        when(usuarioRepository.find("admin", "XYZ"))
                .thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.Autenticar(request));

        assertEquals("Acesso negado. Usuário inválido.", exception.getMessage());
    }
}

