package com.example.wallet.Utils;

import com.example.wallet.Model.User;
import com.example.wallet.Service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Util {

    private static UserService service;

    public Util(UserService service){
        Util.service = service;
    }

    public static Long getAuthenticatedUserId(){
        try{
            Optional<User> user = service.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

            return user.map(User::getId).orElse(null);
        } catch (Exception e){
            return null;
        }
    }
}
