import KeyGeneration.DES;
import KeyGeneration.TDES;
import KeyGeneration.CASCII_Encoding;
import KeyGeneration.BruteForce;
import KeyGeneration.TDES_BruteForce;

public class Main {
    public static void main(String[] args) {
        /* PART 1: SDES */ 

        //First we will verify that it matches answers we were given 
        String[][] verificationCases = {
            {"0000000000", "10101010", "00010001"},
            {"1110001110", "10101010", "11001010"},
            {"1110001110", "01010101", "01110000"},
            {"1111111111", "10101010", "00000100"}
        };
        System.out.println("------------------------------------------------------------------------");
        System.out.println("PART 1. SDES");
        System.out.println("\nVerification Cases:");
        System.out.println("\nRaw Key\t\tPlaintext\tExpected Ciphertext\tActual Ciphertext\tMatch?");
        for (String[] test : verificationCases) {
            String rawKey = test[0];
            String plaintext = test[1];
            String expectedCiphertext = test[2];
            String actualCiphertext = DES.Encrypt(plaintext, rawKey);
            boolean match = actualCiphertext.equals(expectedCiphertext);
            System.out.println(rawKey + "\t" + plaintext + "\t" + expectedCiphertext + "\t\t" 
                               + actualCiphertext + "\t\t" + (match));
        }

        // Now we will move onto the next section which is to get the ciphertext and plaintext 
        String[][] encryptionCases = {
            {"0000000000", "00000000"},
            {"1111111111", "11111111"},
            {"0000011111", "00000000"},
            {"0000011111", "11111111"}
        };
        System.out.println("");
        System.out.println("Encryption Cases:");
        System.out.println("\nRaw Key\t\tPlaintext\tCiphertext");
        for (String[] test : encryptionCases) {
            String rawKey = test[0];
            String plaintext = test[1];
            String ciphertext = DES.Encrypt(plaintext, rawKey);
            System.out.println(rawKey + "\t" + plaintext + "\t" + ciphertext);
        }

        // Part 2: Decrypt given ciphertexts with given keys
        String[][] decryptionCases = {
            {"1000101110", "00011100"},
            {"1000101110", "11000010"},
            {"0010011111", "10011101"},
            {"0010011111", "10010000"}
        };
        System.out.println("\nDecryption Cases:");
        System.out.println("\nRaw Key\t\tCiphertext\tPlaintext");
        for (String[] test : decryptionCases) {
            String rawKey = test[0];
            String ciphertext = test[1];
            String plaintext = DES.Decrypt(ciphertext, rawKey);
            System.out.println(rawKey + "\t" + ciphertext + "\t" + plaintext);
        }

        /* PART 2: TRIPLE SDES */
        String[][] tencryptionCases = {
            {"0000000000", "0000000000", "00000000"},
            {"1000101110", "0110101110", "11010111"},
            {"1000101110", "0110101110", "10101010"},
            {"1111111111", "1111111111", "10101010"}
        };
        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nPART 2: Triple SDES");
        System.out.println("\n Encryption Cases:");
        System.out.println("\nKey1\t\tKey2\t\tPlaintext\tCiphertext");
        for (String[] test : tencryptionCases) {
            String rk1 = test[0];
            String rk2 = test[1];
            String pt = test[2];
            String ct = TDES.Encrypt(pt, rk1, rk2);
            System.out.println(rk1 + "\t" + rk2 + "\t" + pt + "\t\t" + ct);
        }

        // Part 2: Decrypt given ciphertexts with given key pairs
        String[][] tdecryptionCases = {
            {"1000101110", "0110101110", "11100110"},
            {"1011101111", "0110101110", "01010000"},
            {"0000000000", "0000000000", "10000000"},
            {"1111111111", "1111111111", "10010010"}
        };

        System.out.println("\nTripleSDES Decryption Cases:");
        System.out.println("\nKey1\t\tKey2\t\tCiphertext\tPlaintext");
        for (String[] test : tdecryptionCases) {
            String rk1 = test[0];
            String rk2 = test[1];
            String ct = test[2];
            String pt = TDES.Decrypt(ct, rk1, rk2);
            System.out.println(rk1 + "\t" + rk2 + "\t" + ct + "\t\t" + pt);
        }
        
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("PART 3. Cracking SDES and TripleSDES ");
        System.out.println("1. Using CRYPTOGRAPHY and Key: 0111001101");
        System.out.println("Encoding Result: ");

        String crypto_key = "0111001101";
        String crypto_text = "CRYPTOGRAPHY";
    
        String crypto_encode = CASCII_Encoding.CASCII_Encode(crypto_text, crypto_key);

        System.out.println(crypto_encode);
        System.out.println("Length of encryption: " + crypto_encode.length() + " bits.");


        System.out.println("2. Decrypt msg1.txt, and finding the 10-bit raw key used for its encryption");
        String cipherFile = "msg1.txt";
        if (args.length > 0) cipherFile = args[0];

        System.out.println("Brute-forcing SDES on file: " + cipherFile);
        long t0 = System.currentTimeMillis();
        try {
            BruteForce.SDESMessage(cipherFile);
            long t1 = System.currentTimeMillis();
            System.out.println("Finished. Time: " + (t1 - t0) + " ms");
        } catch (Exception e) {
            System.err.println("Error running brute force: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Raw Key used: 1011110100");
        System.out.println("Message: WHOEVER THINKS HIS PROBLEM CAN BE SOLVED USING CRYPTOGRAPHY, DOESN'T UNDERSTAND HIS PROBLEM AND DOESN'T UNDERSTAND CRYPTOGRAPHY.  ATTRIBUTED BY ROGER NEEDHAM AND BUTLER LAMPSON TO EACH OTHER");


        System.out.println("3. Decrypt msg2.txt, and finding the 2 10-bit raw key used for its encryption");
        cipherFile = "msg2.txt";
        if (args.length > 0) cipherFile = args[0];

        System.out.println("Brute-forcing Triple SDES on file: " + cipherFile);
        long t2 = System.currentTimeMillis();
        try {
            TDES_BruteForce.TSDESMessage(cipherFile);
            long t4 = System.currentTimeMillis();
            System.out.println("Finished. Time: " + (t4 - t2) + " ms");
        } catch (Exception e) {
            System.err.println("Error running brute force: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("RawKey1: 1110000101 and RawKey2: 0101100011");
        System.out.println("THERE ARE NO SECRETS BETTER KEPT THAN THE SECRETS THAT EVERYBODY GUESSES.");
    }
}
