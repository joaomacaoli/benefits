package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero");
        }

        Beneficio from = em.find(Beneficio.class, fromId);
        Beneficio to = em.find(Beneficio.class, toId);

        // BUG: sem validações, sem locking, pode gerar saldo negativo e lost update
        if (from == null || to == null) {
            throw new IllegalArgumentException("Benefício não encontrado");
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        em.merge(from);
        em.merge(to);
    }
}
