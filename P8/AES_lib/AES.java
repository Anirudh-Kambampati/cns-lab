import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

class AES {
    static byte[] raw;

    public static void main(String[] args) {
        try {
            // 1. Generate symmetric AES key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // 128-bit AES key (standard, highly secure)
            SecretKey secretKey = keyGen.generateKey();
            raw = secretKey.getEncoded();

            System.out.println("Generated AES Symmetric Key (Hex): " + bytesToHex(raw));

            // 2. Input message from terminal
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter message to encrypt: ");
            String inputMessage = scanner.nextLine();
            byte[] inputBytes = inputMessage.getBytes();

            // 3. Encrypt
            byte[] encryptedBytes = encrypt(raw, inputBytes);
            System.out.println("Encrypted Message (Hex): " + bytesToHex(encryptedBytes));

            // 4. Decrypt
            byte[] decryptedBytes = decrypt(raw, encryptedBytes);
            String decryptedMessage = new String(decryptedBytes);
            System.out.println("Decrypted Message: " + decryptedMessage);

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Modular Encryption Function
    private static byte[] encrypt(byte[] key, byte[] data) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(data);
    }

    // Modular Decryption Function
    private static byte[] decrypt(byte[] key, byte[] encryptedData) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encryptedData);
    }

    // Helper method to convert bytes to hex string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}

