package com.fiap.delivery;

import com.fiap.delivery.api.controller.EncomendaController;
import com.fiap.delivery.aplication.service.EncomendaService;
import com.fiap.delivery.domain.entities.Encomenda;
import com.fiap.delivery.domain.entities.Morador;
import com.fiap.delivery.infrastructure.database.EncomendaRepository;
import com.fiap.delivery.infrastructure.database.MoradorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class EncomendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EncomendaService encomendaService;

    @MockBean
    private EncomendaRepository encomendaRepository;

    @MockBean
    private MoradorRepository moradorRepository;

    private Encomenda encomenda;
    private Morador morador;

    @BeforeEach
    void setUp() {
        morador = new Morador(1L, "João", "987654321");
        encomenda = new Encomenda(null, "João", "101", "Livro", false, morador);
        mockMvc = MockMvcBuilders.standaloneSetup(new EncomendaController(encomendaService)).build();
    }

    @Test
    void testRegistrarEncomenda() throws Exception {
        when(encomendaService.registrarEncomenda(any(Encomenda.class))).thenReturn(encomenda);

        mockMvc.perform(post("/encomendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomeMorador\":\"João\",\"apartamento\":\"101\",\"descricao\":\"Livro\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeMorador").value("João"))
                .andExpect(jsonPath("$.descricao").value("Livro"));
    }

    @Test
    void testRetirarEncomenda() throws Exception {
        when(encomendaService.registrarRetirada(1L)).thenReturn(encomenda);

        mockMvc.perform(put("/encomendas/retirar/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Livro"))
                .andExpect(jsonPath("$.retirada").value(true));
    }
}
