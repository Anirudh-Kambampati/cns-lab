import java.util.Scanner;

public class DES {

    static final int[] IP = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };

    static final int[] FP = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };

    static final int[] E = {
        32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
    };

    static final int[][] SBOX = {
        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,
         0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,
         4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,
         15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13},
        {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,
         3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
         0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,
         13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
    };

    static final int[] P = {
        16, 7, 20, 21, 29, 12, 28, 17,
        1, 15, 23, 26, 5, 18, 31, 10,
        2, 8, 24, 14, 32, 27, 3, 9,
        19, 13, 30, 6, 22, 11, 4, 25
    };

    static final int[] PC1 = {
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4
    };

    static final int[] PC2 = {
        14, 17, 11, 24, 1, 5,
        3, 28, 15, 6, 21, 10,
        23, 19, 12, 4, 26, 8,
        16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
    };

    static final int[] SHIFTS = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    static boolean[] permute(boolean[] input, int[] table, int n) {
        boolean[] output = new boolean[table.length];
        for (int i = 0; i < table.length; i++)
            output[i] = input[table[i] - 1];
        return output;
    }

    static boolean[] xor(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[a.length];
        for (int i = 0; i < a.length; i++)
            result[i] = a[i] ^ b[i];
        return result;
    }

    static boolean[] intToBitArray(long val, int bits) {
        boolean[] result = new boolean[bits];
        for (int i = bits - 1; i >= 0; i--) {
            result[i] = (val & 1) == 1;
            val >>= 1;
        }
        return result;
    }

    static long bitArrayToLong(boolean[] bits) {
        long result = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) result |= (1L << (bits.length - 1 - i));
        }
        return result;
    }

    static String longToHex(long val) {
        return String.format("%016X", val);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== DES Algorithm (Simplified) ===");
        System.out.print("Enter 64-bit plaintext (hex, 16 chars): ");
        String plaintext = sc.nextLine().toUpperCase();
        System.out.print("Enter 64-bit key (hex, 16 chars): ");
        String key = sc.nextLine().toUpperCase();

        long ptLong = Long.parseLong(plaintext, 16);
        long keyLong = Long.parseLong(key, 16);

        boolean[] ptBits = intToBitArray(ptLong, 64);
        boolean[] keyBits = intToBitArray(keyLong, 64);

        boolean[] ipBits = permute(ptBits, IP, 64);
        long leftHalf = bitArrayToLong(java.util.Arrays.copyOfRange(ipBits, 0, 32));
        long rightHalf = bitArrayToLong(java.util.Arrays.copyOfRange(ipBits, 32, 64));

        boolean[] subkey = permute(keyBits, PC1, 56);
        boolean[] leftKey = java.util.Arrays.copyOfRange(subkey, 0, 28);
        boolean[] rightKey = java.util.Arrays.copyOfRange(subkey, 28, 56);

        System.out.println("\n=== DES Round Details ===");
        System.out.printf("Plaintext : %s%n", longToHex(ptLong));
        System.out.printf("Key       : %s%n", longToHex(keyLong));
        System.out.printf("After IP  : L0=%08X R0=%08X%n", leftHalf, rightHalf);

        for (int round = 0; round < 16; round++) {
            long prevRight = rightHalf;

            boolean[] expanded = permute(intToBitArray(rightHalf, 32), E, 48);

            boolean[] roundKey = new boolean[48];
            boolean[] combinedKey = new boolean[56];
            System.arraycopy(leftKey, 0, combinedKey, 0, 28);
            System.arraycopy(rightKey, 0, combinedKey, 28, 28);

            combinedKey = permute(combinedKey, PC2, 48);
            roundKey = combinedKey;

            boolean[] xored = xor(expanded, roundKey);

            boolean[] sboxOut = new boolean[32];
            for (int i = 0; i < 8; i++) {
                int[] block = new int[6];
                for (int j = 0; j < 6; j++) block[j] = xored[i * 6 + j] ? 1 : 0;
                int row = block[0] * 2 + block[5];
                int col = block[1] * 8 + block[2] * 4 + block[3] * 2 + block[4];
                int val = SBOX[i % 2][row * 16 + col];
                boolean[] valBits = intToBitArray(val, 4);
                System.arraycopy(valBits, 0, sboxOut, i * 4, 4);
            }

            boolean[] fResult = permute(sboxOut, P, 32);
            boolean[] leftBits = intToBitArray(leftHalf, 32);
            boolean[] newRight = xor(leftBits, fResult);

            leftHalf = prevRight;
            rightHalf = bitArrayToLong(newRight);

            System.out.printf("Round %2d  : L=%08X R=%08X%n", round + 1, leftHalf, rightHalf);

            boolean[] tempLeft = java.util.Arrays.copyOfRange(combinedKey, 0, 28);
            boolean[] tempRight = java.util.Arrays.copyOfRange(combinedKey, 28, 56);
            int shift = SHIFTS[round];
            boolean[] newLeft = new boolean[28];
            boolean[] newRightKey = new boolean[28];
            System.arraycopy(tempLeft, shift, newLeft, 0, 28 - shift);
            System.arraycopy(tempLeft, 0, newLeft, 28 - shift, shift);
            System.arraycopy(tempRight, shift, newRightKey, 0, 28 - shift);
            System.arraycopy(tempRight, 0, newRightKey, 28 - shift, shift);
            leftKey = newLeft;
            rightKey = newRightKey;
        }

        boolean[] finalBlock = new boolean[64];
        System.arraycopy(intToBitArray(rightHalf, 32), 0, finalBlock, 0, 32);
        System.arraycopy(intToBitArray(leftHalf, 32), 0, finalBlock, 32, 32);
        boolean[] ciphertext = permute(finalBlock, FP, 64);
        long ctLong = bitArrayToLong(ciphertext);

        System.out.printf("%nCiphertext: %s%n", longToHex(ctLong));
        sc.close();
    }
}
