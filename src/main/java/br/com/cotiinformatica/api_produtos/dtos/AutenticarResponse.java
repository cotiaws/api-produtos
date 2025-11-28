package br.com.cotiinformatica.api_produtos.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record AutenticarResponse(
        UUID id,    //Id do usuário
        String login, //Login do usuário
        LocalDateTime dataHoraAcesso, //Data e hora de acesso
        LocalDateTime dataHoraExpiracao, //Data e hora de expiração
        String accessToken //Token JWT
) {
}
