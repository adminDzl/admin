package com.fh.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

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
	
	 public static void main(String[] args) throws Exception {
	        test();
	    }

	    static void test() throws Exception {
	        String source = "http://127.0.0.1/user/toInvestmentStatus?loanId=4&tenderId=5";
	        System.out.println("加密前文字：" + source);
	        String encodedData = Base64Utils.encodeStr(source);
	        System.out.println("加密后文字：" + new String(encodedData));
	        String decodedData = Base64Utils.decodeStr(encodedData);
	        System.out.println("解密后文字: " + decodedData);
	    }
}
