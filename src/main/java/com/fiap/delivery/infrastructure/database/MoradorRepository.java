package com.fiap.delivery.infrastructure.database;

import com.fiap.delivery.domain.entities.Morador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoradorRepository extends JpaRepository<Morador, Long> {
}
