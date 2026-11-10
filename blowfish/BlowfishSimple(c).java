/* 10. Encrypt the text "Hello world" using Blowfish with Java cryptography. */
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

class BlowfishSimple {
    public static void main(String[] args) throws Exception {
        // Step 1: Get the text (from the command line, else "Hello world")
        String text = args.length > 0 ? String.join(" ", args) : "Hello world";

        // Step 2: Generate a random Blowfish secret key
        SecretKey key = KeyGenerator.getInstance("Blowfish").generateKey();

        // Step 3: Create a Blowfish cipher object
        Cipher cipher = Cipher.getInstance("Blowfish");

        // Step 4: Encrypt - put cipher in ENCRYPT mode and encrypt the text bytes
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));

        // Step 5: Decrypt - put the same cipher in DECRYPT mode with the same key
        cipher.init(Cipher.DECRYPT_MODE, key);
        String decrypted = new String(cipher.doFinal(encrypted), "UTF-8");

        // Step 6: Display results (Base64 so encrypted bytes print as readable text)
        System.out.println("Original : " + text);
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));
        System.out.println("Decrypted: " + decrypted);
    }
}

/*
ALGORITHM:
1. Start.
2. Read the plaintext (default "Hello world").
3. Generate a secret key K using KeyGenerator for "Blowfish".
4. Create a Cipher object for "Blowfish".
5. Initialise the cipher in ENCRYPT_MODE with K.
6. Ciphertext C = Encrypt(plaintext bytes, K).
7. Initialise the cipher in DECRYPT_MODE with the same K.
8. Plaintext P = Decrypt(C, K).
9. Display plaintext, C (in Base64) and P.
10. Stop.

WHY IT WORKS FOR ALL INPUTS:
- Blowfish works on 8-byte blocks; the default mode pads the last block
  (PKCS5 padding), so text of any length (even empty) is accepted.
- Text is converted using UTF-8, so any language or symbol is handled.
- Encrypted bytes are random-looking binary; Base64 turns them into
  printable characters, so the output is never garbled (printing raw
  bytes directly would show junk characters).
- Decryption with the same key always gives back the original text.
- The key is random, so the encrypted output changes every run. That is normal.
*/
