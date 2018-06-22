package com.yodlee.sms.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Named;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.yodlee.sms.constants.Constants;

@Named
public class ToolAuthenticator {
	
	private static String KEYSTORELOCATION;
	
	@Value("${keystore-location}")
	private void setKEYSTORELOCATION(String kEYSTORELOCATION) {
		ToolAuthenticator.KEYSTORELOCATION = kEYSTORELOCATION;
	}
	public static String getSecurityToken(String userName, String password) throws JsonParseException, IOException {
		JsonNode response = RestTemplateUtil.generateToken(userName, password);
		String token=null;
		if(response.get(Constants.AUTHENTICATION_KEY).asText().equals(Constants.SUCCESS)){
		 token= response.get(Constants.SECURITY_TOKEN_KEY).asText();
		}
		return token;
	}
	public static String doCrypt(String value) {
		String result = null;
		try {
			   KeyStore keyStore = KeyStore.getInstance("JCEKS");
			   FileInputStream stream = new FileInputStream(KEYSTORELOCATION);
			   keyStore.load(stream, "password".toCharArray());
			   Key key = keyStore.getKey("mykey", "password".toCharArray());
			   result = decryptWithAESKey(value, key.getEncoded());
			  } catch (Exception e) {
			   e.printStackTrace();
			  } 
		return result;
	}
	public static String decryptWithAESKey(String inputData, byte[] key) throws NoSuchAlgorithmException,
	   NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	  Cipher cipher = Cipher.getInstance("AES");
	  SecretKey secKey = new SecretKeySpec(key, "AES");
	  cipher.init(Cipher.DECRYPT_MODE, secKey);
	  byte[] newData = cipher.doFinal(Base64.decodeBase64(inputData.getBytes()));
	  return new String(newData);

	 } 
}
