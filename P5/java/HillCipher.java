import java.util.Scanner;

public class HillCipher {

    private static final int N = 3;

    public static int[][] multiplyMatrix(int[][] key, int[][] vector) {
        int[][] result = new int[N][1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result[i][0] += key[i][j] * vector[j][0];
            }
            result[i][0] %= 26;
        }
        return result;
    }

    public static int determinant(int[][] matrix) {
        int det = 0;
        det = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
            - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
            + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
        return ((det % 26) + 26) % 26;
    }

    public static int modInverse(int a, int m) {
        a = ((a % m) + m) % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) return x;
        }
        return 1;
    }

    public static String encrypt(String plaintext, int[][] key) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        while (plaintext.length() % N != 0) plaintext += "X";

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += N) {
            int[][] vector = new int[N][1];
            for (int j = 0; j < N; j++) {
                vector[j][0] = plaintext.charAt(i + j) - 'A';
            }
            int[][] encrypted = multiplyMatrix(key, vector);
            for (int j = 0; j < N; j++) {
                result.append((char) (encrypted[j][0] + 'A'));
            }
        }
        return result.toString();
    }

    public static String decrypt(String ciphertext, int[][] key) {
        int det = determinant(key);
        int detInv = modInverse(det, 26);

        int[][] adjoint = new int[N][N];
        adjoint[0][0] = ((key[1][1] * key[2][2] - key[1][2] * key[2][1]) % 26 + 26) % 26;
        adjoint[0][1] = ((key[0][2] * key[2][1] - key[0][1] * key[2][2]) % 26 + 26) % 26;
        adjoint[0][2] = ((key[0][1] * key[1][2] - key[0][2] * key[1][1]) % 26 + 26) % 26;
        adjoint[1][0] = ((key[1][2] * key[2][0] - key[1][0] * key[2][2]) % 26 + 26) % 26;
        adjoint[1][1] = ((key[0][0] * key[2][2] - key[0][2] * key[2][0]) % 26 + 26) % 26;
        adjoint[1][2] = ((key[0][2] * key[1][0] - key[0][0] * key[1][2]) % 26 + 26) % 26;
        adjoint[2][0] = ((key[1][0] * key[2][1] - key[1][1] * key[2][0]) % 26 + 26) % 26;
        adjoint[2][1] = ((key[0][1] * key[2][0] - key[0][0] * key[2][1]) % 26 + 26) % 26;
        adjoint[2][2] = ((key[0][0] * key[1][1] - key[0][1] * key[1][0]) % 26 + 26) % 26;

        int[][] inverseKey = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                inverseKey[i][j] = (detInv * adjoint[i][j]) % 26;

        return encrypt(ciphertext, inverseKey);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Hill Cipher (3x3) ===");
        System.out.print("Enter plaintext: ");
        String text = sc.nextLine();

        int[][] key = {
            {6, 24, 1},
            {13, 16, 10},
            {20, 17, 15}
        };

        System.out.println("\nKey Matrix:");
        for (int[] row : key) {
            for (int val : row) System.out.printf("%3d", val);
            System.out.println();
        }

        String encrypted = encrypt(text, key);
        String decrypted = decrypt(encrypted, key);

        System.out.println("\nPlaintext  : " + text);
        System.out.println("Encrypted  : " + encrypted);
        System.out.println("Decrypted  : " + decrypted);
        sc.close();
    }
}
