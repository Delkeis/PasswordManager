package PasswordManager;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class EncryptionController {
    private static final String ALGORITHM = "AES";
    private final String masterPassword;
    public EncryptionController(String masterPassword){
        this.masterPassword = masterPassword;
    }

    public String encrypt(String str){
        try {
            SecretKeySpec keyspec = generateKey(this.masterPassword);
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(cipher.ENCRYPT_MODE, keyspec);

            byte[] encryptedValue = cipher.doFinal(str.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decrypt(String str){
        try {
            SecretKeySpec keySpec = generateKey(this.masterPassword);
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(cipher.DECRYPT_MODE, keySpec);
            byte[] decodedValue = Base64.getDecoder().decode(str);
            byte[] decryptedValue = cipher.doFinal(decodedValue);

            return new String(decryptedValue);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private SecretKeySpec generateKey(String password)
    {
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
}
