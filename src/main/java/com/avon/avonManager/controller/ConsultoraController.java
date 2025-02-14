package com.avon.avonManager.controller;
import com.avon.avonManager.model.Consultora;
import com.avon.avonManager.model.ConsultoraRequestDTO;
import com.avon.avonManager.model.ConsultoraResponseDTO;
import com.avon.avonManager.repository.ConsultoraRepository;
import com.avon.avonManager.service.ConsultoraService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultora")
public class ConsultoraController {

    @Autowired
    private ConsultoraService consultoraService;

    @Autowired
    private ConsultoraRepository repository;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Consultora> create(@RequestBody ConsultoraRequestDTO data){
        Consultora newConsultora = this.consultoraService.createConsultora(data);
        return ResponseEntity.ok(newConsultora);
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<Map<String, Object>> verifyCode(@RequestBody ConsultoraRequestDTO data) {
        Consultora existingConsultora = repository.getByCodigo(data.codigo());

        Map<String, Object> response = new HashMap<>();
        //caso existir consultora irá executar
        if (existingConsultora != null) {
            response.put("dadosAntigos", existingConsultora);
            response.put("dadosNovos", data);
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body(response);
        }
        //Cria nova consultora caso não exista
        Consultora newConsultora = this.consultoraService.createConsultora(data);
        response.put("novaConsultora", data);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Map<String, Object> data){
        //verifica se tem os valores dadosNovos e dadosAntigos no map

        if (!data.containsKey("dadosNovos")) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O campo 'dadosNovos' é obrigatório."));
        }
        if (!data.containsKey("dadosAntigos")) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O campo 'dadosAntigos' é obrigatório."));
        }
      //obtendo código da revendedora no sistema
        Map <String, Object> dadosAntigos = (Map<String, Object>) data.get("dadosAntigos");
        Long toUpdate = ((Number) dadosAntigos.get("codigo")).longValue();

        //converte o map dadosNovos para ConsultoraRequestDTO
        ObjectMapper objectMapper = new ObjectMapper();
        ConsultoraRequestDTO dadosNovos = objectMapper.convertValue(data.get("dadosNovos"), ConsultoraRequestDTO.class);
        consultoraService.updateConsultora(dadosNovos, toUpdate);
        return ResponseEntity.ok(Map.of("mensagem", "Consultora atualizada com sucesso.", "dadosAtuais", dadosNovos));

    }
@GetMapping
    public ResponseEntity<List<ConsultoraResponseDTO>> getAllConsultoras(@RequestParam (defaultValue = "0")int page, @RequestParam (defaultValue = "10")int size){
        List<ConsultoraResponseDTO> allConsultoras = this.consultoraService.getAllConsultoras(page, size);
        return ResponseEntity.ok(allConsultoras);
}
    @PostMapping(consumes ="multipart/form-data")
    public ResponseEntity<Consultora> createFromCSV(@RequestParam("file") MultipartFile file){
       List<Consultora>consultorasCriadas = new ArrayList<>();
        List<ConsultoraRequestDTO>dtos = ConsultoraService.parseCSVToConsultoraRequestDTO(file);
       for (ConsultoraRequestDTO dto : dtos) {
           Consultora newConsultora = this.consultoraService.createConsultora(dto);
           consultorasCriadas.add(newConsultora);
       }
                        return ResponseEntity.ok(consultorasCriadas.get(0));


    }
}


