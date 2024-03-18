package com.qa.dt.util;

import java.io.IOException;
import java.util.Base64;

import com.qa.dt.base.BaseClass;



public class EncryptAndDecrypt extends BaseClass {

	public static String passwordEncryptAndDecrypt() throws IOException {
//		String originalInput = "Test@123";
//		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
//		System.out.println(encodedString);


		byte[] decodedBytes = Base64.getDecoder().decode(loadProperties().getProperty("RfPassword"));
		String decodedString = new String(decodedBytes);
//		System.out.println(decodedString);
		return decodedString;
	}
//	public static void main(String[] args) throws IOException {
//		EncryptAndDecrypt s = new EncryptAndDecrypt();
//		s.passwordEncryptAndDecrypt();
//		
//	}
}




//Test@123  ==   VGVzdEAxMjM=
//Asdf1234!  == QXNkZjEyMzQh













































