/*Write the RC4 logic in Java Using Java cryptography; encrypt the text “Hello world” using Blowfish. Create your own key using Java key tool.*/
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
public class BlowFishCipher
{
	public static void main(String []args) throws Exception
	{
	//create a keygenerator based upon the
	KeyGenerator keygenerator= KeyGenerator.getInstance("Blowfish");
	//create a key
	SecretKey secretkey=keygenerator.generateKey();
	//create a cipher based upon Blowfish
	Cipher cipher=Cipher.getInstance("Blowfish");
	//initialise cipher to with secretkey
	cipher.init(Cipher.ENCRYPT_MODE,secretkey);
	//get the text to encrypt
	String inputText = "Hello world";
	//encrypt message
	byte[] encrypted=cipher.doFinal(inputText.getBytes());
	//re-initialise the cipher to be in decrypt mode
	cipher.init(Cipher.DECRYPT_MODE,secretkey);
	//decrypt message
	byte[] decrypted=cipher.doFinal(encrypted);
	//and display the results
	System.out.println("Original String: " + inputText);
	System.out.println("Encrypted: " + new String(encrypted));
	System.out.println("Decrypted: " + new String(decrypted));
	}
}
