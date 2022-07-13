package com.example.wallet.Service;

import com.example.wallet.Model.UserWallet;
import com.example.wallet.Repository.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWalletService {

    @Autowired
    private UserWalletRepository repository;

    public UserWallet save(UserWallet w){
        return repository.save(w);
    }
}
