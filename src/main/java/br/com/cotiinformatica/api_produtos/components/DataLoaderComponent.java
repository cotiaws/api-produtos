package br.com.cotiinformatica.api_produtos.components;

import br.com.cotiinformatica.api_produtos.entities.Usuario;
import br.com.cotiinformatica.api_produtos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoaderComponent implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CryptoComponent cryptoComponent;

    @Override
    public void run(String... args) throws Exception {

        //Verificar se não há usuários cadastrados
        if(usuarioRepository.count() == 0) {

            var usuario = new Usuario();
            usuario.setLogin("administrador");
            usuario.setSenha(cryptoComponent.getSha256("@Admin2025"));

            usuarioRepository.save(usuario);
        }
    }
}
