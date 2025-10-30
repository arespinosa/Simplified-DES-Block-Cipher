package KeyGeneration;
import java.util.*;
import java.util.HashMap;


public class DES {
    /**
     * Steps for Encryption
     * 1. Using the plaintext, apply IP
     * 2. Using IP and generating k1, apply fk
     * 3. Swap the bits from fk
     * 4. Using swapped bits from step 3 and generating k2, apply fk 
     * 5. Apply InvP
     * 
     */
    public static String Encrypt(String plaintext, String rawkey) {
        KeyGenerations keys = KeyGenerations.Keys(rawkey);

        String ip = Permutations.IP(plaintext);

        String firstFk = Function.Fk(ip, keys.k1);

        String swappedFk = Function.Swap(firstFk);

        String secondFk = Function.Fk(swappedFk, keys.k2);

        String output = Permutations.InvP(secondFk);

        return output;

    }

    /**
     * Steps for Decryption
     * 1. Using the ciphertext, apply IP
     * 2. Using IP and generating k2, apply fk
     * 3. Swap the bits from fk
     * 4. Using swapped bits from step 3 and generating k1, apply fk 
     * 5. Apply InvP
     */
    public static String Decrypt(String ciphertext, String rawkey) {
        KeyGenerations keys = KeyGenerations.Keys(rawkey);

        String ip = Permutations.IP(ciphertext);

        String firstFk = Function.Fk(ip, keys.k2);

        String swappedFk = Function.Swap(firstFk);

        String secondFk = Function.Fk(swappedFk, keys.k1);

        String output = Permutations.InvP(secondFk);

        return output;

    }
}