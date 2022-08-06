package com.example.wallet.Security;

import com.example.wallet.Model.User;

public class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword());
    }

    public JwtUserFactory() {
    }
}