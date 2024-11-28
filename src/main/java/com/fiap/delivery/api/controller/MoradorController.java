package com.fiap.delivery.api.controller;

import com.fiap.delivery.aplication.service.MoradorService;
import com.fiap.delivery.domain.entities.Morador;
import com.fiap.delivery.infrastructure.database.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moradores")
public class MoradorController {
    @Autowired
    private MoradorService moradorService;

    @PostMapping
    public Morador cadastrar(@RequestBody Morador morador) {
        return moradorService.cadastrarMorador(morador);
    }
}
