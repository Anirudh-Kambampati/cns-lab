/* 12. Write a Java program to implement the RSA algorithm. */
import java.math.BigInteger;
import java.util.Scanner;

class RSASimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BigInteger one = BigInteger.ONE;

        // Step 1: Input two prime numbers p and q
        System.out.print("Enter prime p: ");
        BigInteger p = sc.nextBigInteger();
        System.out.print("Enter prime q: ");
        BigInteger q = sc.nextBigInteger();

        // Check that both are prime and different
        if (!p.isProbablePrime(20) || !q.isProbablePrime(20) || p.equals(q)) {
            System.out.println("p and q must be two different primes.");
            return;
        }

        // Step 2: Compute n = p*q and phi(n) = (p-1)*(q-1)
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(one).multiply(q.subtract(one));

        // Step 3: Choose public key e = smallest odd number with gcd(e, phi) = 1
        BigInteger e = BigInteger.valueOf(3);
        while (!e.gcd(phi).equals(one)) e = e.add(BigInteger.valueOf(2));

        // Step 4: Compute private key d = e^-1 mod phi  (so e*d mod phi = 1)
        BigInteger d = e.modInverse(phi);

        // Display the keys
        System.out.println("Public key  (e, n) = (" + e + ", " + n + ")");
        System.out.println("Private key (d, n) = (" + d + ", " + n + ")");

        // Step 5: Input the message (must be a number smaller than n)
        System.out.print("Enter message (number from 0 to " + n.subtract(one) + "): ");
        BigInteger m = sc.nextBigInteger();
        if (m.signum() < 0 || m.compareTo(n) >= 0) {
            System.out.println("Message must be between 0 and n-1.");
            return;
        }

        // Step 6: Encrypt  C = M^e mod n
        BigInteger c = m.modPow(e, n);
        System.out.println("Encrypted: " + c);

        // Step 7: Decrypt  M = C^d mod n
        System.out.println("Decrypted: " + c.modPow(d, n));
    }
}

/*
ALGORITHM:
1. Start.
2. Read two different prime numbers p and q.
3. n = p * q.
4. phi(n) = (p - 1) * (q - 1).
5. Choose e such that 1 < e and gcd(e, phi) = 1 (start at 3, add 2 until found).
6. Compute d such that (d * e) mod phi = 1   (d = e^-1 mod phi).
7. Public key = (e, n), Private key = (d, n).
8. Read message M where 0 <= M < n.
9. Encryption: C = M^e mod n.
10. Decryption: M = C^d mod n.
11. Display C and the decrypted M.
12. Stop.

Example: p = 61, q = 53 -> n = 3233, phi = 3120, e = 7, d = 1783
         M = 65 -> C = 65^7 mod 3233 = 1317 -> 1317^1783 mod 3233 = 65

WHY IT WORKS FOR (ALMOST) ALL INPUTS:
- BigInteger has no size limit, so p and q can be huge (hundreds of digits)
  without overflow; modPow computes powers mod n efficiently.
- phi is even for odd primes, so trying odd e = 3, 5, 7 ... quickly finds
  one with gcd = 1, and gcd = 1 guarantees the inverse d exists.
  (e could also be picked randomly; starting from 3 gives a valid e that is
  predictable and easy to check by hand.)
- RSA only recovers M correctly if M < n, so the program checks 0 <= M < n.
- Rejected inputs: non-primes, p = q, and M outside 0..n-1.
- Typing letters instead of numbers stops the program (InputMismatchException).
- isProbablePrime(20) is wrong less than 1 time in a trillion.
*/
