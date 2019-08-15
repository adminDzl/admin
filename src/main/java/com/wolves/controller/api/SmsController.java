package com.wolves.controller.api;

import com.google.gson.JsonObject;
import com.wolves.common.Constants;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 个人中心相关接口
 * @author xulu
 * @date 2019/2/26
 * @link https://github.com/xulu163
 */
@RestController
@RequestMapping(value = "/api/sms")
public class SmsController {

    @Resource(name = "smsService")
    SmsService smsService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 发送验证码短信
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Result send(@RequestBody JsonObject obj){
        Result result = new Result();
        Object telObj = obj.get("tel");
        if(null == telObj){
            result.setMsg("请输入手机号");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String tel = telObj.toString();
        String code = (Math.random()*9+1)*100000 + "";
        String msg = ("您的验证码是：code【opark】").replaceAll("code", code);

        return smsService.sendSms(tel, Constants.SMS_TYPE, code, msg);
    }
}
