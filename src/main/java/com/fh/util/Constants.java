package com.fh.util;

public class Constants {
	
	public static String PICTURE_VISIT_FILE_PATH = "";//图片访问的路径
	
	public static String PICTURE_SAVE_FILE_PATH = "";//图片存放的路径

	public static String getPICTURE_VISIT_FILE_PATH() {
		return PICTURE_VISIT_FILE_PATH;
	}

	public static void setPICTURE_VISIT_FILE_PATH(String pICTURE_VISIT_FILE_PATH) {
		PICTURE_VISIT_FILE_PATH = pICTURE_VISIT_FILE_PATH;
	}

	public static String getPICTURE_SAVE_FILE_PATH() {
		return PICTURE_SAVE_FILE_PATH;
	}

	public static void setPICTURE_SAVE_FILE_PATH(String pICTURE_SAVE_FILE_PATH) {
		PICTURE_SAVE_FILE_PATH = pICTURE_SAVE_FILE_PATH;
	}

	private static String token="";

	private static String appId="";

	private static String secret = "";

	public static final String GROUP1="1";

	public static final String GROUP2="2";

	public static final String GROUP3="3";

	public static final String GROUP4="4";

	public static final String MENU_CLICK_SIGN_IN = "click_type_sign_in";

	public static String getToken(){
		if(StringUtils.isEmpty(token)){
			token = PropUtils.getConfigMessage("TOKEN");
		}
		return token;
	}
	public static String getAppId(){
		if(StringUtils.isEmpty(appId)){
			appId = PropUtils.getConfigMessage("APPID");
		}
		return appId;
	}
	public static String getSecret(){
		if(StringUtils.isEmpty(secret)){
			secret = PropUtils.getConfigMessage("SECRET");
		}
		return secret;
	}

	public static void main(String[] args) {
		Constants.setPICTURE_SAVE_FILE_PATH("D:/Tomcat 6.0/webapps/FH/topic/");
		Constants.setPICTURE_VISIT_FILE_PATH("http://192.168.1.225:8888/FH/topic/");
	}
	
}
