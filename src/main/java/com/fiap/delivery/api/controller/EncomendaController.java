package com.fiap.delivery.api.controller;

import com.fiap.delivery.aplication.service.EncomendaService;
import com.fiap.delivery.domain.entities.Encomenda;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/encomendas")
public class EncomendaController {

    private final EncomendaService encomendaService;

    public EncomendaController(EncomendaService encomendaService) {
        this.encomendaService = encomendaService;
    }
    @PostMapping
    public ResponseEntity<Object> registrarEncomenda(@RequestBody Encomenda encomenda) {
        try {
            Encomenda encomendaRegistrada = encomendaService.registrarEncomenda(encomenda);
            return new ResponseEntity<>(encomendaRegistrada, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao registrar encomenda: " + e.getMessage());
        }
    }


    @PutMapping("/retirar/{id}")
    public ResponseEntity<Encomenda> retirarEncomenda(@PathVariable Long id) {
        try {
            Encomenda encomendaRetirada = encomendaService.registrarRetirada(id);
            return ResponseEntity.ok(encomendaRetirada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
