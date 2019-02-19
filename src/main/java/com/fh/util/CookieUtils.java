package com.fh.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 从request中获取cookie信息
 */
public class CookieUtils {

    public static final int COOKIE_DATE = 5; //分钟

    /**
     * 根据cookie的名字获取cookie的值
     */
    public static String getCookie(HttpServletRequest request,
                                   String cookieName) throws Exception {
        Cookie cookies[] = request.getCookies();
        String value = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    value = cookie.getValue();
                }
            }
        }
        return value;
    }

    public static void setCookie(HttpServletResponse response, String key, String value) throws Exception {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(COOKIE_DATE * 60);
        //cookie.setDomain(".yinpiao.com/");
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse response, String key) throws Exception {
        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
        //cookie.setDomain(".yinpiao.com/");
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
