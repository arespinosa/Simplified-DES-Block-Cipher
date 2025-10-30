package KeyGeneration;
import java.util.*;

public class CASCII_Encoding{
    public static String CASCII_Encode(String input, String key){
        byte[] bits = CASCII.Convert(input);

        // Now going to convert it into a string 
        StringBuilder sb = new StringBuilder();
        for (byte b : bits) {
            sb.append(b);
        }

        String bitString = sb.toString();

        // Creating the amount of blocks we will have to encrypt 
        int blockSize = bitString.length() / 8;
        // Starting from 0 
        int j = 0;
        // Encrypted result we will return 
        String result = "";
        // Iterating over the amount of blocks and encrypting 8 bits at a time 
        for(int i = 0; i < blockSize; i++) {
            String bitBlock = bitString.substring(j, j+8);
            String cipherBlock = DES.Encrypt(bitBlock, key);
            result += cipherBlock;
            j += 8;
        }

        return result;
    }
}