package com.example.login.auth;

import com.example.login.entity.User;
import com.example.login.repository.UserRepository;
import com.example.login.token.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthentificationUser {
    private String email;
    private String password;
    private UserRepository userRepository;

    public String verifyAuthentification(AuthentificationUser authentificationUser) throws Exception {
        Optional<User> user = userRepository.findByEmail(authentificationUser.getEmail());
        if (!user.isPresent()){
            throw new Exception("L'email n'existe pas!");
        }else {
            Optional<User> userpwd = userRepository.findByEmailAndPassword(authentificationUser.getEmail(), authentificationUser.getPassword());
            if (!userpwd.isPresent()){
                throw new Exception("Mot de passe Incorrect!");
            }else {
                return TokenGenerator.generateToken(userpwd.get());
            }
        }
    }

    @Autowired
    public AuthentificationUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public AuthentificationUser() {
    }

    public AuthentificationUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
