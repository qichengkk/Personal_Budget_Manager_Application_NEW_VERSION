package budget_comp5511.Budget_Model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

public class Encryption {
	static public String HashToSHA256(String password) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		String hex = (new HexBinaryAdapter()).marshal(md.digest(password
				.getBytes()));
		return hex;
	}
	/*
	 * Waiting for encryption and decryption methods
	 * 
	 * private String ALGO="AES"; // ������Կ // private static final byte[]
	 * keyValue = new byte[] { 'T', 'h', 'e', // 'B','e', 's', 't', 'S', 'e',
	 * 'c', 'r', 'e', 't', 'K', 'e', 'y' }; // 16λ�ļ�����Կ private byte[]
	 * keyValue="4E7FF1C1F04F4B36".getBytes();
	 * 
	 * public String encrypt(String Data) throws Exception { Key key =
	 * generateKey(); Cipher c = Cipher.getInstance(ALGO);
	 * c.init(Cipher.ENCRYPT_MODE, key); byte[] encVal =
	 * c.doFinal(Data.getBytes()); String encryptedValue = new
	 * BASE64Encoder().encode(encVal); return encryptedValue; }
	 * 
	 * public String decrypt(String encryptedData) throws Exception { Key key =
	 * generateKey(); Cipher c = Cipher.getInstance(ALGO);
	 * c.init(Cipher.DECRYPT_MODE, key); byte[] decordedValue = new
	 * BASE64Decoder().decodeBuffer(encryptedData); byte[] decValue =
	 * c.doFinal(decordedValue); String decryptedValue = new String(decValue);
	 * return decryptedValue; }
	 * 
	 * 
	 * private Key generateKey() throws Exception { Key key = new
	 * SecretKeySpec(keyValue, ALGO); return key; }
	 * 
	 * 
	 * public String getALGO() { return ALGO; }
	 * 
	 * public void setALGO(String aLGO) { ALGO = aLGO; }
	 * 
	 * public byte[] getKeyValue() { return keyValue; }
	 * 
	 * public void setKeyValue(byte[] keyValue) { this.keyValue = keyValue; }
	 */

}
