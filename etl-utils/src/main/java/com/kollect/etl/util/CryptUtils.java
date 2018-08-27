package com.kollect.etl.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.util.encoders.Hex;

public class CryptUtils {
  
  
  public static String sha256HexHash(final String rawString) {
    MessageDigest digest = null;
    ;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    byte[] hash = digest.digest(rawString.getBytes(StandardCharsets.UTF_8));
    String sha256hex = new String(Hex.encode(hash));

    return sha256hex;
  }

}
