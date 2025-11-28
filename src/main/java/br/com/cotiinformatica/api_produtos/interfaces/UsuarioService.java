package br.com.cotiinformatica.api_produtos.interfaces;

import br.com.cotiinformatica.api_produtos.dtos.AutenticarRequest;
import br.com.cotiinformatica.api_produtos.dtos.AutenticarResponse;

public interface UsuarioService {

    AutenticarResponse Autenticar(AutenticarRequest request);
}
