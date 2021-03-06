package com.example.wallet.Model.Dto;

import javax.validation.constraints.NotNull;

public class UserWalletDTO {

    private Long id;
    @NotNull(message = "Informe o ID do usuário")
    private Long users;
    @NotNull(message = "Informe o ID da carteira")
    private Long wallet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsers() {
        return users;
    }

    public void setUsers(Long users) {
        this.users = users;
    }

    public Long getWallet() {
        return wallet;
    }

    public void setWallet(Long wallet) {
        this.wallet = wallet;
    }
}
