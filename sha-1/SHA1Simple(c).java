/* 14. Calculate the message digest of a text using the SHA-1 algorithm in Java. */
import java.math.BigInteger;
import java.security.MessageDigest;

class SHA1Simple {
    public static void main(String[] args) throws Exception {
        // Step 1: Get the text (from the command line, else "Hello World")
        String text = args.length > 0 ? String.join(" ", args) : "Hello World";

        // Step 2: Create a MessageDigest object for SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // Step 3: Compute the digest (always 20 bytes = 160 bits)
        byte[] hash = md.digest(text.getBytes("UTF-8"));

        // Step 4: Convert the 20 bytes to 40 hex digits (%040x keeps leading zeros)
        String hex = String.format("%040x", new BigInteger(1, hash));

        // Step 5: Display the result
        System.out.println("SHA-1(\"" + text + "\") = " + hex);
    }
}

/*
ALGORITHM:
1. Start.
2. Read the input text.
3. Create a MessageDigest instance for "SHA-1".
4. Convert the text to bytes (UTF-8).
5. hash = digest(bytes)  -> 20-byte (160-bit) value.
6. Convert hash to a positive BigInteger, then to 40 hex digits.
7. Display the hex digest.
8. Stop.

Example: "abc" -> a9993e364706816aba3e25717850c26c9cd0d89d

WHY IT WORKS FOR ALL INPUTS:
- SHA-1 accepts input of any length (even empty) and always outputs 160 bits.
- UTF-8 handles any characters.
- new BigInteger(1, hash) treats the bytes as a positive number (the 1 means
  "positive"), so the hex never gets a minus sign.
- %040x pads with leading zeros, so the result is always exactly 40 digits
  (no while-loop needed to add zeros).
- The same input always gives the same digest; a tiny change gives a
  completely different one.
*/
