package com.company.Controllers.PasswordHelper;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordHelper {

    public String generateHash(String enteredPassword){
        return BCrypt.hashpw(enteredPassword,BCrypt.gensalt());
    }
    public boolean isCorrectPassword(String enteredPassword, String dbPassword){
       return  BCrypt.checkpw(enteredPassword,dbPassword);
    }
}
