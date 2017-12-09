package com.hp.ws.utility;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Base64;
 

public class TripleDESUtil {

	public static void main(String[] args) throws Exception {

		String text = "halis ocak";

		String key="59AB25DF56CA29A1";
		String codedtext = new TripleDESUtil().encrypt(text,key);
		String decodedtext = new TripleDESUtil().decrypt(Base64.decode(codedtext.getBytes()),key);
		
		System.out.println(codedtext);
		System.out.println(decodedtext);
	}

	public static String encrypt(String message, String key) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
		final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		final byte[] plainTextBytes = message.getBytes("utf-8");
		final byte[] cipherText = cipher.doFinal(plainTextBytes);
		// final String encodedCipherText = new sun.misc.BASE64Encoder()
		// .encode(cipherText);
		return new String(Base64.encode(cipherText));
	}

	public static String decrypt(byte[] message, String key) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
		final Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, secretKey);
		final byte[] plainText = decipher.doFinal(message);

		return new String(plainText, "UTF-8");
	}
}