package Serices;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class EncryptionService {
    // la classe sert à chiffrer et déchiffrer une chaîne de charactères
    private static final String ALGORITHM = "AES";
    // on utilise le masterPassword comme clé principale de chiffrage.
    private String masterPassword;
    public EncryptionService(String masterPassword){
        this.masterPassword = masterPassword;
    }

    public String encrypt(String str){
        // chiffrage de str
        try {
            SecretKeySpec keySpec = generateKey(this.masterPassword);
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] encryptedValue = cipher.doFinal(str.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decrypt(String str){
        // déchiffrage de str
        try {
            SecretKeySpec keySpec = generateKey(this.masterPassword);
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedValue = Base64.getDecoder().decode(str);
            byte[] decryptedValue = cipher.doFinal(decodedValue);

            return new String(decryptedValue);
        } catch (Exception e){
            System.out.println("BAD PASSWORD");
        }
        return "";
    }

    private SecretKeySpec generateKey(String password)
    {
        //on génère une clé de chiffrage à partir de password
        try{
            byte[] key = (password).getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, ALGORITHM);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void setMasterPassword(String pass){
        this.masterPassword = pass;
    }

    public String getMasterPassword(){
        return this.masterPassword;
    }
}
