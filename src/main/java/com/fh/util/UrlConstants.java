package com.fh.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xulu on 2016/3/8.
 */
public class UrlConstants {

    private static Map map = new HashMap<String, String>();
    public static Map getWapMap(){
        if(null == map || map.size() == 0){
            //wap的url，后期考虑存储在db
            map.put("1", "/appwechat/weather");
            map.put("2", "/index");
            map.put("3", "/user/showReply");
            map.put("4","/user/invest");
            map.put("5", "");
            map.put("6", "/user/showUserFriends");
            map.put("7", "/user/showUserFriends");
            map.put("8", "");
            map.put("9", "/app/down");
            map.put("10", "/stat/help.htm");
            map.put("11", "/user/sign");
            map.put("12", "");
            map.put("13", "");
            map.put("14", "/app/down");
            map.put("15", "");
        }
        return map;
    }

    public static String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    public static String GET_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public static String GET_USER_WECHAT_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    public static String GET_USER_INFO_BATCH = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";

    public static String GET_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static String GET_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

    public static String GET_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

    public static String GET_PAGE_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

    public static String GET_OAUTH = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";

    /**
     * 设置模板消息行业
     */
    public static String POST_SET_INDUSTRY = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";

    /**
     * 获得模板id
     */
    public static String GET_TEMOLATE_ID = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";

    /**
     * 获取模板列表
     */
    public static String GET_TEMPLATE_LIST = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";

    /**
     * 模板消息发送
     */
    public static String POST_SEND_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

}
