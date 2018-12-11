package pt.ulisboa.tecnico.securechildlocator.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class CryptoUtil {
    private static byte[] key = new byte[]{'t','e','m','q','u','e','s','e','r','1','2','8','b','y','t','e'};
    // TODO add security helper methods
    public static String cipherString(String textToCipher){
        String encriptedMessageString = null;
        try {
            Key key1 = new SecretKeySpec(key, "AES");
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

    public static String decipherString(String textToDecipher){
        String decriptedMessageString = null;
        try {
            Key key1 = new SecretKeySpec(key, "AES");
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

    public static String cipherStringUsingMAC(String stringToBeCiphered){
        String checkString = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
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
