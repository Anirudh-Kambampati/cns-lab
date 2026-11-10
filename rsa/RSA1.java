/*7. Write a Java program to implement the RSA algorithm.*/
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA1 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Step 1: Input Two Large Prime Numbers
        System.out.print("Enter a Prime number: ");
        BigInteger p = sc.nextBigInteger();
        System.out.print("Enter another prime number: ");
        BigInteger q = sc.nextBigInteger();

        // Check if numbers are prime
        if (!p.isProbablePrime(10) || !q.isProbablePrime(10)) {
            System.out.println("Both numbers must be prime.");
            return;
        }

        // Step 2: Compute n and φ(n)
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Step 3: Choose Public Key 'e'
        BigInteger e = generateE(phi);

        // Step 4: Compute Private Key 'd' (modular inver se of e)
        BigInteger d = e.modInverse(phi);

        // Display Generated Keys
        System.out.println("\nPublic Key (e, n): " + e + ", " + n);
        System.out.println("Private Key (d, n): " + d + ", " + n);

        // Step 5: Encrypt a Message
        System.out.print("\nEnter a message (number) to encrypt: ");
        BigInteger message = sc.nextBigInteger();
        BigInteger encryptedMessage = encrypt(message, e, n);
        System.out.println("Encrypted Message: " + encryptedMessage);

        // Step 6: Decrypt the Message
        BigInteger decryptedMessage = decrypt(encryptedMessage, d, n);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }

    // Generate Public Key 'e'
    private static BigInteger generateE(BigInteger phi) {
        Random rand = new Random();
        BigInteger e;
        do {
            e = new BigInteger(phi.bitLength(), rand).mod(phi);
        } while (e.compareTo(BigInteger.TWO) <= 0 || !e.gcd(phi).equals(BigInteger.ONE));
        return e;
    }

    // Encrypt: C = M^e mod n
    private static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    // Decrypt: M = C^d mod n
    private static BigInteger decrypt(BigInteger ciphertext, BigInteger d, BigInteger n) {
        return ciphertext.modPow(d, n);
    }
}

