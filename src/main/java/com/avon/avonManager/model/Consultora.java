package com.avon.avonManager.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "consultora")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consultora {
    @Id
    @GeneratedValue
    private UUID id;
    private Long codigo;
    private String nome;
    private String contatos;
    private String email;
    private String enderecoResidencial;
    private String bairro;
    private String cidade;
    private String cep;
    private Integer grupoLider;
    private Integer credito;
    private Integer creditoDisponivel;
    private Integer pontosAcumulados;
    private String cnIniciante;
    private String nivel;
    private String situacao;
    private Integer pontosFaltantes;
    private Integer ciclosRestantes;
    private Integer debito;
    private String espacoAberto;
    private String espacoComVendas;
    private String origem;
}
