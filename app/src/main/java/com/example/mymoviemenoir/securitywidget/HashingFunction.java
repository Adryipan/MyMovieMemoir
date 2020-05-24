package com.example.mymoviemenoir.securitywidget;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingFunction {
    public static String getHash(String password){
        String hashed = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100,16).substring(1));
            }
            hashed = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashed;
    }

}
