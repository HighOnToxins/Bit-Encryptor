package encryption;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Scanner;

public class BitCryption { 	
	//Small and easy encryption code
	private static String encrypt(String binTxt, String binKey) {
		String enBin = "";
		
		//running through text of bits and change by using (first bit XOR second bit)
		for (int i = 0; i < binTxt.length(); i++) {
			//adding next bit to the encrypted data, by using XOR gate on current text bit and key bit
			enBin += binTxt.charAt(i) == '1' ^ binKey.charAt(i % binKey.length()) == '1' ? '1' : '0';
		}
		
		return enBin;
	}
	
	//change direction of text
	private static String reverse(String txt) {
		//reverse
		String rev = "";
		
		//for lopping this stuff
		for (int i = txt.length() - 1; i >= 0; i--) {
			rev += txt.charAt(i);
		}
		
		return rev;
	}
	
	//---------------------------ENCRYPTOR ITSELF---------------------------
	//encrypting string
	public static String encryptor(String txt, String key) {
		try {
			//first we add the variables and convert to bits
			String txtBits = new BigInteger(txt.getBytes()).toString(2);
			String keyBits = new BigInteger(key.getBytes()).toString(2);
			
			//fixing binary
			while(txtBits.length() % 8 != 0) txtBits = '0' + txtBits;
			while(keyBits.length() % 8 != 0) keyBits = '0' + keyBits;
			
			//running master encryption
			keyBits = encrypt(keyBits, reverse(keyBits));
			txtBits = encrypt(txtBits, keyBits);
			
			//changing to base 64 and returning encrypted data
			return Base64.getEncoder().encodeToString(new BigInteger(txtBits, 2).toByteArray()).replace("=", "");
		} catch (Exception e) {
			System.err.println("You cann't use a key at a length of ZERO!");
			e.printStackTrace();
			
			return "";
		}
	}
	
	//decrypting string
	public static String decryptor(String txt, String key) {
		try {
			//first we add the variables and convert to bits and getting data from base 64
			String txtBits = new BigInteger(Base64.getDecoder().decode(txt)).toString(2);
			String keyBits = new BigInteger(key.getBytes()).toString(2);
			
			//fixing binary
			while(txtBits.length() % 8 != 0) txtBits = '0' + txtBits;
			while(keyBits.length() % 8 != 0) keyBits = '0' + keyBits;

			//running master decryption
			keyBits = encrypt(keyBits, reverse(keyBits));
			txtBits = encrypt(txtBits, keyBits);
			
			//returning encrypted data
			return new String(new BigInteger(txtBits, 2).toByteArray());
		} catch (Exception e) {
			System.err.println("You cann't use a key at a length of ZERO!");
			return "";
		}
	}
	
	//---------------------------MAIN METHOD TO RUN SYSTEM ITSELF---------------------------
	public static void main(String args[]) {
		//variables
		Scanner in = new Scanner(System.in);
		
		boolean run = true;
		
		//keep running
		do {
			//encryption or decryptions
			boolean ask = true;
			String txt = "";
			do {
				//asking
				System.out.print("En / De / Out: ");
				txt = in.nextLine().replace(" ", "");
				
				//checking
				if (txt.equalsIgnoreCase("En") || txt.equalsIgnoreCase("De") || txt.equalsIgnoreCase("Out")) {
					ask = false;
				}else{
					//if no correct awnser make space
					System.out.print("\n\n\n\n\n\n");
				}
			}while(ask);
			
			//checking if wanting to exit
			if(txt.equalsIgnoreCase("Out")) {
				System.out.print("\nYou have exited the program\n");
				run = false;
			}else {
				//getting information
				System.out.println("\nTEXT: ");
				String data = in.nextLine();
				
				System.out.println("\nKEY: ");
				String key = in.nextLine();
				
				//encryption
				if (txt.equalsIgnoreCase("En")) {
					System.out.printf("\n\nEncrypted Data:\n%s\n\n", encryptor(data, key));
				}

				//decryption
				if(txt.equalsIgnoreCase("De")) {
					System.out.printf("\n\nDecrypted Data:\n%s\n\n", decryptor(data, key));
				}
			}
		}while(run);
		
		//closing scanner
		in.close();
	}
}