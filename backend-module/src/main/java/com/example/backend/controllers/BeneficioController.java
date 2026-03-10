package com.example.backend.controllers;

import com.example.backend.dtos.BeneficioRequestDTO;
import com.example.backend.dtos.TransferRequestDTO;
import com.example.backend.services.BeneficioService;
import com.example.ejb.Beneficio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
@Tag(name = "Benefícios", description = "API de gerenciamento de benefícios")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar benefícios")
    @GetMapping
    public List<Beneficio> list() {
        return service.list();
    }

    @Operation(summary = "Buscar benefício por ID")
    @GetMapping("/{id}")
    public Beneficio get(@PathVariable Long id) {
        return service.get(id);
    }

    @Operation(summary = "Criar benefício")
    @PostMapping
    public Beneficio create(@Valid @RequestBody BeneficioRequestDTO beneficioDTO) {
        return service.create(beneficioDTO);
    }

    @Operation(summary = "Atualizar benefício")
    @PutMapping("/{id}")
    public Beneficio update(@PathVariable Long id, @Valid @RequestBody BeneficioRequestDTO beneficioDTO) {
        return service.update(id, beneficioDTO);
    }

    @Operation(summary = "Remover benefício")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Realiza transferência de saldo entre dois benefícios")
    @PostMapping("/transfer")
    public void transfer(@Valid @RequestBody TransferRequestDTO dto) {
        service.transfer(dto.getFromId(), dto.getToId(), dto.getAmount());
    }
}