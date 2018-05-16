package com.example.kaktysig.myapplication.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CredentialsUtils {
    public final static String ts = "1";
    public final static String public_key = "476aaaed52db688e074d91efc3f520fd";
    private final static String private_key = "85d3975b5d3fc50d88c17849da4ea945b94e6b18";

    public static String getHash(){
        String hash = ts + private_key + public_key;
        Log.i("CredentialsUtils", md5(hash));
        return "28511f3428aff54a0452711ec1eb6940";
    }

    private static String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
