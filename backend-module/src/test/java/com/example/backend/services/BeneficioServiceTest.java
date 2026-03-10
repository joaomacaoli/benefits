package com.example.backend.services;

import com.example.backend.dtos.BeneficioRequestDTO;
import com.example.ejb.Beneficio;
import com.example.ejb.BeneficioEjbService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficioServiceTest {

    @Mock
    private BeneficioEjbService ejbService;

    @InjectMocks
    private BeneficioService service;

    @Test
    @DisplayName("Should list all beneficios")
    void shouldListAllBeneficios() {

        Beneficio b1 = new Beneficio();
        Beneficio b2 = new Beneficio();

        when(ejbService.findAll()).thenReturn(List.of(b1, b2));

        List<Beneficio> result = service.list();

        assertEquals(2, result.size());
        verify(ejbService).findAll();
    }

    @Test
    @DisplayName("Should return beneficio by id")
    void shouldReturnBeneficioById() {

        Beneficio beneficio = new Beneficio();
        beneficio.setId(1L);

        when(ejbService.findById(1L)).thenReturn(beneficio);

        Beneficio result = service.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(ejbService).findById(1L);
    }

    @Test
    @DisplayName("Should create beneficio")
    void shouldCreateBeneficio() {

        BeneficioRequestDTO dto = new BeneficioRequestDTO();
        dto.setNome("Vale alimentação");
        dto.setDescricao("Benefício mensal");
        dto.setValor(BigDecimal.valueOf(500));
        dto.setAtivo(true);

        Beneficio beneficio = new Beneficio();

        when(ejbService.create(any())).thenReturn(beneficio);

        Beneficio result = service.create(dto);

        assertNotNull(result);

        verify(ejbService).create(any(Beneficio.class));
    }

    @Test
    @DisplayName("Should update beneficio")
    void shouldUpdateBeneficio() {

        BeneficioRequestDTO dto = new BeneficioRequestDTO();
        dto.setNome("Atualizado");
        dto.setDescricao("Descrição atualizada");
        dto.setValor(BigDecimal.valueOf(300));
        dto.setAtivo(true);

        when(ejbService.update(eq(1L), any())).thenReturn(new Beneficio());

        Beneficio result = service.update(1L, dto);

        assertNotNull(result);

        verify(ejbService).update(eq(1L), any());
    }

    @Test
    @DisplayName("Should delete beneficio")
    void shouldDeleteBeneficio() {

        service.delete(1L);

        verify(ejbService).delete(1L);
    }

    @Test
    @DisplayName("Should transfer balance")
    void shouldTransferBalance() {

        BigDecimal amount = BigDecimal.valueOf(100);

        service.transfer(1L, 2L, amount);

        verify(ejbService).transfer(1L, 2L, amount);
    }

    @Test
    @DisplayName("Should not transfer when source and destination are equal")
    void shouldThrowExceptionWhenTransferIdsAreEqual() {

        BigDecimal amount = BigDecimal.valueOf(100);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(1L, 1L, amount)
        );

        assertEquals(
                "O benefício de origem e destino não podem ser iguais",
                exception.getMessage()
        );

        verify(ejbService, never()).transfer(any(), any(), any());
    }
}