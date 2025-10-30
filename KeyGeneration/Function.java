package KeyGeneration;
import java.util.*;
import java.util.HashMap;


public class Function {
    // Creating the substitution boxes 
    static String[][] s0 = {
        {"01", "00", "11", "10"},
        {"11", "10", "01", "00"},
        {"00", "10", "01", "11"},
        {"11", "01", "11", "10"}
    };

    static String[][] s1 = {
        {"00", "01", "10", "11"},
        {"10", "00", "01", "11"},
        {"11", "00", "01", "00"},
        {"10", "01", "00", "11"}
    };

    static HashMap<String, Integer> bits = new HashMap<String, Integer>();
    static {
        bits.put("00", 0);
        bits.put("01", 1);
        bits.put("10", 2);
        bits.put("11", 3);
    }

    public static char XOR(char bit1, char bit2) {
        if (bit1 == '0' && bit2 == '0') {
            return '0';
        } else if (bit1 == '1' && bit2 == '0') {
            return '1';
        } else if (bit1 == '0' && bit2 == '1') {
            return '1';
        } else { // bit1 == '1' && bit2 == '1'
            return '0';
        }
    }



    /**
     * Steps for Fk
     * Step 1: Split the input into 4 bits each
     * Step 2: With the right 4 bits 
     *  a. Expand and Permutate those rightmost 4 bits
     *  b. XOR that w/ the Key
     *  c  Split those 8 bits and use Substitution to get 4 bits
     *  d. Then apply p4 
     * Step 3: Apply XOR to leftmost bits and step 2 
     * Step 4: Concatenate step 3 w/ rightmost 4
     */

    public static String Fk(String input, String key) {
        String left4bits = input.substring(0,4);
        String right4bits = input.substring(4,8);

        String ep = Permutations.EP(right4bits);
        String product = "";

        for(int i = 0; i < ep.length(); i++){
            char temp1 = ep.charAt(i);
            char temp2 = key.charAt(i);
            char result = XOR(temp1, temp2);

            String currBit = "" + result;
            product += currBit;
        }

        String productLeft = product.substring(0,4);
        String productRight = product.substring(4,8);

        String leftRow = "" + productLeft.charAt(0) + productLeft.charAt(3);
        String leftCol = "" + productLeft.charAt(1) + productLeft.charAt(2);

        String rightRow = "" + productRight.charAt(0) + productRight.charAt(3);
        String rightCol = "" + productRight.charAt(1) + productRight.charAt(2);

        int lR = bits.get(leftRow);
        int lC = bits.get(leftCol);

        int rR = bits.get(rightRow);
        int rC = bits.get(rightCol);

        String firstTwo = s0[lR][lC];
        String rightTwo = s1[rR][rC];
        String finalFour = firstTwo + rightTwo;

        // Applying P4 onto s0s1
        String permutated4 = Permutations.P4(finalFour);

        product = "";

        for(int i = 0; i < permutated4.length(); i++){
            char temp1 = permutated4.charAt(i);
            char temp2 = left4bits.charAt(i);
            char result = XOR(temp1, temp2);

            String currBit = "" + result;
            product += currBit;
        }

        
        String output = product + right4bits;

        return output;

    }

    public static String Swap(String input){
        int half = input.length() / 2;
        String firstHalf = input.substring(0, half);
        String secondHalf = input.substring(half, input.length());

        return secondHalf + firstHalf;

    }


}