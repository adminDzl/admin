package com.fh.controller.wechat;

import com.fh.service.wechat.WechatService;
import com.fh.util.Constants;
import com.fh.util.PropUtils;
import com.fh.util.UrlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by xulu on 2016/3/10.
 */
@Controller
public class GateWayController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WechatService wechatService;

    /**
     *get code to exchange access_token and openId
     */
    @RequestMapping(value="/appwechat/gateway", method = RequestMethod.GET)
    @ResponseBody
    public void toGetCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String codeUrl = UrlConstants.GET_CODE;
        String type = request.getParameter("type");
        String wap_url = (String) UrlConstants.getWapMap().get(type);
        String toWap = URLEncoder.encode(PropUtils.getConfigMessage("WEBSITEURL") + wap_url);
        String httpUrl = codeUrl.replace("APPID", Constants.getAppId()).replace("REDIRECT_URI", toWap).replace("STATE", "wechat_token");
        System.out.println("httpUrl:" + httpUrl);
        response.sendRedirect(httpUrl);
    }

    @RequestMapping(value="/gateway/webPage", method = RequestMethod.GET)
    @ResponseBody
    public void webPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String code = request.getParameter("code");
        System.out.print("hhhhhhh");
    }

    @RequestMapping(value="/gateway/index", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model){
        model.setViewName("/index");
        return model;
    }

    @RequestMapping(value="/create", method = RequestMethod.GET)
    public void printUrl(){
        wechatService.printUrl();
    }
}
