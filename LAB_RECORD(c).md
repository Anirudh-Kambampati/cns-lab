# CNS Lab Record — Experiments 10 to 15

For each experiment write: **AIM → THEORY → ALGORITHM → SOURCE CODE → OUTPUT**.
For SOURCE CODE, copy the file named under each experiment.
Values marked *(random)* change every run, so write down what your own run prints.

---

## Experiment 10 — Blowfish encryption of "Hello world"

**AIM:** To encrypt and decrypt the text "Hello world" using the Blowfish algorithm with Java Cryptography.

**THEORY:**
Blowfish is a symmetric block cipher designed by Bruce Schneier in 1993. Symmetric means the same key is used to encrypt and to decrypt.
- Block size: 64 bits (8 bytes).
- Key size: 32 to 448 bits.
- Structure: a Feistel network with 16 rounds, using 4 S-boxes and a P-array of 18 subkeys.

Java provides Blowfish through the `javax.crypto` package. `KeyGenerator` creates the secret key, and `Cipher` performs encryption and decryption. Text whose length is not a multiple of 8 bytes is padded with PKCS5 padding. The ciphertext is shown in Base64 so that it is readable.

**ALGORITHM:**
1. Start.
2. Read the plaintext ("Hello world").
3. Generate a secret key K using KeyGenerator for "Blowfish".
4. Create a Cipher object for "Blowfish".
5. Initialise the cipher in ENCRYPT_MODE with K.
6. Ciphertext C = Encrypt(plaintext, K).
7. Initialise the cipher in DECRYPT_MODE with K.
8. Plaintext P = Decrypt(C, K).
9. Display the plaintext, C (in Base64) and P.
10. Stop.

**SOURCE CODE:** `P10/BlowfishSimple(c).java`

**OUTPUT:**
```
> javac "BlowfishSimple(c).java"
> java BlowfishSimple
Original : Hello world
Encrypted: <24 Base64 characters, e.g. 3kQ9...==>   (random)
Decrypted: Hello world
```

---

## Experiment 11 — Blowfish file encryption

**AIM:** To encrypt the contents of a file using the Blowfish algorithm in Java and verify the result by decrypting it.

**THEORY:**
This experiment uses Blowfish in **CFB (Cipher Feedback) mode**. CFB turns the block cipher into a stream cipher, so data of any length is encrypted byte by byte and no padding is needed.

CFB needs an **IV (Initialization Vector)**. This is a random 8-byte value that makes the ciphertext different on every run, even for the same file. Decryption needs both the same key and the same IV.

**ALGORITHM:**
1. Start.
2. Read the input file name (inputFile.txt).
3. Generate a Blowfish secret key K.
4. Create a Cipher for "Blowfish/CFB/NoPadding".
5. Initialise it in ENCRYPT_MODE with K and get the random IV.
6. Read all the bytes of the input file.
7. C = Encrypt(file bytes, K, IV). Write C to outputFile.txt.
8. Initialise the cipher in DECRYPT_MODE with K and the same IV.
9. P = Decrypt(C, K, IV). Display P.
10. Stop.

**SOURCE CODE:** `P11/BlowFishSimple(c).java` (keep `inputFile.txt` in the same folder)

**OUTPUT:** (inputFile.txt contains `Hello`)
```
> javac "BlowFishSimple(c).java"
> java BlowFishSimple
IV: <12 Base64 characters>   (random)
Encrypted inputFile.txt -> outputFile.txt
Decrypted: Hello
```

---

## Experiment 12 — RSA algorithm

**AIM:** To implement the RSA public-key algorithm in Java for encryption and decryption.

**THEORY:**
RSA (Rivest–Shamir–Adleman, 1977) is an asymmetric algorithm. It uses a public key (e, n) to encrypt and a private key (d, n) to decrypt. Its security comes from how hard it is to factor n = p × q when p and q are large primes.

- Key generation: n = p·q, φ(n) = (p−1)(q−1), choose e with gcd(e, φ) = 1, then d = e⁻¹ mod φ.
- Encryption: C = Mᵉ mod n
- Decryption: M = Cᵈ mod n
- The message M must satisfy 0 ≤ M < n.

**ALGORITHM:**
1. Start.
2. Read two different prime numbers p and q.
3. n = p × q.
4. φ(n) = (p − 1) × (q − 1).
5. Choose e such that gcd(e, φ) = 1. Start at 3 and add 2 until one is found.
6. Compute d such that (d × e) mod φ = 1.
7. Public key = (e, n). Private key = (d, n).
8. Read a message M where 0 ≤ M < n.
9. Encryption: C = Mᵉ mod n.
10. Decryption: M = Cᵈ mod n.
11. Display C and the decrypted M.
12. Stop.

