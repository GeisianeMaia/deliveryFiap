package com.fiap.delivery;

import com.fiap.delivery.aplication.service.MoradorService;
import com.fiap.delivery.domain.entities.Morador;
import com.fiap.delivery.infrastructure.database.MoradorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MoradorServiceTest {

    @Mock
    private MoradorRepository moradorRepository;

    @InjectMocks
    private MoradorService moradorService;

    private Morador morador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        morador = new Morador(null, "João", "987654321");
    }

    @Test
    void testCadastrarMorador() {
        when(moradorRepository.save(any(Morador.class))).thenReturn(morador);

        Morador result = moradorService.cadastrarMorador(morador);

        verify(moradorRepository).save(morador);

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }
}
