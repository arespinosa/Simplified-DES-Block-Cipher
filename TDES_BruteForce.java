import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class TDES_BruteForce {
    private static byte[] convBytes(String bits) {
        byte[] out = new byte[bits.length()];
        for (int i = 0; i < bits.length(); i++) {
            out[i] = (byte) (bits.charAt(i) == '1' ? 1 : 0);
        }
        return out;
    }

    static final HashSet<String> commonWords = new HashSet<>();
    static {
        commonWords.add("THAT");
        commonWords.add("AND");
        commonWords.add("NOT");
        commonWords.add("FOR");
        commonWords.add("THE");
        commonWords.add("ALL");
        commonWords.add("CRYPTO");
        commonWords.add("OF");
        commonWords.add("TO");
        commonWords.add("IN");
        commonWords.add("IS");
        commonWords.add("BE");
        commonWords.add("OR");
        commonWords.add("WAS");
        commonWords.add("FROM");
        commonWords.add("ABOUT");
        commonWords.add("WAY");
        commonWords.add("BY");

    }


    public static void TSDESMessage(String ciphertext) throws IOException {
        String cipherBits = new String(Files.readAllBytes(Paths.get(ciphertext)));
        if (cipherBits.length() == 0) {
            throw new IllegalArgumentException("Ciphertext file empty");
        }


        BufferedWriter allWriter = new BufferedWriter(new FileWriter("bruteforce_TDES_all.txt"));
        BufferedWriter specialWriter = new BufferedWriter(new FileWriter("bruteforce_special_TDES.txt"));

        String outPath = "bruteforce_TDES_all.txt";
        String promisingPath = "bruteforce_special_TDES.txt";

        int blockSize = cipherBits.length() / 8;
        
        // We have to iterate 2^10 * 2^10 times since every key must be tried with the other key
        for(int i = 0; i < 1024; i++) {
            String rawKey1 = String.format("%10s", Integer.toBinaryString(i)).replace(' ', '0');
            for(int j = 0; j < 1024; j++){
                String rawKey2 = String.format("%10s", Integer.toBinaryString(j)).replace(' ', '0');
                // decrypt message by 8 bit bloks 
                StringBuilder decryptedBits = new StringBuilder(cipherBits.length());
                for (int k = 0; k + 8 <= cipherBits.length(); k += 8) {
                    String bitBlock = cipherBits.substring(k, k + 8);
                    String plainBlock = TripleDES.Decrypt(bitBlock, rawKey1, rawKey2);
                    decryptedBits.append(plainBlock);
                }
                
                // convert to CASCII plaintext
                byte[] casciiBits = convBytes(decryptedBits.toString());
                String plaintext;
                int validLength = casciiBits.length - (casciiBits.length % 5);
                byte[] trimmedBits = Arrays.copyOf(casciiBits, validLength);
                try {
                  plaintext = CASCII.toString(trimmedBits); 
                } catch (Exception e) {
                  plaintext = "<invalid-cascii>";
                }
                
                int previewLen = 200;
                String preview = plaintext.length() <= previewLen ? plaintext : plaintext.substring(0, previewLen);
                String safePreview = preview.replace("\t", " ").replace("\r", " ").replace("\n", " ");
                allWriter.write("rawKey: 1 " +  rawKey1 + "\t" + "rawKey: 2 " + rawKey2 + "\t" + "Decrypted Message" + safePreview + "\n");

                String upPlain = plaintext.toUpperCase();
                boolean matched = false;
                for (String cw : commonWords) {
                    if (upPlain.contains(cw)) { 
                        matched = true; 
                        break; 
                    }
                }
                
                if (matched) {
                  String safeFull = plaintext.replace("\t", " ").replace("\r", " ").replace("\n", " ");
                  specialWriter.write("RawKey1: " + rawKey1 + "\tRawKey2: " + rawKey2 + "\t" + safeFull + System.lineSeparator());
                }
            }
            allWriter.flush();
            specialWriter.flush();
      }

      System.out.println("Wrote into bruteforce_TDES_all.txt & bruteforce_TDES_special.txt");
  }
}