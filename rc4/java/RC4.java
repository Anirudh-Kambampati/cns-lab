/*
Write the RC4 logic in Java Using Java cryptography; encrypt the text “Hello world” using Blowfish.
Create your own key using Java key tool.
*/
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

public class RC4 {
    static byte[] rawKey;

    public static void main(String[] args) {
        try {
            // 1. Generate RC4 symmetric key
            KeyGenerator keyGen = KeyGenerator.getInstance("RC4");
            keyGen.init(128); // Key size can be 40 to 2048 bits (128 bits is typical)
            SecretKey secretKey = keyGen.generateKey();
            rawKey = secretKey.getEncoded();
            System.out.println("Generated RC4 Symmetric Key (Hex): " + bytesToHex(rawKey));

            // 2. Input message from terminal
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter message to encrypt: ");
            String inputMessage = scanner.nextLine();
            byte[] inputBytes = inputMessage.getBytes();

            // 3. Encrypt
            byte[] encryptedBytes = encryptRC4(rawKey, inputBytes);
            System.out.println("Encrypted Message (Hex): " + bytesToHex(encryptedBytes));

            // 4. Decrypt
            byte[] decryptedBytes = decryptRC4(rawKey, encryptedBytes);
            String decryptedMessage = new String(decryptedBytes);
            System.out.println("Decrypted Message: " + decryptedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] encryptRC4(byte[] key, byte[] data) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "RC4");
        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

    private static byte[] decryptRC4(byte[] key, byte[] encryptedData) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "RC4");
        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encryptedData);
    }

    // Helper to convert byte array to hex string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02X", b));
        return sb.toString();
    }
}




