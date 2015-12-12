package model;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Julie on 10.12.2015.
 */
public class AES {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    private static String decryptedString;
    private static String encryptedString;

    private static final String[] chars = { "qwertyuiopasdfghjklzxcvbnm",
            "0123456789",
            "QWERTYUIOPASDFGHJKLZXCVBNM",
            "!@#$%^&*()_+-=?<>{}[]|\\/"};

    public static String encryptionError = "Ошибка шифровки :(";
    public static String decryptionError = "Ошибка расшифровки :(";

    public static void setKey(String myKey) {

        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");

            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit

            secretKey = new SecretKeySpec(key, "AES");

        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }

    }

    public static String getDecryptedString() {
        return decryptedString;
    }

    public static void setDecryptedString(String decryptedString) {
        AES.decryptedString = decryptedString;
    }

    public static String getEncryptedString() {
        return encryptedString;
    }

    public static void setEncryptedString(String encryptedString) {
        AES.encryptedString = encryptedString;
    }

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            setEncryptedString(Base64.encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")), Base64.DEFAULT));

        } catch (Exception e) {
            setEncryptedString(encryptionError/*e.getMessage().toString()*/);
        }
        return null;

    }

    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            setDecryptedString(new String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT))));

        } catch (Exception e) {
            setDecryptedString(decryptionError);
        }
        return null;
    }

    public static String generatePassword(int length, int strength) {
        if (strength > 4) {
            strength = 4;
        }
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        int i, j;
        for (int k = 0; k < strength; ++k) {
            sb.append(chars[k].charAt(r.nextInt(chars[k].length())));
        }
        for (int k = strength; k < length; ++k) {
            i = r.nextInt(strength);
            j = r.nextInt(chars[i].length());
            sb.append(chars[i].charAt(j));
        }
        return sb.toString();
    }
}
