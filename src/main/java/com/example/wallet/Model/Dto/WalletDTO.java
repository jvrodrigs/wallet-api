package com.example.wallet.Model.Dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class WalletDTO {

    private Long id;
    @Length(min = 3, message = "O nome da carteira deve conter no mínimo 3 caracteres")
    @NotNull(message = "Por favor, informe um nome para a carteira")
    private String name;
    @NotNull(message = "Por favor, informe um valor para a carteira")
    private BigDecimal value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
