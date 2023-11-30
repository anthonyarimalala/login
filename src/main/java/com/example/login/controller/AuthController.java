package com.example.login.controller;

import com.example.login.auth.AuthentificationUser;
import com.example.login.entity.User;
import com.example.login.repository.UserRepository;
import com.example.login.token.TokenValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @GetMapping
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthentificationUser authentificationUser, HttpServletResponse response) throws Exception {
        AuthentificationUser authentificationUser1 = new AuthentificationUser(userRepository);
        String token = authentificationUser1.verifyAuthentification(authentificationUser);
        return token;
    }

    @GetMapping("/user")
    public User getUserByToken(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
        // Extraire le token du header
        String token = authorizationHeader.replace("Bearer ", "");

        User user = null;
        TokenValidator tokenValidator = new TokenValidator();
        try {
            user = tokenValidator.validateToken(token);
        } catch (Exception e) {
            throw new Exception("Cet utilisateur n'existe pas.");
        }
        return user;
    }
}
