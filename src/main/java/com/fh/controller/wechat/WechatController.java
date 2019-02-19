package com.fh.controller.wechat;

import com.fh.service.wechat.WechatService;
import com.fh.util.Base64Utils;
import com.fh.util.Constants;
import com.fh.util.CookieUtils;
import com.fh.util.StringUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by xulu on 2016/3/11.
 */
@Controller
@RequestMapping("/wechat")
public class WechatController extends BaseController {


    @Autowired
    private WechatService wechatService;

    /**
     * wechat redirect
     */
    @RequestMapping("/bind")
    public ModelAndView bind(HttpServletRequest request,HttpServletResponse response, ModelAndView model) throws Exception {
        HttpSession session = request.getSession();
        String openId = (String)session.getAttribute("open_id");
        boolean flag = false;//acctService.getUserBind(openId);
        if(flag){
            //已绑定用户点击绑定
            model.setViewName("/user/bind_success");
        } else{
            model.setViewName("/user/bind_acount");
        }
        return model;
    }

    /**
     * bind_success
     */
    @RequestMapping("/bind_success")
    public ModelAndView bindSuccess(ModelAndView model) throws Exception {
        model.setViewName("/user/bind_success");
        return model;
    }


    @RequestMapping(value = "/getweixinParam", method = RequestMethod.POST)
    @ResponseBody
    public Object getParam(HttpServletRequest request, HttpServletResponse response, String url) {
        String access_token = null;
        String jsapi_ticket = null;
        String buid = null;
        try {
            if (StringUtils.isEmpty(CookieUtils.getCookie(request, "access_token")) || StringUtils.isEmpty(CookieUtils.getCookie(request, "jsapi_ticket"))) { //判断是否存储过微信token 没有则去微信平台接口掉用获取票据
                access_token = getAccessToken();
                jsapi_ticket = getJsapi_ticket(access_token);

                CookieUtils.setCookie(response, "access_token", access_token);
                CookieUtils.setCookie(response, "jsapi_ticket", jsapi_ticket);
            } else {
                jsapi_ticket = CookieUtils.getCookie(request, "jsapi_ticket");//直接从redis中取票据
            }
            buid = CookieUtils.getCookie(request, "website_name");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> params = sign(jsapi_ticket, url);

        params.put("appID", Constants.getAppId());
        params.put("buid", Base64Utils.encodeStr(buid));
        JSONObject jsonObject = JSONObject.fromObject(params);
        return jsonObject;

    }


    /**
     * 获取微信accesstoken
     *
     * @return
     * @throws Exception
     */
    private String getAccessToken() throws Exception {
        RestTemplate cardTemplate = new RestTemplate();
        String acctokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.getAppId() + "&secret=" + Constants.getSecret();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        ResponseEntity<String> responseEntity = cardTemplate.exchange(acctokenUrl,
                HttpMethod.POST, new HttpEntity<byte[]>(headers), String.class);
        JSONObject jsonObj = JSONObject.fromObject(responseEntity.getBody());
        return (String) jsonObj.get("access_token");
    }


    /**
     * 获取 票据 调用微信JS接口
     *
     * @param access_token
     * @return
     * @throws Exception
     */
    private String getJsapi_ticket(String access_token) throws Exception {
        RestTemplate cardTemplate = new RestTemplate();
        String acctokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        ResponseEntity<String> responseEntity = cardTemplate.exchange(acctokenUrl,
                HttpMethod.POST, new HttpEntity<byte[]>(headers), String.class);
        JSONObject jsonObj = JSONObject.fromObject(responseEntity.getBody());
        return (String) jsonObj.get("ticket");
    }


    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }


    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
}
