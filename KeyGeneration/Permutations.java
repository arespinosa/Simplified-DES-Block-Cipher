package KeyGeneration;
import java.util.*;

// This file will hold the permutations needed for SDES 
public class Permutations {

    /**
     * P10(Permutation)
     * Input: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
     * Output: 3, 5, 2, 7, 4, 10, 1, 9, 8, 6
     */
    public static String P10(String input) {
        char[] output = new char[input.length()];
        output[0] = input.charAt(2);
        output[1] = input.charAt(4);
        output[2] = input.charAt(1);
        output[3] = input.charAt(6);
        output[4] = input.charAt(3);
        output[5] = input.charAt(9);
        output[6] = input.charAt(0);
        output[7] = input.charAt(8);
        output[8] = input.charAt(7);
        output[9] = input.charAt(5);

        String result = new String(output);
        return result;
    }

    /**
     * P8 (select & permutate)
     * Input: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
     * Output: 6, 3, 7, 4, 8, 5, 10, 9
     */

    public static String P8(String input){
        char[] output = new char[8];
        output[0] = input.charAt(5);
        output[1] = input.charAt(2);
        output[2] = input.charAt(6);
        output[3] = input.charAt(3);
        output[4] = input.charAt(7);
        output[5] = input.charAt(4);
        output[6] = input.charAt(9);
        output[7] = input.charAt(8);

        String result = new String(output);
        return result;
    }

    /**
     * P4 (permutate)
     * Input: 1, 2, 3, 4
     * Output: 2, 4, 3, 1
     */
    public static String P4(String input) {
        char[] output = new char[4];

        output[0] = input.charAt(1);
        output[1] = input.charAt(3);
        output[2] = input.charAt(2);
        output[3] = input.charAt(0);

        String result = new String(output);
        return result;

    }

    /**
     * Grabbing a set of bits and shifting to the left by one
     */
    public static String LeftShiftOne(String input) { 
        char[] shifted = new char[5];
        shifted[0] = input.charAt(1);
        shifted[1] = input.charAt(2);
        shifted[2] = input.charAt(3);
        shifted[3] = input.charAt(4);
        shifted[4] = input.charAt(0);
        return new String(shifted);
    }
    /**
     * Grabbing a set of bits and shifting to the left by two  
     */
    public static String LeftShiftTwo(String input) {
        char[] shifted = new char[5];
        shifted[0] = input.charAt(2);
        shifted[1] = input.charAt(3);
        shifted[2] = input.charAt(4);
        shifted[3] = input.charAt(0);
        shifted[4] = input.charAt(1);
        return new String(shifted);
    }

    /**
     * EP: Expand and Permutate 
     * Input: 1 2 3 4 
     * Output: 4 1 2 3 2 3 4 1
     */
    public static String EP(String input){
        char[] output = new char[8];
        output[0] = input.charAt(3);
        output[1] = input.charAt(0);
        output[2] = input.charAt(1);
        output[3] = input.charAt(2);
        output[4] = input.charAt(1);
        output[5] = input.charAt(2);
        output[6] = input.charAt(3);
        output[7] = input.charAt(0);

        String result = new String(output);
        return result;
    }

    /**
     * IP: Initial Permutation 
     * Input: 1, 2, 3, 4, 5, 6, 7, 8
     * Output: 2, 6, 3, 1 , 4, 8, 5, 7 
     */
    public static String IP(String input) {
        char[] output = new char[8];
        output[0] = input.charAt(1);
        output[1] = input.charAt(5);
        output[2] = input.charAt(2);
        output[3] = input.charAt(0);
        output[4] = input.charAt(3);
        output[5] = input.charAt(7);
        output[6] = input.charAt(4);
        output[7] = input.charAt(6);

        String result = new String(output);
        return result;
    }

    public static String InvP(String input){
        char[] output = new char[8];
        output[0] = input.charAt(3);
        output[1] = input.charAt(0);
        output[2] = input.charAt(2);
        output[3] = input.charAt(4);
        output[4] = input.charAt(6);
        output[5] = input.charAt(1);
        output[6] = input.charAt(7);
        output[7] = input.charAt(5);

        String result = new String(output);
        return result;

    }

}