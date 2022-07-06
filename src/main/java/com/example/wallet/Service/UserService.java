package com.example.wallet.Service;

import com.example.wallet.Model.User;
import com.example.wallet.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User save(User user){
        return repository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return repository.findByEmailEquals(email);
    }
}
