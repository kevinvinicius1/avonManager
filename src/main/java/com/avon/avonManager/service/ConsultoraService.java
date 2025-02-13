package com.avon.avonManager.service;

import com.avon.avonManager.model.Consultora;
import com.avon.avonManager.model.ConsultoraRequestDTO;
import com.avon.avonManager.model.ConsultoraResponseDTO;
import com.avon.avonManager.repository.ConsultoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConsultoraService {
    @Autowired
    private ConsultoraRepository repository;

    public List<ConsultoraResponseDTO> getAllConsultoras(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Consultora> consultorasPage = this.repository.findAll(pageable);
        return  consultorasPage.map(consultora -> new ConsultoraResponseDTO(consultora.getId(), consultora.getCodigo(), consultora.getNome(), consultora.getContatos(), consultora.getEmail(),
                consultora.getEnderecoResidencial(), consultora.getBairro(), consultora.getCidade(), consultora.getCep(),
                consultora.getGrupoLider(), consultora.getCredito(), consultora.getCreditoDisponivel(), consultora.getPontosAcumulados(),
                consultora.getCnIniciante(), consultora.getNivel(), consultora.getSituacao(), consultora.getPontosFaltantes(),
                consultora.getCiclosRestantes(), consultora.getDebito(), consultora.getEspacoAberto(), consultora.getEspacoComVendas(),
                consultora.getOrigem())).stream().toList();
    }

    public Consultora createConsultora(ConsultoraRequestDTO data) {
        Consultora newConsultora = new Consultora();

        newConsultora.setCodigo(data.codigo());
        newConsultora.setContatos(data.contatos());
        newConsultora.setNome(data.nome());
        newConsultora.setEmail(data.email());
        newConsultora.setEnderecoResidencial(data.enderecoResidencial());
        newConsultora.setBairro(data.bairro());
        newConsultora.setCidade(data.cidade());
        newConsultora.setCep(data.cep());
        newConsultora.setGrupoLider(data.grupoLider());
        newConsultora.setCredito(data.credito());
        newConsultora.setCreditoDisponivel(data.creditoDisponivel());
        newConsultora.setPontosAcumulados(data.pontosAcumulados());
        newConsultora.setCnIniciante(data.cnIniciante());
        newConsultora.setNivel(data.nivel());
        newConsultora.setSituacao(data.situacao());
        newConsultora.setEspacoAberto(data.espacoAberto());
        newConsultora.setPontosFaltantes(data.pontosFaltantes());
        newConsultora.setCiclosRestantes(data.ciclosRestantes());
        newConsultora.setDebito(data.debito());
        newConsultora.setEspacoComVendas(data.espacoComVendas());
        newConsultora.setOrigem(data.origem());
        repository.save(newConsultora);
    return newConsultora;

    }




}
