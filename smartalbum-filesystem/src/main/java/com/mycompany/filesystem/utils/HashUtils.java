/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package com.mycompany.filesystem.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
/**
 * Convenience class to hash user passwords.
 *
 * @author Andrey Markhel
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class HashUtils {
	private static final String CHARSET = "UTF-8";
	//private static String digestAlgorithm = "SHA-1";

	private static byte[] salt = {(byte) 0x39,(byte) 0x9B,(byte) 0xC8,(byte) 0x3A,(byte) 0x56,(byte) 0x35,(byte) 0xE3,(byte) 0x03};
	private static int iterationCount = 23;
	private static String OBFUSCATOR = "ty,d45//::dqs,,242";

	/**
	 * Convenience method to hash user passwords.
	 *
	 * @param plainTextPassword
	 *            - password to hash
	 */
//	public static String hash(String plainTextPassword) {
//		try {
//			MessageDigest digest = MessageDigest.getInstance(digestAlgorithm);
//			digest.update(plainTextPassword.getBytes(charset));
//			byte[] rawHash = digest.digest();
//			return new String("Hex.encodeHex(rawHash)");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	public static String DES_encode(String toEncode, String phrase) throws InvalidKeySpecException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
			UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {

		String code = "";
		KeySpec keySpec = new PBEKeySpec(phrase.toCharArray(), salt, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		byte[] utf8 = toEncode.getBytes("UTF8");
		byte[] enc = ecipher.doFinal(utf8);

		for (int i = 0; i < Hex.encodeHex(enc).length; i++) {
			code = code + Hex.encodeHex(enc)[i];
		}

		return code;

	}

	public static String DES_decode(String toDecode, String phrase) throws InvalidKeySpecException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
			UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		KeySpec keySpec = new PBEKeySpec(phrase.toCharArray(), salt, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
		ecipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		byte[] dec = Hex.decodeHex(toDecode.toCharArray());
		byte[] utf8 = ecipher.doFinal(dec);
		return new String(utf8, CHARSET);
	}
	
	public static String SHA_encode(String toEncode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		 
		 String code = ""; 
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(toEncode.getBytes(CHARSET));
	        byte[] data = md.digest();
	      //  log.info(Hex.encodeHex(data).toString());
	       for(int i = 0; i < Hex.encodeHex(data).length; i++) {
	    	   code = code + Hex.encodeHex(data)[i];
	       }
	        
	        return code;
	}
	
	public static int parseIntMinusOneIfError(String s) {

      int res = -1; 
      try{
          res = Integer.parseInt(s);
      } catch (NumberFormatException e) {
          res = -1;
      }
   
      return res;
  }
	
	public static String encodePasswd(String password)
	{
		try {
			return DES_encode(password, OBFUSCATOR);
		} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String decodePasswd(String encodedPassword)
	{
			try {
				return DES_decode(encodedPassword, OBFUSCATOR);
			} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException
					| BadPaddingException | DecoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	
	public static String obfuscate(Long i) {
      try {
			return DES_encode(i.toString(), OBFUSCATOR);
		} catch (InvalidKeyException e) {

			e.printStackTrace();
		} catch (InvalidKeySpecException e) {

			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (NoSuchPaddingException e) {

			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {

			e.printStackTrace();
		} catch (BadPaddingException e) {

			e.printStackTrace();
		}
		return null;
  }
	
	public static Long deobfuscate(String to){
      try {
			return Long.parseLong(DES_decode(to, OBFUSCATOR));
		} catch (NumberFormatException e) {
		
			e.printStackTrace();
		} catch (InvalidKeyException e) {

			e.printStackTrace();
		} catch (InvalidKeySpecException e) {

			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (NoSuchPaddingException e) {

			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {

			e.printStackTrace();
		} catch (BadPaddingException e) {

			e.printStackTrace();
		} catch (DecoderException e) {

			e.printStackTrace();
		}
		return null;
  }

}
