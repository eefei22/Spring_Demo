package com.example.demo.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("eefei123 -> " + encoder.encode("eefei123"));
        System.out.println("galaxyS22 -> " + encoder.encode("galaxyS22"));
    }

}