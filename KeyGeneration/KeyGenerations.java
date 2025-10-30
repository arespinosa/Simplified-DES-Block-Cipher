package KeyGeneration; 
import java.util.*;

public class KeyGenerations {
    public String k1;
    public String k2;

    public KeyGenerations(String k1, String k2) {
        this.k1 = k1;
        this.k2 = k2;
    }
    /**
     * Steps to generate K1
     *  1. With 10 bit input, apply P10
     *  2. Split Bits in half and apply Left Shift One
     *  3. Apply P8 and return output 
     * 
     * Steps to generate K2
     *  1. With bits that were shifted by one, reuse them to shift by 2
     *  2. Then apply p8
     */
    public static KeyGenerations Keys(String input) {
        String p10 = Permutations.P10(input);
        // s0 will be the first half of p10 and s1 will be the second half of p10
        String s0 = p10.substring(0,5);
        String s1 = p10.substring(5,10);

        String ls_s0 = Permutations.LeftShiftOne(s0);
        String ls_s1 = Permutations.LeftShiftOne(s1);

        String p8 = ls_s0 + ls_s1;
        String k1 = Permutations.P8(p8);
        
        // Now taking steps for s2
        String ls_p1 = Permutations.LeftShiftTwo(ls_s0);
        String ls_p2 = Permutations.LeftShiftTwo(ls_s1);

        p8 = ls_p1 + ls_p2;
        String k2 = Permutations.P8(p8);

        KeyGenerations keys = new KeyGenerations(k1, k2);
        return keys;
    }
}


