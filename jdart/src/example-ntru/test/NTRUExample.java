package test;

import net.sf.ntru.encrypt.EncryptionKeyPair;
import net.sf.ntru.encrypt.EncryptionParameters;
import net.sf.ntru.encrypt.NtruEncrypt;
import net.sf.ntru.sign.NtruSign;
import net.sf.ntru.sign.SignatureKeyPair;
import net.sf.ntru.sign.SignatureParameters;

public class NTRUExample {
	
	//public static String str = "1111111111111111111111111111111111111111111111111111111"; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new test.NTRUExample().start();
	}
	 private static void encrypt() {
	        System.out.println("NTRU encryption");

	        // create an instance of NtruEncrypt with a standard parameter set
	        NtruEncrypt ntru = new NtruEncrypt(EncryptionParameters.APR2011_439_FAST);

	        // create an encryption key pair
	        EncryptionKeyPair kp = ntru.generateKeyPair();

	        String msg = "The quick brown fox";
	        System.out.println("  Before encryption: " + msg);

	        // encrypt the message with the public key created above
	        byte[] enc = ntru.encrypt(msg.getBytes(), kp.getPublic());

	        // decrypt the message with the private key created above
	        byte[] dec = ntru.decrypt(enc, kp);

	        // print the decrypted message
	        System.out.println("  After decryption:  " + new String(dec));
	    }

	    private static void sign() {
	        System.out.println("NTRU signature");
	        
	        // create an instance of NtruSign with a test parameter set
	        NtruSign ntru = new NtruSign(SignatureParameters.TEST157);

	        // create an signature key pair
	        SignatureKeyPair kp = ntru.generateKeyPair();

	        String msg = "The quick brown fox";
	        System.out.println("  Message: " + msg);
	        
	        // sign the message with the private key created above
	        byte[] sig = ntru.sign(msg.getBytes(), kp);
	        
	        // verify the signature with the public key created above
	        boolean valid = ntru.verify(msg.getBytes(), sig, kp.getPublic());
	        
	        System.out.println("  Signature valid? " + valid);
	    }
	
	public void start() {
		
		try {
			//java.util.List<Integer> a = new java.util.ArrayList<Integer>();
			//a.add(new Integer(0));
			encrypt();
	        //System.out.println();
	        //sign();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
