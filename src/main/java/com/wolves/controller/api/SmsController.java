package com.wolves.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人中心相关接口
 * @author xulu
 * @date 2019/2/26
 * @link https://github.com/xulu163
 */
@RestController
@RequestMapping(value = "/api/sms")
public class SmsController {

    /**
     * 发送短信
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void send(){

    }

    /**
     * 登出,清空token
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(){

    }
}
