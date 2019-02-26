package com.wolves.framework.common;

/**
 * @author xulu
 */
public class ResultCode {

    /**
     * 系统异常
     */
    public static int ERROR = -1;
    /**
     * 操作成功
     */
    public static int SUCCESS = 0;
    /**
     * 业务错误
     */
    public static int FAIL = 1;
    /**
     * 验证码正确
     */
    public static int CHECKCODE_SUCCESS = 2;
    /**
     * 用户名存在
     */
    public static int CHECKUSERNAME_SUCCESS = 3;
    /**
     * 用户名不存在
     */
    public static int CHECKUSERNAME_FAIL = 4;
    /**
     * 风险承受力不足
     */
    public static int INVEST_FAIL_RISKLEVEL = 5;
    /**
     * 没有设置交易密码
     */
    public static int INVEST_FAIL_TRADEPWD = 6;
    /**
     * 没有实名认证
     */
    public static int INVEST_FAIL_SID = 7;
}
