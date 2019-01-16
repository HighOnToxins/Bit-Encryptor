package encryption;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Scanner;

public class BitCryption { 	
	//---------------------------ENCRYPTOR ITSELF---------------------------
	//encrypting string
	public static String encrypt(String txt, String key) {
		try {
			//first we add the variables and convert to bits
			String txtBits = new BigInteger(txt.getBytes()).toString(2);
			String keyBits = new BigInteger(key.getBytes()).toString(2);
			String enTxtBits = "";
			
			//fixing binary
			while(txtBits.length() % 8 != 0) txtBits = '0' + txtBits;
			while(keyBits.length() % 8 != 0) keyBits = '0' + keyBits;
			
			//running through text of bits and change by using (first bit XOR second bit)
			for (int i = 0; i < txtBits.length(); i++) {
				//adding next bit to the encrypted data, by using XOR gate on current text bit and key bit
				enTxtBits += txtBits.charAt(i) == '1' ^ keyBits.charAt(i % keyBits.length()) == '1' ? '1' : '0';
			}
			
			//changing to base 64 and returning encrypted data
			return Base64.getEncoder().encodeToString(new BigInteger(enTxtBits, 2).toByteArray()).replace("=", "");
		} catch (Exception e) {
			System.err.println("You cann't use a key at a length of ZERO!");
			return "";
		}
	}
	
	//decrypting string
	public static String decrypt(String txt, String key) {
		try {
			//first we add the variables and convert to bits and getting data from base 64
			String txtBits = new BigInteger(Base64.getDecoder().decode(txt)).toString(2);
			String keyBits = new BigInteger(key.getBytes()).toString(2);
			String enTxtBits = "";
			
			//fixing binary
			while(txtBits.length() % 8 != 0) txtBits = '0' + txtBits;
			while(keyBits.length() % 8 != 0) keyBits = '0' + keyBits;

			//running through text of bits and change by using (first bit XOR second bit)
			for (int i = 0; i < txtBits.length(); i++) {
				//adding next bit to the encrypted data, by using XOR gate on current text bit and key bit
				enTxtBits += txtBits.charAt(i) == '1' ^ keyBits.charAt(i % keyBits.length()) == '1' ? '1' : '0';
			}
			
			//returning encrypted data
			return new String(new BigInteger(enTxtBits, 2).toByteArray());
		} catch (Exception e) {
			System.err.println("You cann't use a key at a length of ZERO!");
			return "";
		}
	}
	
	//---------------------------MAIN METHOD TO RUN SYSTEM ITSELF---------------------------
	public static void main(String args[]) {
		//variables
		Scanner in = new Scanner(System.in);
		
		//encryption or decryptions
		boolean ask = true;
		String txt = "";
		do {
			//asking
			System.out.print("En/De: ");
			txt = in.nextLine().replace(" ", "");
			
			//checking
			if (txt.equalsIgnoreCase("En") || txt.equalsIgnoreCase("De")) {
				ask = false;
			}else{
				//if no correct awnser make space
				System.out.print("\n\n\n\n\n\n");
			}
		}while(ask);
		
		//getting needed information : data + key
		System.out.println("\nTEXT: ");
		String data = in.nextLine();
		
		System.out.println("\nKEY: ");
		String key = in.nextLine();
		
		//encryption
		if (txt.equalsIgnoreCase("En")) System.out.print("\n\nEncrypted Data:\n" + encrypt(data, key));
		
		//decryption
		if(txt.equalsIgnoreCase("De")) System.out.print("\n\nDecrypted Data:\n" + decrypt(data, key));
		
		//closing scanner
		in.close();
	}
}
