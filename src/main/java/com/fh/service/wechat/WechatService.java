package com.fh.service.wechat;

import com.fh.common.data.Result;
import com.fh.entity.SignRequest;
import com.fh.util.*;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * Created by xulu on 2016/3/15.
 */
@Service
public class WechatService {

    @Value("${WEBSITEURL}")
    private String hostPath;

    private String SSO_URL;

    /**
     *sso认证微信用户
     */
    public JSONObject certify(String openId) throws Exception{
        String url = this.SSO_URL;//+ this.ssoLoginPath + "?openId=" + openId;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        Long t1 = new Date().getTime();
        System.out.println("certify begin:" + t1);
        HttpResponse response = httpClient.execute(httpPost);
        Long t2 = new Date().getTime();
        System.out.println("certify end:" + t2);
        System.out.println("count:" + (t2 - t1));
        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "utf-8"));
        JSONObject obj = JsonUtils.parseJson(br, JSONObject.class);
        br.close();
        return obj;
    }

    /**
     *获取wap端access_token
     * 1.session中
     * 2.通过refresh_token调微信api获取最新
     */
    public String getAccessToken(HttpServletRequest request) throws Exception{
        String refresh_token = CookieUtils.getCookie(request, "refresh_token");
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("access_token");
        String access_token = "";
        if(null == obj){
            //调wechat api获取token
            Object tokenObj = this.getPageToken(refresh_token, Constants.getAppId());
            access_token = (String) JSONObject.fromObject(tokenObj).get("access_token");
            session.setAttribute("access_token", access_token);
        } else {
            access_token = (String)obj;
        }
        return access_token;
    }

    /**
     *setWechatInfo
     */
    public void setWechatInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        com.alibaba.fastjson.JSONObject obj = this.getOAuthInfo(Constants.getAppId(), Constants.getSecret(), code);
        String accesss_token = obj.getString("access_token");
        String open_id =obj.getString("openid");
        String refresh_token = obj.getString("refresh_token");

        HttpSession session = request.getSession();
        session.setAttribute("access_token", accesss_token);
        session.setAttribute("open_id", open_id);

        Cookie refreshTokenCookie = new Cookie("refresh_token", refresh_token);
        refreshTokenCookie.setMaxAge(7* 24 * 60);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
    }

    /**
     *三方认证
     */
    public void setUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        String openId = (String)session.getAttribute("open_id");
        String uid = CookieUtils.getCookie(request, "website_name");
        String token = CookieUtils.getCookie(request, "website_token");
        if(StringUtils.isEmpty(uid) || StringUtils.isEmpty(token)){
            JSONObject obj = this.certify(openId);
            String status =obj.getString("status");
            JSONObject data = obj.getJSONObject("data");
            if("1".equals(status)){
                uid = data.getLong("uid")+"";
                token = data.getString("ticket");
                CookieUtils.setCookie(response,"website_name",uid);
                CookieUtils.setCookie(response,"website_token",token);
            }
        }
        session.setAttribute("website_name", uid);
        session.setAttribute("website_token", token);
    }

    /**
     * 获取网页用户信息
     */
    public com.alibaba.fastjson.JSONObject getOAuthInfo(String appId, String secret, String code) throws Exception{
        String openIdUrl = UrlConstants.GET_OPENID;
        String requestUrl = openIdUrl.replace("APPID", appId).replace("SECRET", secret).replace("CODE", code);
        return HttpRequestUtil.httpRequest(requestUrl, "GET", null);
    }

    /**
     * 获取用户微信信息
     */
    public com.alibaba.fastjson.JSONObject getUserInfo(String access_token,String openid) throws Exception{
        String wechatInfoUrl = UrlConstants.GET_USER_WECHAT_INFO;
        String requestUrl = wechatInfoUrl.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
        return HttpRequestUtil.httpRequest(requestUrl, "GET", null);
    }

    /**
     * 批量获取用户微信信息
     */
    public com.alibaba.fastjson.JSONObject getUserInfoBatch(String access_token, String userList) throws Exception{
        String url = UrlConstants.GET_USER_INFO_BATCH;
        String requestUrl = url.replace("ACCESS_TOKEN", access_token);
        return HttpRequestUtil.httpRequest(requestUrl, "POST", userList);
    }

    /**
     * 创建菜单
     */
    public void createMenu(String data, String access_token) {
        String url = UrlConstants.CREATE_MENU;
        String requestUrl = url.replace("ACCESS_TOKEN", access_token);
        HttpRequestUtil.httpRequest(requestUrl, "POST", data);
    }

    /**
     * 获取二维码ticket
     */
    public String getWechatTicket(String userId, String accessToken) throws Exception{
        String data = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": USERID}}}";
        String url = UrlConstants.GET_TICKET;
        String requestUrl = url.replace("ACCESS_TOKEN", accessToken);
        com.alibaba.fastjson.JSONObject jsonObject =  HttpRequestUtil.httpRequest(requestUrl, "POST", data.replace("USERID", userId));
        return jsonObject.toString();
    }

    /**
     *获取网页token
     */
    public com.alibaba.fastjson.JSONObject getPageToken(String refresh_token, String appId) throws Exception{
        String url = UrlConstants.GET_PAGE_TOKEN;
        return HttpRequestUtil.httpRequest(url.replace("APPID", appId).replace("REFRESH_TOKEN", refresh_token), "GET", null);
    }

    /**
     * sso认证
     */
    public com.alibaba.fastjson.JSONObject oauth(String token, String openId) throws Exception{
        String url = UrlConstants.GET_OAUTH;
        return HttpRequestUtil.httpRequest(url.replace("ACCESS_TOKEN", token).replace("OPENID", openId),"GET", null );
    }

    /**
     * 设置模板行业
     */
    public com.alibaba.fastjson.JSONObject setIndustry(String token, String data) throws Exception{
        String url = UrlConstants.POST_SET_INDUSTRY;
        return HttpRequestUtil.httpRequest(url.replace("ACCESS_TOKEN", token), "POST", data);
    }

    /**
     * 获得模板id
     */
    public com.alibaba.fastjson.JSONObject getTempId(String token, String data) throws Exception{
        String url = UrlConstants.GET_TEMOLATE_ID;
        return HttpRequestUtil.httpRequest(url.replace("ACCESS_TOKEN", token), "POST", data);
    }

    /**
     * 获取模板列表
     */
    public com.alibaba.fastjson.JSONObject getTemp(String token, String data) throws Exception{
        String url = UrlConstants.GET_TEMPLATE_LIST;
        return HttpRequestUtil.httpRequest(url.replace("ACCESS_TOKEN", token), "POST" ,data);
    }

    /**
     * 模板消息发送
     */
    public com.alibaba.fastjson.JSONObject sendTemp(String token, String data) throws Exception{
        String url = UrlConstants.POST_SEND_TEMPLATE;
        return HttpRequestUtil.httpRequest(url.replace("ACCESS_TOKEN", token), "POST" ,data);
    }

    /**
     * print url
     */
    public void printUrl(){
        String url = UrlConstants.GET_CODE.replace("APPID", Constants.getAppId()).replace("STATE", "wechat_token");
        String redirectUrl = hostPath;
        Map map = UrlConstants.getWapMap();
        for(int i = 1; i < 16; i++){
            System.out.println(i +"<>"+url.replace("REDIRECT_URI", URLEncoder.encode(redirectUrl+map.get(i+""))));
        }
    }

    /**
     * 签到
     */
    public Result signIn(String openId) throws Exception {
        Result result = new Result();
        //判断绑定状态
        boolean flag = false;//acctService.getUserBind(openId);
        if(!flag){
            result.setResult(1);
            result.setMsg("您未绑定服务号，请先绑定！");
            return result;
        }
        //三方认证
        JSONObject obj = this.certify(openId);
        String status =obj.getString("status");
        JSONObject data = obj.getJSONObject("data");
        System.out.println("微信签到，认证返回：" + data);
        String userId = "";
        String token = "";
        if("1".equals(status)){
            userId = data.getLong("uid")+"";
            token = data.getString("ticket");
        } else {
            result.setResult(1);
            result.setMsg("签到失败！");
            return result;
        }
        SignRequest signRequest = new SignRequest();
        signRequest.setUserId(userId);
        signRequest.setFrom("wx");
        //签到
        Result signData = null;//userAccountService.sign(signRequest, token);
        String msg = "";
        //获取可用银两
//        ComplexBalanceView complex =null;// userAccountService.getComplexBalance(userId, token);
//        BigDecimal yinpiao = complex.getYinPiao();
//        if(0 == signData.getResult()){
//            msg += "成功获取银票1两，目前您的可用银两为"+yinpiao+"两。";
//        } else if(1 == signData.getResult()){
//            msg += "一天只能签到一次哦，目前您的可用银两为"+yinpiao+"两。";
//        }
        result.setMsg(msg);
        return result;
    }

    /**
     * 同步头像
     */
    public Result syncHeadImg(String openId, String imgUrl) throws Exception{
        Result resultData = new Result();
        //三方认证
        JSONObject obj = this.certify(openId);
        String status =obj.getString("status");
        JSONObject data = obj.getJSONObject("data");
        System.out.println("微信签到，认证返回：" + data);
        String token = "";
        if("1".equals(status)){
            token = data.getString("ticket");
        } else {
            resultData.setResult(1);
            resultData.setMsg("同步头像失败！");
            return resultData;
        }
//        ResultData result = getHost(token).get(this.syncUrl+"?relationId="+openId+"&relationType=wx"+"&headImg="+imgUrl, null, ResultData.class);
//        if (checkResult(result.getResult())) {
//            resultData.setResult(result.getResult());
//            resultData.setMsg(result.getMsg());
//        } else {
//            resultData.setMsg("稍后再试");
//            resultData.setResult(1);
//        }
        return resultData;
    }
}
