package com.wolves.util;

/**
 *
 * @author gf
 * @date 2019/3/12
 */
public class WxConfigUtil {

    /**
     * 预支付请求地址
     */
    public static final String  PrepayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 查询订单地址
     */
    public static final String  OrderUrl = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 关闭订单地址
     */
    public static final String  CloseOrderUrl = "https://api.mch.weixin.qq.com/pay/closeorder";

    /**
     * 申请退款地址
     */
    public static final String  RefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 查询退款地址
     */
    public static final String  RefundQueryUrl = "https://api.mch.weixin.qq.com/pay/refundquery";

    /**
     * 下载账单地址
     */
    public static final String  DownloadBillUrl = "https://api.mch.weixin.qq.com/pay/downloadbill";

    /**
     * 商户APPID
     */
    public static final String  AppId = "此处是商户appid";

    /**
     * 商户账户 获取支付能力后，从邮件中得到
     */
    public static final String  MchId = "此处是mchid";

    /**
     * 商户秘钥  32位，在微信商户平台中设置 api安全下的设置密钥(不是开放平台 )  注意！！！ 注意！！！！
     */
    public static final String  AppSercret = "此处是商户密钥";

    //API密钥
    public static final String API_KEY = "*-***";

    /**
     * sign type
     */
    public static final String  Sign = "MD5";

    /**
     * 服务器异步通知页面路径
     */
    public static String notify_url = "http://catering.saimark.xusage.com/catering/a/****** （为后台url）";

    /**
     * 页面跳转同步通知页面路径
     */
    public static String return_url = "此处为后台url";

    /**
     * 退款通知地址
     */
    public static String refund_notify_url = "此处为后台url";

    /**
     * 退款需要证书文件，证书文件的地址
     */
    public static String refund_file_path = "此处为后台url";

    /**
     * 商品名称
     */
    public static String subject =  "subject（自己按需要更改，也可以传值进入）";

    /**
     * 商品描述
     */
    public static String body = "微信支付（自己按需要更改，也可以传值进入）";
}
