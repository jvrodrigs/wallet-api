package com.example.wallet.Service;

import com.example.wallet.Model.Wallet;
import com.example.wallet.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repository;

    public Wallet save(Wallet wallet){
        return repository.save(wallet);
    }
}
