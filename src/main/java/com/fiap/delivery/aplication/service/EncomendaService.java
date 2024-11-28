package com.fiap.delivery.aplication.service;

import com.fiap.delivery.domain.entities.Encomenda;
import com.fiap.delivery.domain.entities.Morador;
import com.fiap.delivery.infrastructure.database.EncomendaRepository;
import com.fiap.delivery.infrastructure.database.MoradorRepository;
import com.fiap.delivery.infrastructure.messaging.SmsServiceMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EncomendaService {

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private SmsServiceMock smsService;

    public Encomenda registrarEncomenda(Encomenda encomenda) {
        Long moradorId = encomenda.getMorador().getId();
        Morador morador = moradorRepository.findById(moradorId)
                .orElseThrow(() -> new RuntimeException("Morador não encontrado para a encomenda"));

        encomenda.setMorador(morador);

        Encomenda encomendaSalva = encomendaRepository.save(encomenda);

        kafkaTemplate.send("encomendas", encomendaSalva.toString());

        String mensagemSms = "Olá, " + morador.getNome() + "! Sua encomenda chegou na portaria. " +
                "Por favor, retire-a quando possível.";
        smsService.sendSms(morador.getTelefone(), mensagemSms);

        return encomendaSalva;
    }

    public Encomenda registrarRetirada(Long encomendaId) {
        Encomenda encomenda = encomendaRepository.findById(encomendaId)
                .orElseThrow(() -> new RuntimeException("Encomenda não encontrada"));

        encomenda.setRetirada(true);

        Encomenda encomendaAtualizada = encomendaRepository.save(encomenda);

        kafkaTemplate.send("retiradas", encomendaAtualizada.toString());

        return encomendaAtualizada;
    }
}
