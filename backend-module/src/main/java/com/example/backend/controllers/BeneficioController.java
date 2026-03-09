package com.example.backend.controllers;

import com.example.backend.services.BeneficioService;
import com.example.ejb.Beneficio;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Beneficio> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public Beneficio get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Beneficio create(@RequestBody Beneficio beneficio) {
        return service.create(beneficio);
    }

    @PutMapping("/{id}")
    public Beneficio update(@PathVariable Long id, @RequestBody Beneficio beneficio) {
        return service.update(id, beneficio);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/transfer")
    public void transfer(
            @RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam BigDecimal amount
    ) {
        service.transfer(fromId, toId, amount);
    }
}