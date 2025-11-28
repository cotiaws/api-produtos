package br.com.cotiinformatica.api_produtos.repositories;

import br.com.cotiinformatica.api_produtos.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

}
