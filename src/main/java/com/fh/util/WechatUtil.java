package com.fh.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class WechatUtil {

    /**
     * access_token
     */
    private static String token = "";

    /**
     * 更新access_token的时间
     */
    private static Date date = new Date();

    public static void setToken(String token) {
        WechatUtil.token = token;
    }

    public static String getToken() {
        return token = getAccessToken(WechatUtil.token, WechatUtil.date);
    }

    public static Date getDate() {
        return date;
    }

    public static void setDate(Date date) {
        WechatUtil.date = date;
    }

   static Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    /**
     * 获取全局access_token
     */
    public static String getAccessToken(String token, Date date){
        Date nowDate = new Date();
        //access_token每两小时过期，这里每半小时获取一次
        if(StringUtils.isEmpty(token) || null == date || nowDate.getTime() - date.getTime() > 60 * 30 * 1000){
            try{
                String accesstokenUrl = UrlConstants.GET_ACCESSTOKEN;
                String requestUrl = accesstokenUrl.replace("APPID", Constants.getAppId()).replace("APPSECRET", Constants.getSecret());
                JSONObject obj = HttpRequestUtil.httpRequest(requestUrl, "GET", null);
                if(null != obj){
                    token = obj.getString("access_token");
                }
            } catch (Exception e){
                logger.error("获取access_token异常", e);
            }
            WechatUtil.setDate(nowDate);
            WechatUtil.setToken(token);
        }
        logger.info("token"+token);
        return token;
    }
}
