package com.example.backend.services;

import com.example.ejb.Beneficio;
import com.example.ejb.BeneficioEjbService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BeneficioService {

    private final BeneficioEjbService ejbService;

    public BeneficioService(BeneficioEjbService ejbService) {
        this.ejbService = ejbService;
    }

    public List<Beneficio> list() {
        return ejbService.findAll();
    }

    public Beneficio get(Long id) {
        return ejbService.findById(id);
    }

    @Transactional
    public Beneficio create(Beneficio beneficio) {
        return ejbService.create(beneficio);
    }

    @Transactional
    public Beneficio update(Long id, Beneficio beneficio) {
        return ejbService.update(id, beneficio);
    }

    @Transactional
    public void delete(Long id) {
        ejbService.delete(id);
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        ejbService.transfer(fromId, toId, amount);
    }
}