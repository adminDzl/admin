package com.wolves.framework.common;

public class ResultCode {

    public static int ERROR = -1;//系统异常
    public static int SUCCESS = 0;//操作成功
    public static int FAIL = 1;//业务错误
    public static int CHECKCODE_SUCCESS = 2;//验证码正确
    public static int CHECKUSERNAME_SUCCESS = 3;//用户名存在
    public static int CHECKUSERNAME_FAIL = 4;//用户名不存在
    public static int INVEST_FAIL_RISKLEVEL = 5;//风险承受力不足
    public static int INVEST_FAIL_TRADEPWD = 6;//没有设置交易密码
    public static int INVEST_FAIL_SID = 7;//没有实名认证
}
