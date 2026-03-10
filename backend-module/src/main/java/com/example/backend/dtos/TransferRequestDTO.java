package com.example.backend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransferRequestDTO {

    @NotNull(message = "O id do benefício de origem é obrigatório")
    private Long fromId;

    @NotNull(message = "O id do benefício de destino é obrigatório")
    private Long toId;

    @NotNull(message = "O valor da transferência é obrigatório")
    @Positive(message = "O valor da transferência deve ser maior que zero")
    private BigDecimal amount;

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}