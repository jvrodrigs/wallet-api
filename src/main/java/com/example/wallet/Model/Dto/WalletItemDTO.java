package com.example.wallet.Model.Dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

public class WalletItemDTO {

    private Long id;
    @NotNull(message = "Informe qual carteira pertence este item")
    private Long wallet;
    @NotNull(message = "Informe uma data")
    private Date date;
    @NotNull(message = "Informe o tipo deste item")
    @Pattern(regexp = "^(ENTRADA|SAÍDA)$", message = "Para informar o tipo, somente são aceitos os valores: ENTRADA ou SAÍDA")
    private String type;
    @NotNull(message = "Informe a descrição deste item")
    @Length(min = 5, message = "A descrição dos itens, deve ter no mínimo 5 caracteres")
    private String description;
    @NotNull(message = "É necessário informar o valor deste item")
    private BigDecimal value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWallet() {
        return wallet;
    }

    public void setWallet(Long wallet) {
        this.wallet = wallet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