**SOURCE CODE:** `P12/RSASimple(c).java`

**OUTPUT:**
```
> javac "RSASimple(c).java"
> java RSASimple
Enter prime p: 61
Enter prime q: 53
Public key  (e, n) = (7, 3233)
Private key (d, n) = (1783, 3233)
Enter message (number from 0 to 3232): 65
Encrypted: 1317
Decrypted: 65
```

---

## Experiment 13 — Diffie-Hellman key exchange

**AIM:** To implement the Diffie-Hellman key exchange mechanism (in Java, and in HTML and JavaScript).

**THEORY:**
Diffie-Hellman (1976) lets two people agree on a shared secret key over an insecure channel without ever sending the key itself.

1. Both agree on public values: a prime p and a primitive root g.
2. Each person picks a private number (a and b).
3. They exchange A = gᵃ mod p and B = gᵇ mod p.
4. Each computes the same secret: Bᵃ mod p = Aᵇ mod p = gᵃᵇ mod p.

An attacker who sees p, g, A and B would have to solve the *discrete logarithm problem* to find the key, which is very hard for large p.

**ALGORITHM:**
1. Start.
2. Read the prime p and the primitive root g.
3. Read the private keys a (1st person) and b (2nd person).
4. A = gᵃ mod p (sent by the 1st person).
5. B = gᵇ mod p (sent by the 2nd person).
6. K1 = Bᵃ mod p and K2 = Aᵇ mod p.
7. If K1 = K2, display the shared secret key.
8. Stop.

**SOURCE CODE:** `P13/DiffieHellmanSimple(c).java` or `P13/DHA_HTML/simple(c).html`

**OUTPUT:**
```
> javac "DiffieHellmanSimple(c).java"
> java DiffieHellmanSimple
Enter prime p: 23
Enter primitive root g: 5
Enter 1st person's secret a: 6
Enter 2nd person's secret b: 15
1st person sends A = 8, 2nd person sends B = 19
1st person's key = 2
2nd person's key = 2
They can communicate! Shared secret = 2
```
(The HTML version asks for the same values in pop-ups and shows Secret key = 2 for both.)

---

## Experiment 14 — SHA-1 message digest

**AIM:** To calculate the message digest of a text using the SHA-1 algorithm in Java.

**THEORY:**
SHA-1 (Secure Hash Algorithm 1, designed by the NSA in 1995) is a one-way hash function.
- It takes input of any length and produces a fixed **160-bit (20-byte)** digest, written as 40 hex digits.
- The input is padded and processed in 512-bit blocks over 80 rounds.
- A tiny change in the input completely changes the digest.

SHA-1 is used for integrity checks. It is now considered weak against collision attacks. In Java it is available through `java.security.MessageDigest`.

**ALGORITHM:**
1. Start.
2. Read the input text.
3. Create a MessageDigest instance for "SHA-1".
4. Convert the text to bytes.
5. hash = digest(bytes), which is a 20-byte value.
6. Convert the hash to 40 hexadecimal digits.
7. Display the digest.
8. Stop.

**SOURCE CODE:** `P14/SHA1Simple(c).java`

**OUTPUT:**
```
> javac "SHA1Simple(c).java"
> java SHA1Simple
SHA-1("Hello World") = 0a4d55a8d778e5022fab701977c5d840bbc486d0
> java SHA1Simple abc
SHA-1("abc") = a9993e364706816aba3e25717850c26c9cd0d89d
```

---

## Experiment 15 — MD5 message digest

**AIM:** To calculate the message digest of a text using the MD5 algorithm in Java.

**THEORY:**
MD5 (Message Digest 5, designed by Ron Rivest in 1991) is a hash function.
- It produces a fixed **128-bit (16-byte)** digest, written as 32 hex digits.
- The input is padded and processed in 512-bit blocks using 4 rounds of 16 operations each.

MD5 is fast and is widely used for checksums. It is no longer secure against collisions, so it should not be used for passwords or digital signatures. In Java it is available through `java.security.MessageDigest`.

**ALGORITHM:**
1. Start.
2. Read the input text.
3. Create a MessageDigest instance for "MD5".
4. Convert the text to bytes.
5. hash = digest(bytes), which is a 16-byte value.
6. Convert the hash to 32 hexadecimal digits.
7. Display the digest.
8. Stop.

**SOURCE CODE:** `P15/MD5Simple(c).java`

**OUTPUT:**
```
> javac "MD5Simple(c).java"
> java MD5Simple
MD5("GeeksForGeeks") = e39b9c178b2c9be4e99b141d956c6ff6
> java MD5Simple abc
MD5("abc") = 900150983cd24fb0d6963f7d28e17f72
```
