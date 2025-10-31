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
        // Read file and collapse whitespace/newlines to a single bitstring
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
              // decrypt whole message block-by-block
              StringBuilder decryptedBits = new StringBuilder(cipherBits.length());
              for (int pos = 0; pos + 8 <= cipherBits.length(); pos += 8) {
                  String bitBlock = cipherBits.substring(pos, pos + 8);
                  String plainBlock = SDES.Decrypt(bitBlock, rawKey);

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

              allWriter.write(rawKey + "\t" + safePreview + "\n");

              // check uppercase keywords in plaintext
              String upPlain = plaintext.toUpperCase();
              boolean matched = false;
              for (String kw : commonWords) {
                  if (upPlain.contains(kw)) { matched = true; break; }
              }

              if (matched) {
                  String safeFull = plaintext.replace("\t", " ").replace("\r", " ").replace("\n", " ");
                  specialWriter.write(rawKey + "\t" + safeFull + "\n");
              }
              allWriter.flush();
              specialWriter.flush();
      }

      System.out.println("Wrote bruteforce_all.txt and bruteforce_special.txt");
  }
}
