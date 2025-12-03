import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class BruteForce {
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
        commonWords.add("EE");
        commonWords.add("TT");
        commonWords.add("SS");
        commonWords.add("WAS");
        commonWords.add("LL");
    }


    public static void SDESMessage(String ciphertext) throws IOException {
        String cipherBits = new String(Files.readAllBytes(Paths.get(ciphertext)));
        if (cipherBits.length() == 0) {
            throw new IllegalArgumentException("Ciphertext file empty");
        }


        BufferedWriter allWriter = new BufferedWriter(new FileWriter("bruteforce_all.txt"));
        BufferedWriter specialWriter = new BufferedWriter(new FileWriter("bruteforce_special.txt"));

        String outPath = "bruteforce_all.txt";
        String promisingPath = "bruteforce_special.txt";

        int blockSize = cipherBits.length() / 8;
        
        
        for(int i = 0; i < 1024; i++) {
            String rawKey = String.format("%10s", Integer.toBinaryString(i)).replace(' ', '0');
              // decrypt whole message by 8 bit blocks. 
              StringBuilder decryptedBits = new StringBuilder(cipherBits.length());
              for (int j = 0; j + 8 <= cipherBits.length(); j += 8) {
                  String bitBlock = cipherBits.substring(j, j + 8);
                  String plainBlock = SDES.Decrypt(bitBlock, rawKey);
                  decryptedBits.append(plainBlock);
              }

              // convert to CASCII plaintext
              byte[] casciiBits = convBytes(decryptedBits.toString());
              String plaintext;
              try {
                  plaintext = CASCII.toString(casciiBits); 
              } catch (Exception e) {
                  plaintext = "<invalid-cascii>";
              }

              int previewLen = 200;
              String preview = plaintext.length() <= previewLen ? plaintext : plaintext.substring(0, previewLen);

              allWriter.write(rawKey + "\t" + preview + "\n");

              String upPlain = plaintext.toUpperCase();
              boolean matched = false;
              for (String cw : commonWords) {
                if (upPlain.contains(cw)) { 
                    matched = true; 
                    break; 
                }
              }

              if (matched) {
                  specialWriter.write(rawKey + "\t" + plaintext + "\n");
              }
              allWriter.flush();
              specialWriter.flush();
      }

      System.out.println("Wrote bruteforce_all.txt and bruteforce_special.txt");
  }
}
