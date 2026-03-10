package com.example.ejb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public List<Beneficio> findAll() {
        return em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)
                .getResultList();
    }

    public Beneficio findById(Long id) {
        return em.find(Beneficio.class, id);
    }

    public Beneficio create(Beneficio beneficio) {
        em.persist(beneficio);
        return beneficio;
    }

    public Beneficio update(Long id, Beneficio data) {
        Beneficio beneficio = em.find(Beneficio.class, id);

        if (beneficio == null) {
            throw new IllegalArgumentException("Benefício não encontrado");
        }

        beneficio.setNome(data.getNome());
        beneficio.setDescricao(data.getDescricao());
        beneficio.setValor(data.getValor());
        beneficio.setAtivo(data.getAtivo());

        return em.merge(beneficio);
    }

    public void delete(Long id) {
        Beneficio beneficio = em.find(Beneficio.class, id);

        if (beneficio != null) {
            em.remove(beneficio);
        }
    }

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (fromId == null || toId == null) {
            throw new IllegalArgumentException("Os benefícios de origem e destino são obrigatórios");
        }

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("O benefício de origem e destino não podem ser iguais");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero");
        }

        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio to = em.find(Beneficio.class, toId, LockModeType.PESSIMISTIC_WRITE);

        if (from == null) {
            throw new IllegalArgumentException("Benefício de origem não encontrado");
        }

        if (to == null) {
            throw new IllegalArgumentException("Benefício de destino não encontrado");
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));
    }
}
