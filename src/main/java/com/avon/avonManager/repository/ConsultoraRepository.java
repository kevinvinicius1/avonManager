package com.avon.avonManager.repository;

import com.avon.avonManager.model.Consultora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsultoraRepository extends JpaRepository<Consultora, UUID> {
    Consultora getByCodigo(Long codigo);
}
