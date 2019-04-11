package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.wolves.common.StatusEnum;
import com.wolves.dto.OrderDTO;
import com.wolves.dto.pay.WxResultDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.pay.WechatPayService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
import com.wolves.util.Tools;
import com.wolves.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 个人中心相关接口
 * @author xulu
 * @date 2019/2/26
 * @link https://github.com/xulu163
 */
@RestController
@RequestMapping(value = "/api/pay")
public class PayController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="wechatPayService")
    private WechatPayService wechatPayService;

    /**
     * 微信预支付
     * @param token
     * @param orderDTO
     * @return
     */
    @RequestMapping(value = "/wxprePay", method = RequestMethod.POST)
    public Result wxprePay(@RequestHeader("Authorization") String token, @RequestBody OrderDTO orderDTO, HttpServletRequest request){
        logger.info("-----------------------------支付预支付开始--------------------------------");
        Result result = new Result();

        WxResultDTO wxResultDTO = wechatPayService.pay(request, token, orderDTO);
        //返回结果
        result.setData(wxResultDTO);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("请求成功");
        return result;
    }


    /**
     * 微信支付回调接口
     * @param request
     * @param response
     */
    @RequestMapping(value = "/wxPayNotify", method = RequestMethod.POST, produces="text/html;charset=utf-8")
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response){
        logger.info("-----------------------------支付回调开始--------------------------------");

    }
}
