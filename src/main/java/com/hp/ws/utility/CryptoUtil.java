package com.hp.ws.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:06:01
 */
public class CryptoUtil {
	Cipher dcipher;
	int iterationCount = 10;
	int keyStrength = 128;
	SecretKey key;
	byte[] iv;

	public CryptoUtil(byte[] salt) {
		SecretKeyFactory factory;
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			String passPhrase = "ABCDEFGHIJKL";
			KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount, keyStrength);
			SecretKey tmp = factory.generateSecret(spec);
			key = new SecretKeySpec(tmp.getEncoded(), "AES");
			dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String encrypt(String data) {
		return encrypt(data, null);
	}

	public String encrypt(String data, byte[] ivPrm) {
		String base64EncryptedData = null;
		try {
			if (ivPrm != null) {
				dcipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivPrm));
			} else {
				dcipher.init(Cipher.ENCRYPT_MODE, key);
			}
			AlgorithmParameters params = dcipher.getParameters();
			iv = params.getParameterSpec(IvParameterSpec.class).getIV();

			byte[] utf8EncryptedData = dcipher.doFinal(data.getBytes());
			base64EncryptedData = new String(BASE64EncoderStream.encode(utf8EncryptedData));

			// new sun.misc.BASE64Encoder().encodeBuffer(utf8EncryptedData);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64EncryptedData;
	}

	public String decrypt(String base64EncryptedData) {
		return decrypt(base64EncryptedData, null);
	}

	public String decrypt(String base64EncryptedData, byte[] ivPrm) {
		try {
			if (ivPrm != null) {
				dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivPrm));
			} else {
				dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			}

			byte[] decryptedData = BASE64DecoderStream.decode(base64EncryptedData.getBytes());// new
																								// sun.misc.BASE64Decoder().decodeBuffer(base64EncryptedData);
			byte[] utf8 = dcipher.doFinal(decryptedData);
			return new String(utf8, "UTF8");
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]) throws Exception {
		byte[] salt = "1399738873".getBytes();// salt= counter
		String otp = "40131986" + ";" + new String(salt);// otp =otp;counter
		// byte[] ivPrm = data.getBytes();

		CryptoUtil decrypter = new CryptoUtil(salt);
		String encrypted = decrypter.encrypt(otp, decrypter.getIv());
		System.err.println(encrypted);
		System.err.println("IV:" + decrypter.getIv());
		String decrypted = decrypter.decrypt(encrypted);
		System.out.println(decrypted);
	}

	public byte[] getIv() {
		return iv;
	}
}