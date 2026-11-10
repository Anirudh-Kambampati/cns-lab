/* 15. Calculate the message digest of a text using the MD5 algorithm in Java. */
import java.math.BigInteger;
import java.security.MessageDigest;

class MD5Simple {
    public static void main(String[] args) throws Exception {
        // Step 1: Get the text (from the command line, else "GeeksForGeeks")
        String text = args.length > 0 ? String.join(" ", args) : "GeeksForGeeks";

        // Step 2: Create a MessageDigest object for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // Step 3: Compute the digest (always 16 bytes = 128 bits)
        byte[] hash = md.digest(text.getBytes("UTF-8"));

        // Step 4: Convert the 16 bytes to 32 hex digits (%032x keeps leading zeros)
        String hex = String.format("%032x", new BigInteger(1, hash));

        // Step 5: Display the result
        System.out.println("MD5(\"" + text + "\") = " + hex);
    }
}

/*
ALGORITHM:
1. Start.
2. Read the input text.
3. Create a MessageDigest instance for "MD5".
4. Convert the text to bytes (UTF-8).
5. hash = digest(bytes)  -> 16-byte (128-bit) value.
6. Convert hash to a positive BigInteger, then to 32 hex digits.
7. Display the hex digest.
8. Stop.

Example: "abc" -> 900150983cd24fb0d6963f7d28e17f72

WHY IT WORKS FOR ALL INPUTS:
- MD5 accepts input of any length (even empty) and always outputs 128 bits.
- UTF-8 handles any characters.
- new BigInteger(1, hash) keeps the number positive (no minus sign).
- %032x pads with leading zeros, so the result is always exactly 32 digits.
- The same input always gives the same digest.
*/
