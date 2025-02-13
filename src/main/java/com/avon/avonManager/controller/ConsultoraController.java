package com.avon.avonManager.controller;
import com.avon.avonManager.model.Consultora;
import com.avon.avonManager.model.ConsultoraRequestDTO;
import com.avon.avonManager.model.ConsultoraResponseDTO;
import com.avon.avonManager.service.ConsultoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/consultora")
public class ConsultoraController {

    @Autowired
    private ConsultoraService consultoraService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Consultora> create(@RequestBody ConsultoraRequestDTO consultoraRequestDTO){
        Consultora newConsultora = this.consultoraService.createConsultora(consultoraRequestDTO);
        return ResponseEntity.ok(newConsultora);
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


