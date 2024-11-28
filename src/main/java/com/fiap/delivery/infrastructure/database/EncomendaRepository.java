package com.fiap.delivery.infrastructure.database;

import com.fiap.delivery.domain.entities.Encomenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {
}
