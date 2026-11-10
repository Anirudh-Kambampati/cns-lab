import java.util.Scanner;

public class SubstitutionCipher {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String KEY = "QWERTYUIOPASDFGHJKLZXCVBNM";

    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        String upperText = text.toUpperCase();
        for (char ch : upperText.toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            if (index != -1) {
                result.append(KEY.charAt(index));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        String upperText = text.toUpperCase();
        for (char ch : upperText.toCharArray()) {
            int index = KEY.indexOf(ch);
            if (index != -1) {
                result.append(ALPHABET.charAt(index));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter plaintext: ");
        String text = sc.nextLine();

        System.out.println("\n=== Substitution Cipher ===");
        System.out.println("Plaintext Alphabet : " + ALPHABET);
        System.out.println("Cipher Alphabet    : " + KEY);

        String encrypted = encrypt(text);
        String decrypted = decrypt(encrypted);

        System.out.println("Plaintext  : " + text);
        System.out.println("Encrypted  : " + encrypted);
        System.out.println("Decrypted  : " + decrypted);
        sc.close();
    }
}
