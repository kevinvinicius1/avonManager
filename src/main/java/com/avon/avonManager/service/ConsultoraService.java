package com.avon.avonManager.service;

import com.avon.avonManager.model.Consultora;
import com.avon.avonManager.model.ConsultoraRequestDTO;
import com.avon.avonManager.model.ConsultoraResponseDTO;
import com.avon.avonManager.repository.ConsultoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.nio.charset.StandardCharsets;
import java.util.Map;


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

    public Consultora updateConsultora(ConsultoraRequestDTO data, Long toUpdate) {
        Consultora existingConsultora = repository.getByCodigo(toUpdate);
        existingConsultora.setCodigo(data.codigo());
        existingConsultora.setContatos(data.contatos());
        existingConsultora.setNome(data.nome());
        existingConsultora.setEmail(data.email());
        existingConsultora.setEnderecoResidencial(data.enderecoResidencial());
        existingConsultora.setBairro(data.bairro());
        existingConsultora.setCidade(data.cidade());
        existingConsultora.setCep(data.cep());
        existingConsultora.setGrupoLider(data.grupoLider());
        existingConsultora.setCredito(data.credito());
        existingConsultora.setCreditoDisponivel(data.creditoDisponivel());
        existingConsultora.setPontosAcumulados(data.pontosAcumulados());
        existingConsultora.setCnIniciante(data.cnIniciante());
        existingConsultora.setNivel(data.nivel());
        existingConsultora.setSituacao(data.situacao());
        existingConsultora.setEspacoAberto(data.espacoAberto());
        existingConsultora.setPontosFaltantes(data.pontosFaltantes());
        existingConsultora.setCiclosRestantes(data.ciclosRestantes());
        existingConsultora.setDebito(StringUtils.isNotBlank(data.debito()) ? data.debito() : null);
        existingConsultora.setEspacoComVendas(data.espacoComVendas());
        existingConsultora.setOrigem(data.origem());
        return repository.save(existingConsultora);


        
    }
    

    @ExceptionHandler
    public Consultora createConsultora(ConsultoraRequestDTO data) {
             //Cadastra uma nova consultora
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
        newConsultora.setDebito(StringUtils.isNotBlank(data.debito()) ? data.debito() : null);
        newConsultora.setEspacoComVendas(data.espacoComVendas());
        newConsultora.setOrigem(data.origem());
        repository.save(newConsultora);
    return newConsultora;

    }
    public static List<ConsultoraRequestDTO> parseCSVToConsultoraRequestDTO(MultipartFile file) {
        List<ConsultoraRequestDTO> consultoras = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withDelimiter(';')
                     .withTrim()               // Remove espaços em branco ao redor dos valores
                     .withIgnoreEmptyLines()   // Ignora linhas vazias
                     .withFirstRecordAsHeader() // Descomente se o CSV possuir cabeçalho
             )) {
            for (CSVRecord record : csvParser) {



               if (record.size() != 21) {
                   throw new IllegalArgumentException("Linha " + record.getRecordNumber() +
                           " do CSV deve conter exatamente 21 campos. Encontrado: " + record.size());
               }

                Long codigo = parseLong(record.get(0));
                String nome = record.get(1);
                String contatos = record.get(2);
                String email = record.get(3);
                String enderecoResidencial = record.get(4);
                String bairro = record.get(5);
                String cidade = record.get(6);
                String cep = record.get(7);
                Integer grupoLider = parseInteger(record.get(8));
                Integer credito = parseInteger(record.get(9));
                Integer creditoDisponivel = parseInteger(record.get(10));
                Integer pontosAcumulados = parseInteger(record.get(11));
                String cnIniciante = record.get(12);
                String nivel = record.get(13);
                String situacao = record.get(14);
                Integer pontosFaltantes = parseInteger(record.get(15));
                Integer ciclosRestantes = parseInteger(record.get(16));
                String debito = record.get(17);
                Boolean espacoAberto = parseBoolean(record.get(18));
                Boolean espacoComVendas = parseBoolean(record.get(19));
                String origem = record.get(20);

                ConsultoraRequestDTO dto = new ConsultoraRequestDTO(codigo, nome, contatos, email,
                        enderecoResidencial, bairro, cidade, cep, grupoLider, credito, creditoDisponivel,
                        pontosAcumulados, cnIniciante, nivel, situacao, pontosFaltantes, ciclosRestantes,debito, espacoAberto, espacoComVendas, origem);

                consultoras.add(dto);
            }
            return consultoras;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo CSV", e);
        }
    }

    // Métodos auxiliares para conversão de tipos
    private static Long parseLong(String value) {
        value = value.trim();
        return value.isEmpty() ? null : Long.valueOf(value);
    }

    private static Integer parseInteger(String value) {
        value = value.trim();
        return value.isEmpty() ? null : Integer.valueOf(value);
    }

    private static Boolean parseBoolean(String value) {
        value = value.trim();
        return value.isEmpty() ? null : Boolean.valueOf(value);
    }

}




