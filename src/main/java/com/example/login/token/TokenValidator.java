package com.example.login.token;

import com.example.login.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class TokenValidator {

    public static void main(String[] args){
        System.out.println(validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6ImFudGhvbnkiLCJlbWFpbCI6ImFudGhvbnlAZ21haWwuY29tIiwicm9sZSI6MTAwLCJleHAiOjE3MDExMDc3NTR9.BZoFDx1bjwJDB2JiuZhC3eGgtBcFbEbjVYclsKPNHqQ").toString());
    }

    private static final String SECRET_KEY = "fdkjnkjbdvksjdqlgnglkjqsklmdnziojadojnmqjsdfbovbmbmdqmodfjhzaadbc";

    public static User validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            Long idUser = Long.parseLong(claims.getSubject());
            String name = claims.get("name", String.class);
            String email = claims.get("email", String.class);
            int role = claims.get("role", Integer.class);

            User user = new User();
            user.setIdUser(idUser);
            user.setName(name);
            user.setEmail(email);
            user.setRole(role);

            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
