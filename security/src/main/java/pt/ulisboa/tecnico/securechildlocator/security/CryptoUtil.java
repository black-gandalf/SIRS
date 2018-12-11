package pt.ulisboa.tecnico.securechildlocator.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class CryptoUtil {
    private static byte[] key = new byte[]{'t','e','m','q','u','e','s','e','r','1','2','8','b','y','t','e'};
    private static byte[] keyToCheckServerComs = new byte[]{'k','e','y','s','e','r','v','e','r','m','o','n','i','t','o','r'};
    // TODO add security helper methods
    // keyToUse == 1 -> key ; keyToUse == 2 -> keyToCheckServerComs
    public static String cipherString(String textToCipher, int keyToUse){
        String encriptedMessageString = null;
        try {
            Key key1 = null;
            if (keyToUse == 1){
                key1 = new SecretKeySpec(key, "AES");
            }
            else if (keyToUse == 2){
                key1 = new SecretKeySpec(keyToCheckServerComs, "AES");
            }
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key1);
            byte[] encriptedMessage = cipher.doFinal(textToCipher.getBytes());
             encriptedMessageString = new BASE64Encoder().encode(encriptedMessage);
            System.out.println("encrypted: " + encriptedMessageString);
        }catch(Exception e){
            System.out.printf("Caught exception while ciphering the message: %s%n", e);
        }
        return encriptedMessageString;
    }

    public static String decipherString(String textToDecipher, int keyToUse){
        String decriptedMessageString = null;
        try {
            Key key1 = null;
            if (keyToUse == 1){
                key1 = new SecretKeySpec(key, "AES");
            }
            else if (keyToUse == 2){
                key1 = new SecretKeySpec(keyToCheckServerComs, "AES");
            }
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key1);
            byte[] decodedEncryptedMessage = new BASE64Decoder().decodeBuffer(textToDecipher);
            byte[] decriptedMessage = cipher.doFinal(decodedEncryptedMessage);
            decriptedMessageString = new String(decriptedMessage);
            System.out.println("decryptedMessageString= " + decriptedMessageString);
        }catch(Exception e){
            System.out.printf("Caught exception while deciphering the message: %s%n", e);
        }
        return decriptedMessageString;
    }

    public static String cipherStringUsingMAC(String stringToBeCiphered, int keyToUse){
        String checkString = null;
        try {
            SecretKeySpec signingKey = null;
            if (keyToUse == 1){
                signingKey = new SecretKeySpec(key, "HmacSHA1");
            }
            else if (keyToUse == 2){
                signingKey = new SecretKeySpec(keyToCheckServerComs, "HmacSHA1");
            }
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] cipheredToCheck = mac.doFinal(stringToBeCiphered.getBytes());
            checkString = new BASE64Encoder().encode(cipheredToCheck);
        }catch(Exception e){
            System.out.printf("Caught exception while ciphering the message using MAC: %s%n", e);
        }
        return checkString;
    }

}
