package com.example.ticketingsystem.util;


import com.example.ticketingsystem.entity.User;
import com.example.ticketingsystem.exceptions.NotFoundException;
import com.example.ticketingsystem.repository.UserRepo;

import java.util.Base64;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Token {
    private static final String SECRET_KEY = "this is the sceret key ";

    public static String generateToken(User user) throws Exception {
        long timestamp = (new Date()).getTime();
        String data = user.getUserName() + ":" + timestamp;
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] hash = sha256Hmac.doFinal(data.getBytes());
        String hashString = Base64.getEncoder().encodeToString(hash);
        return Base64.getEncoder().encodeToString((data + ":" + hashString).getBytes());
    }


    public static String[] decodeToken(String token) {
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);
        return decodedString.split(":");
    }

    public static User validateToken(String token, UserRepo userRepo) throws Exception{
        String[] tokenData = decodeToken(token);
        String username = tokenData[0];
        long timestamp = Long.parseLong(tokenData[1]);
        String tokenHash = tokenData[2];
        User user = userRepo.findByUserName(username).orElseThrow(() -> new NotFoundException("User not found"));
        String data = username + ":" + timestamp;
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] hash = sha256Hmac.doFinal(data.getBytes());
        String recreatedHash = Base64.getEncoder().encodeToString(hash);
        return recreatedHash.equals(tokenHash) ? user : null;

    }
}