package io.swagger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.swagger.model.Details;

public class Auxiliary {

  public static String computeHash(int id, String timestamp, Details details, String status, String previousHash,
      String secret) throws NoSuchAlgorithmException, IOException {
    byte[] salt = secret.getBytes(StandardCharsets.UTF_8);
    MessageDigest digest;

    digest = MessageDigest.getInstance("SHA-256");
    digest.update(salt);

    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(byteOut);
    out.writeObject(details);
    out.flush();

    digest.update(byteOut.toByteArray());

    digest.update(ByteBuffer.allocate(4).putInt(id).array());

    digest.update(timestamp.getBytes());

    digest.update(status.getBytes());

    digest.update(previousHash.getBytes());

    byte[] encodedhash = digest.digest();
    return Auxiliary.bytesToHex(encodedhash);
  }

  public static String bytesToHex(byte[] hash) {
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if(hex.length() == 1) hexString.append('0');
          hexString.append(hex);
      }
      return hexString.toString();
  }
  
  public static int difOffsetDateTime(org.threeten.bp.OffsetDateTime start, org.threeten.bp.OffsetDateTime end){
    return start.compareTo(end);
  }
}