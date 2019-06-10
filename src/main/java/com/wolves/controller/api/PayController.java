package com.wolves.controller.api;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.wolves.dto.OrderDTO;
import com.wolves.dto.pay.WxResultDTO;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.pay.WechatPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.alipay.api.AlipayConstants.CHARSET;

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

    private static final String aliPay_appId = "2019051364508381";
    private static final String aliPay_publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoeYlI5B9MRmA+Ts+qKM" +
            "sR8EZ9hcQ6UdXbyK2GUExajW01kDXPoZVNsBMAVOyfr20V8xxTaYfYc3441TvkVqrJpNBeP7eohXIoX/F0jBjVvwt6VgsoxoSnGQfy" +
            "6IVBQrhAI04FTnpO4ah/0bxW3oDDlshcjm+5WXoPTEY0T2f/UQ/MZkWXePy+V+d0g83xfclP4yYxw9QS2CQO8U2T55KFS6kiWqvxNp" +
            "6SSz/X20iHhPtm1m+R+q8Pn0AFt1O6F9HNjI0SZyU0TziEvlvxyqaS0A8Bl4/SB/gajfjbH4vyBXTevWVj0VC8lSAa9a2ysCgCQl73" +
            "ZEtdg5q7dzdsy9i5QIDAQAB";
    private static final String aliPay_privateKey = "MIIEowIBAAKCAQEAoeYlI5B9MRmA+Ts+qKMsR8EZ9hcQ6UdXbyK2GUExajW01k" +
            "DXPoZVNsBMAVOyfr20V8xxTaYfYc3441TvkVqrJpNBeP7eohXIoX/F0jBjVvwt6VgsoxoSnGQfy6IVBQrhAI04FTnpO4ah/0bxW3oD" +
            "Dlshcjm+5WXoPTEY0T2f/UQ/MZkWXePy+V+d0g83xfclP4yYxw9QS2CQO8U2T55KFS6kiWqvxNp6SSz/X20iHhPtm1m+R+q8Pn0AFt" +
            "1O6F9HNjI0SZyU0TziEvlvxyqaS0A8Bl4/SB/gajfjbH4vyBXTevWVj0VC8lSAa9a2ysCgCQl73ZEtdg5q7dzdsy9i5QIDAQABAoIB" +
            "AFv2CF59Mj9zhykKl/reRMa1TvPUAk0fsvBzvQ/3UgRMYV3aEul3EGw7KnEOg7MuOoQsoqdCl0zM3HT0x+e+or0Uo/oAe6GB0ia6Fz" +
            "nfNm4wbDvBVAmJQ80FqnHWrS3iEhBoJtUXwf2H6fjA6VPWqGm9JfBmEKLpEdPAUaJ46KnI9PxVAHok0rLsJ1c1ykkSKTj2sp2aqqeT" +
            "VBUkTZqDPLws/UIHXpgyAWy8hZURIxkDrMK1n9EkOnU8bDCxKB6LRMnxgmpM1iy2idcyld6mprDqLAVRdKQr7bObnsN4YejtT0fr0a" +
            "MmJxlJ/H6gLg2C103a+XJZDsQy6J/xFz14rvkCgYEAzCccvgedUbBsA25NKrDythByTFcWmguaTG7iJ82xkcWN4UMFCAb9/WzUko7x" +
            "/u926EOCD2yYm5NafUOMuMmKCBPGwlsaJcV22cMK8YQrFcBH+UJnOCuhbbVwUfYCQA1wS1X5oICkPLLicRWSy4P7f8TCE0+jF4wanf" +
            "ymY+NI9UsCgYEAywPs3AyvGSpCy7qiFzZ2q+1KjMaVlhdMDPlu1OolOdI1boteQ+DKuGxnQ2QDbEha724+royC1Snl1UWgmcDmB+eN" +
            "IC4jzd4zxTyTuW+ZI4a9zPQl8T7KxNjjVardVOEmIPDnl0Nc1Mr8AyWZgSPpY6fa0CAaLDbFC65tGRBWWo8CgYAgelBuQtPaPRDR8u" +
            "g/1GqoYDFux6QG2bWwVyuPXX/USvNpVLomLsaVXIzsy+Tfw8TM6oB0Z7hXd7e7mdV3SEi3RD4oiUm9nI5t0kOZbIdWLILGurOlEfva" +
            "cjTuNQ7ilcC5m3HdANof9e1kRCRYUCTgjLK7U74EbvuFCr6FwZDHxQKBgGws7kQeuhp1q1v1WbN2wOtBqKy0kDaGuW5cZtMq5KUXvo" +
            "Ll3lfa6wo0MR32p+BF+baFDv9hPcGp7p6i2D21TJ0/o7JXA5sJ4Jojei+gKICGppIUMjeHFvKnAxO91OwKgI+i7jRqn4d/laNzoITw" +
            "hsckRPMLeAbBNGPSEK3/R5Q/AoGBALSMd4wFFna2z7OiRDx1I8++xgGEnQvJRYSiiwge4o/dg4mT7iXZJskn2yopKQVTAlNhtjr1R4" +
            "B/SzCWhh4zLKpaYiMmXTMrYc6ka5YmYpgxlD0UwiNamuGy/2QGgJgymUIdJdl+VeLhBj0uT3onGrqJ6OV775xIT+SxC3aGSApT";

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

    /**
     * 支付宝支付
     * @param token
     * @param orderDTO
     * @return
     */
    @RequestMapping(value = "/aliPay", method = RequestMethod.POST)
    public Result aliPay(@RequestHeader("Authorization") String token, @RequestBody OrderDTO orderDTO){
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", aliPay_appId, aliPay_privateKey, "json", CHARSET, aliPay_publicKey, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        //model.setOutTradeNo(outtradeno);
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("商户外网可以访问的异步地址");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 支付宝回调接口
     * @param request
     */
    @RequestMapping(value = "/aliPayNotify", method = RequestMethod.POST, produces="text/html;charset=utf-8")
    public void aliPayNotify(HttpServletRequest request) throws AlipayApiException{
        //获取支付宝POST过来反馈信息
        HashMap<String, String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag = AlipaySignature.rsaCheckV1(params, aliPay_publicKey, "utf-8","RSA2");

    }
}
