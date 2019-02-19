package com.fh.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.fh.service.wechat.WechatService;
import com.fh.util.Constants;
import com.fh.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller基类
 */

public class BaseController {

    @Autowired
    private WechatService wechatService;

    /**
     * 获取用户ID
     */
    public String getUserId(HttpServletRequest request) throws Exception {
        return CookieUtils.getCookie(request, "website_name");
    }

    /**
     * 获取用户签证（token）
     */
    public String getToken(HttpServletRequest request) throws Exception {
        return CookieUtils.getCookie(request, "website_token");
    }

    /**
     * 获取用户手机号
     */
    public String getMobile(HttpServletRequest request) throws Exception {
        return CookieUtils.getCookie(request, "user_tel");
    }

    /**
     *code 换取open_id
     */
    public String getOpenId(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        JSONObject obj = wechatService.getOAuthInfo(Constants.getAppId(), Constants.getSecret(), code);
        return obj.getString("openid");
    }
}