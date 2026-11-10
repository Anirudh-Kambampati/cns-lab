import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Scanner;

class DES2 {
    static byte[] raw;

    public static void main(String[] args) {
        try {
            // 1. Generate symmetric DES key
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56); // 56-bit DES key
            SecretKey secretKey = keyGen.generateKey();
            raw = secretKey.getEncoded();

            System.out.println("Generated DES Symmetric Key (Hex): " + bytesToHex(raw));

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] encrypt(byte[] key, byte[] data) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] key, byte[] encryptedData) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encryptedData);
    }

    // Helper method to convert bytes to hex string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02X", b));
        return sb.toString();
    }
}

