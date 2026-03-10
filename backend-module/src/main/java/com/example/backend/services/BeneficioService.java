package com.example.backend.services;

import com.example.backend.dtos.BeneficioRequestDTO;
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
        public Beneficio create(BeneficioRequestDTO dto) {
            Beneficio b = new Beneficio();

            b.setNome(dto.getNome());
            b.setDescricao(dto.getDescricao());
            b.setValor(dto.getValor());
            b.setAtivo(dto.getAtivo());

            return ejbService.create(b);
        }

    @Transactional
    public Beneficio update(Long id, BeneficioRequestDTO dto) {
        Beneficio b = new Beneficio();

        b.setNome(dto.getNome());
        b.setDescricao(dto.getDescricao());
        b.setValor(dto.getValor());
        b.setAtivo(dto.getAtivo());

        return ejbService.update(id, b);
    }

    @Transactional
    public void delete(Long id) {
        ejbService.delete(id);
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("O benefício de origem e destino não podem ser iguais");
        }

        ejbService.transfer(fromId, toId, amount);
    }
}