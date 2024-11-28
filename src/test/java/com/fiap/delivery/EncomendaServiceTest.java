package com.fiap.delivery;

import com.fiap.delivery.aplication.service.EncomendaService;
import com.fiap.delivery.domain.entities.Encomenda;
import com.fiap.delivery.domain.entities.Morador;
import com.fiap.delivery.infrastructure.database.EncomendaRepository;
import com.fiap.delivery.infrastructure.database.MoradorRepository;
import com.fiap.delivery.infrastructure.messaging.SmsServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EncomendaServiceTest {

    @Mock
    private EncomendaRepository encomendaRepository;

    @Mock
    private MoradorRepository moradorRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private SmsServiceMock smsService;

    @InjectMocks
    private EncomendaService encomendaService;

    private Morador morador;
    private Encomenda encomenda;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        morador = new Morador(1L, "João", "987654321");
        encomenda = new Encomenda(null, "João", "101", "Livro", false, morador);
    }

    @Test
    void testRegistrarEncomenda() {
        when(moradorRepository.findById(1L)).thenReturn(java.util.Optional.of(morador));
        when(encomendaRepository.save(any(Encomenda.class))).thenReturn(encomenda);

        Encomenda result = encomendaService.registrarEncomenda(encomenda);

        verify(encomendaRepository).save(encomenda);
        verify(kafkaTemplate).send("encomendas", encomenda.toString());
        verify(smsService).sendSms("987654321", "Olá, João! Sua encomenda chegou na portaria. Por favor, retire-a quando possível.");

        assertNotNull(result);
        assertEquals("Livro", result.getDescricao());
    }

    @Test
    void testRegistrarRetirada() {
        Encomenda encomendaRetirada = new Encomenda(1L, "João", "101", "Livro", true, morador);

        when(encomendaRepository.findById(1L)).thenReturn(java.util.Optional.of(encomenda));
        when(encomendaRepository.save(any(Encomenda.class))).thenReturn(encomendaRetirada);

        Encomenda result = encomendaService.registrarRetirada(1L);

        verify(encomendaRepository).save(encomendaRetirada);
        verify(kafkaTemplate).send("retiradas", encomendaRetirada.toString());

        assertNotNull(result);
        assertTrue(result.isRetirada());
    }
}
