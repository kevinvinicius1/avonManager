package com.avon.avonManager.model;

import java.util.UUID;

public record ConsultoraResponseDTO(UUID id, Long codigo, String nome, String contatos, String email,
                                    String enderecoResidencial, String bairro, String cidade,
                                    String cep, Integer grupoLider, Integer credito, Integer creditoDisponivel,
                                    Integer pontosAcumulados, String cnIniciante, String nivel, String situacao,
                                    Integer pontosFaltantes, Integer ciclosRestantes, String debito, Boolean espacoAberto,
                                    Boolean espacoComVendas, String origem)  {
}
