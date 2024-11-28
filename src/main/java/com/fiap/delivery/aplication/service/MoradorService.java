package com.fiap.delivery.aplication.service;

import com.fiap.delivery.domain.entities.Morador;
import com.fiap.delivery.infrastructure.database.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoradorService {

    @Autowired
    private MoradorRepository moradorRepository;

    public Morador cadastrarMorador(Morador morador) {
        return moradorRepository.save(morador);
    }
}
