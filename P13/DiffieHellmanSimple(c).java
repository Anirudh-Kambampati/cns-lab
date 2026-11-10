/* 13. Implement the Diffie-Hellman Key Exchange mechanism. */
import java.math.BigInteger;
import java.util.Scanner;

class DiffieHellmanSimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Both persons agree on public values: prime p and primitive root g
        System.out.print("Enter prime p: ");
        BigInteger p = sc.nextBigInteger();
        System.out.print("Enter primitive root g: ");
        BigInteger g = sc.nextBigInteger();

        // Step 2: Each person chooses a private (secret) number
        System.out.print("Enter 1st person's secret a: ");
        BigInteger a = sc.nextBigInteger();
        System.out.print("Enter 2nd person's secret b: ");
        BigInteger b = sc.nextBigInteger();

        // Step 3: Each computes a public key and sends it to the other
        // modPow never overflows, unlike Math.pow
        BigInteger A = g.modPow(a, p);   // 1st person: A = g^a mod p
        BigInteger B = g.modPow(b, p);   // 2nd person: B = g^b mod p
        System.out.println("1st person sends A = " + A + ", 2nd person sends B = " + B);

        // Step 4: Each computes the shared secret from the other's public key
        BigInteger keyA = B.modPow(a, p);   // 1st person: B^a mod p
        BigInteger keyB = A.modPow(b, p);   // 2nd person: A^b mod p
        System.out.println("1st person's key = " + keyA);
        System.out.println("2nd person's key = " + keyB);

        // Step 5: Check both keys match
        if (keyA.equals(keyB))
            System.out.println("They can communicate! Shared secret = " + keyA);
        else
            System.out.println("Keys do not match, they cannot communicate.");
    }
}

/*
ALGORITHM:
1. Start.
2. Read public values: prime p and primitive root g.
3. Read 1st person's private key a and 2nd person's private key b.
4. 1st person computes A = g^a mod p and sends A.
5. 2nd person computes B = g^b mod p and sends B.
6. 1st person computes K1 = B^a mod p.
7. 2nd person computes K2 = A^b mod p.
8. If K1 == K2, display the shared secret key.
9. Stop.

Example: p = 23, g = 5, a = 6, b = 15 -> A = 8, B = 19, shared key = 2

WHY IT WORKS FOR (ALMOST) ALL INPUTS:
- Math: K1 = (g^b)^a = g^(ab) and K2 = (g^a)^b = g^(ab)  (mod p), so the
  keys are ALWAYS equal, whatever a and b are.
- (int)Math.pow(g, a) % p would NOT work: g^a gets huge fast
  (5^15 = 30517578125 is already bigger than an int can hold), so the cast
  chops it and the keys come out wrong. modPow reduces mod p at every step,
  so numbers never grow beyond p and inputs of any size work.
- If g is not a primitive root the keys still match; the exchange is just
  less secure (fewer possible keys).
- p must be positive; p <= 0 stops the program with ArithmeticException.
  Letters instead of numbers also stop it.
*/
