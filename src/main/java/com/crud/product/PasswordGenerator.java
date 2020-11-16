package com.crud.product;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //String rawPassword = "admin";
        //String rawPassword = "user";
        String rawPassword = "tommy";
        System.out.println(encoder.encode(rawPassword));
    }
}
