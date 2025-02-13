package com.avon.avonManager.model;

public record ConsultoraRequestDTO(Long codigo, String nome, String contatos, String email,
                                   String enderecoResidencial, String bairro, String cidade,
                                   String cep, Integer grupoLider, Integer credito, Integer creditoDisponivel,
                                   Integer pontosAcumulados, String cnIniciante, String nivel, String situacao,
                                   Integer pontosFaltantes, Integer ciclosRestantes, Integer debito, Boolean espacoAberto,
                                   Boolean espacoComVendas, String origem) {
}
