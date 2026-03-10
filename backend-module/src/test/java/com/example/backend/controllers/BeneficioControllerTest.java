package com.example.backend.controllers;

import com.example.backend.dtos.BeneficioRequestDTO;
import com.example.backend.dtos.TransferRequestDTO;
import com.example.backend.services.BeneficioService;
import com.example.ejb.Beneficio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeneficioController.class)
class BeneficioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficioService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should list beneficios")
    void shouldListBeneficios() throws Exception {

        Beneficio b = new Beneficio();
        b.setId(1L);

        when(service.list()).thenReturn(List.of(b));

        mockMvc.perform(get("/api/v1/beneficios"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get beneficio by id")
    void shouldGetBeneficioById() throws Exception {

        Beneficio beneficio = new Beneficio();
        beneficio.setId(1L);

        when(service.get(1L)).thenReturn(beneficio);

        mockMvc.perform(get("/api/v1/beneficios/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should create beneficio")
    void shouldCreateBeneficio() throws Exception {

        BeneficioRequestDTO dto = new BeneficioRequestDTO();
        dto.setNome("Vale");
        dto.setDescricao("Descrição");
        dto.setValor(BigDecimal.valueOf(100));
        dto.setAtivo(true);

        mockMvc.perform(post("/api/v1/beneficios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(service).create(any());
    }

    @Test
    @DisplayName("Should return 400 when create request is invalid")
    void shouldReturnBadRequestWhenCreateIsInvalid() throws Exception {

        BeneficioRequestDTO dto = new BeneficioRequestDTO();

        mockMvc.perform(post("/api/v1/beneficios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should transfer balance")
    void shouldTransferBalance() throws Exception {

        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setFromId(1L);
        dto.setToId(2L);
        dto.setAmount(BigDecimal.valueOf(50));

        mockMvc.perform(post("/api/v1/beneficios/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(service).transfer(1L, 2L, BigDecimal.valueOf(50));
    }

    @Test
    @DisplayName("Should return 400 when transfer request is invalid")
    void shouldReturnBadRequestWhenTransferIsInvalid() throws Exception {

        TransferRequestDTO dto = new TransferRequestDTO();

        mockMvc.perform(post("/api/v1/beneficios/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}