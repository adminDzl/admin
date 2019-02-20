package com.fh.util;

import org.apache.commons.codec.binary.Base64;
import java.io.UnsupportedEncodingException;

/**
 * @author msi
 */
public class Base64Utils {

	/**
	 * Base64加密
	 */
	public static String encodeStr(String plainText){
		byte[] b=plainText.getBytes();
		Base64 base64=new Base64();
		b=base64.encode(b);
		String s=new String(b);
		return s;
	}
	
	/**
	 * Base64解密
	 */
	public static String decodeStr(String encodeStr) throws UnsupportedEncodingException{
		byte[] b=encodeStr.getBytes("utf-8");
		Base64 base64=new Base64();
		b=base64.decode(b);
		String s=new String(b);
		return s;
	}
}
