package br.com.cotiinformatica.api_produtos.configurations;

import br.com.cotiinformatica.api_produtos.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Value("${jwt.secretkey}")
    private String jwtSecretkey;

    @Bean
    FilterRegistrationBean<AuthenticationFilter> authenticationFilterConfig() {

        //Criando a configuração para o filter de autenticação
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();

        //Instanciando e configurando o filter com a chave do TOKEN JWT
        registration.setFilter(new AuthenticationFilter(jwtSecretkey));

        //Configurando os endpoints para o qual o filter será aplicado
        registration.addUrlPatterns("/api/v1/produtos/*");

        //retornando a configuração
        return registration;
    }
}
