package com.wolves.common;

public class Constants {

	/**
	 * 图片访问的路径
	 */
	public static String PICTURE_VISIT_FILE_PATH = "";
	/**
	 * 图片存放的路径
	 */
	public static String PICTURE_SAVE_FILE_PATH = "";

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

	/**
	 * 验证码类短信
	 */
	public static Integer SMS_TYPE = 1;

	/**
	 * 三十分钟
	 */
	public static Integer TIME_MINUTE = 30;

	/**
	 * 编辑公司信息
	 */
	public static String editCompany = "editCompany";
	/**
	 * 编辑权限
	 */
	public static String editRight = "editRight";
	/**
	 * 缴费支付
	 */
	public static String companyPay = "companyPay";
	/**
	 * 编辑门禁
	 */
	public static String editGate = "editGate";
	/**
	 * 人员审核
	 */
	public static String verifyStaff = "verifyStaff";
}