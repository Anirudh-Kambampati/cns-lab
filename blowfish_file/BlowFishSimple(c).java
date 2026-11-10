/* 11. Implement Blowfish encryption on a file using Java cryptography. */
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

class BlowFishSimple {
    public static void main(String[] args) throws Exception {
        // Step 1: Get the input file name (from the command line, else inputFile.txt)
        String inFile = args.length > 0 ? args[0] : "inputFile.txt";

        // Step 2: Generate a random Blowfish secret key
        SecretKey key = KeyGenerator.getInstance("Blowfish").generateKey();

        // Step 3: Create a Blowfish cipher in CFB mode (no padding needed)
        Cipher cipher = Cipher.getInstance("Blowfish/CFB/NoPadding");

        // Step 4: Initialise for encryption; Java creates a random IV (initialization vector)
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] iv = cipher.getIV();
        System.out.println("IV: " + Base64.getEncoder().encodeToString(iv));

        // Step 5: Read the file, encrypt it and write outputFile.txt
        // shortcut: reads the whole file into memory, fine for lab-sized files
        byte[] encrypted = cipher.doFinal(Files.readAllBytes(Paths.get(inFile)));
        Files.write(Paths.get("outputFile.txt"), encrypted);
        System.out.println("Encrypted " + inFile + " -> outputFile.txt");

        // Step 6: Decrypt with the same key and IV to prove it worked
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        System.out.println("Decrypted: " + new String(cipher.doFinal(encrypted), "UTF-8"));
    }
}

/*
ALGORITHM:
1. Start.
2. Read the input file name (default inputFile.txt).
3. Generate a Blowfish secret key K.
4. Create a Cipher for "Blowfish/CFB/NoPadding".
5. Initialise it in ENCRYPT_MODE with K; get the random IV.
6. Read all bytes of the input file.
7. C = Encrypt(file bytes, K, IV); write C to outputFile.txt.
8. Initialise the cipher in DECRYPT_MODE with K and the same IV.
9. P = Decrypt(C, K, IV); display P.
10. Stop.

WHY IT WORKS FOR ALL INPUTS:
- CFB mode turns Blowfish into a stream cipher: it encrypts byte by byte,
  so a file of ANY size (even 0 or 1 byte) works with no padding.
- The file is read as raw bytes, so any file type works (text, image ...).
- The IV makes the output different each run even for the same file;
  decryption needs the same key AND IV, which we keep in memory.
- Limit: the whole file is held in memory, so very large files
  (hundreds of MB) may run out of memory.
- If the input file does not exist, Java stops with NoSuchFileException.
*/
