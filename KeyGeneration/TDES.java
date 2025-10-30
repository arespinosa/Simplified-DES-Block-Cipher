package KeyGeneration;
import java.util.*;

public class TDES {

    public static String Encrypt(String plaintext, String key1, String key2) {
        // Step 1: Encrypt with key1
        String step1 = DES.Encrypt(plaintext, key1);
        
        // Step 2: Decrypt with key2
        String step2 = DES.Decrypt(step1, key2);
        
        // Step 3: Encrypt with key1
        String ciphertext = DES.Encrypt(step2, key1);
        
        return ciphertext;
    }

    public static String Decrypt(String ciphertext, String key1, String key2) {
        // Step 1: Decrypt with key1
        String step1 = DES.Decrypt(ciphertext, key1);
        
        // Step 2: Encrypt with key2
        String step2 = DES.Encrypt(step1, key2);
        
        // Step 3: Decrypt with key1
        String plaintext = DES.Decrypt(step2, key1);
        
        return plaintext;
    }
}