package com.farm2pot.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUtils {
    private final PasswordEncoder passwordEncoder;

    public String encodePassword (String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String oldEncPass, String newPass){
        return passwordEncoder.matches(oldEncPass, newPass);
    }
}
