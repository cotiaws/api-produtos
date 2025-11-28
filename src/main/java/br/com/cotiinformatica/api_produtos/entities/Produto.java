package br.com.cotiinformatica.api_produtos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE produto SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidade;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraUltimaAlteracao;

    @ManyToOne
    @JoinColumn
    private Usuario usuarioCriacao;

    @ManyToOne
    @JoinColumn
    private Usuario usuarioAlteracao;

    @ManyToOne
    @JoinColumn
    private Usuario usuarioExclusao;

    @Column(nullable = false)
    private boolean ativo;

    @PrePersist
    public void onCreate() {
        dataHoraCriacao = LocalDateTime.now();
        ativo = true;
    }

    @PreUpdate
    public void onUpdate() {
        dataHoraUltimaAlteracao = LocalDateTime.now();
    }
}
