package com.example.login.token;

import com.example.login.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenGenerator {

    public static void main(String[] args){
        User user = new User(1L,"anthony","anthony@gmail.com","anthony",100);
        System.out.println("Token: "+generateToken(user));
    }

    private static final String SECRET_KEY = "fdkjnkjbdvksjdqlgnglkjqsklmdnziojadojnmqjsdfbovbmbmdqmodfjhzaadbc";

    public static String generateToken(User user) {
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user.getIdUser().toString());
        builder.claim("name", user.getName());
        builder.claim("email", user.getEmail());
        builder.claim("role", user.getRole());
        builder.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60))); // 1 hour
        builder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);

        return builder.compact();
    }
}
