package com.wolves.controller.api;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.jpay.alipay.AliPayApi;
import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;
import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.jpay.weixin.api.WxPayApiConfigKit;
import com.wolves.common.PayTypeEnum;
import com.wolves.dto.pay.WxResultDTO;
import com.wolves.entity.app.PayOrder;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.pay.WechatPayService;
import com.wolves.util.PageData;
import com.wolves.util.PublicUtil;
import com.wolves.util.StringUtils;
import com.wolves.util.UuidUtil;
import com.wolves.util.wxUtil.WxPayApiConfigKitUtil;
import com.wolves.util.wxUtil.WxPayApiConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 个人中心相关接口
 *
 * @author xulu
 * @date 2019/2/26
 * @link https://github.com/xulu163
 */
@RestController
@RequestMapping(value = "/api/pay")
public class PayController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String notify_url;
    /**
     * 通知端口
     */
    private static String PORT = "8080";
    /* private static String REQ_PORT="http:127.0.0.1:";*/
    /**
     * 通知地址
     */
    private static String REQ_PORT = "http://47.101.208.47:";
    /*private static String CER_PATH="F:\\tuyuan_project\\admin\\src\\main\\resources\\apiclient_cert.p12";*/
    /**
     * 微信退款要用到的证书
     */
    private static String CER_PATH = "C:\\apiclient_cert.p12";
    @Resource(name = "wechatPayService")
    private WechatPayService wechatPayService;
    /**
     * 本地IP
     */
    String LOCAL_NET_IP = "0:0:0:0:0:0:0:1";
    private static final String aliPay_appId = "2019041663927066";

//    private static final String aliPay_appId = "2019051364508381";
    private static final String aliPay_publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyp//dRhxqswEBmWxMEC65ZyNu1sP02txKAvgZlfPL/Qgn15Av/nZt32IcBR5XfIgo4qj28zdMlZlxqR/00J72mhoXqMAtqlplsLDlvDY0PgAzm8VAsu3+g3q+JJHdAP4iL3ykw+vLsIBQ51JeXLxMApyx0fhyeIZJJ9LdCucID+qIQEiwjk+3locHd/PYTrnSJ3C+OpAS920hMuxWeeGiABPaNO+HX+YNZ+eoemhZTAPAeeTil5lMBJdYEQ5lZywRuVMclBtTxbm0sF5Wf21PmV0/Gmtpn5rfEb/Ptm3cONt4BS+kUWnMVoetJOmkMxKW9hEzjspDnavceYQ3X/9qQIDAQAB";
    private static final String aliPay_privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDKn/91GHGqzAQGZbEwQLrlnI27Ww/Ta3EoC+BmV88v9CCfXkC/+dm3fYhwFHld8iCjiqPbzN0yVmXGpH/TQnvaaGheowC2qWmWwsOW8NjQ+ADObxUCy7f6Der4kkd0A/iIvfKTD68uwgFDnUl5cvEwCnLHR+HJ4hkkn0t0K5wgP6ohASLCOT7eWhwd389hOudIncL46kBL3bSEy7FZ54aIAE9o074df5g1n56h6aFlMA8B55OKXmUwEl1gRDmVnLBG5UxyUG1PFubSwXlZ/bU+ZXT8aa2mfmt8Rv8+2bdw423gFL6RRacxWh60k6aQzEpb2ETOOykOdq9x5hDdf/2pAgMBAAECggEBAJEsf0/7Gn1/vKaEt6XVytJ2D0ocHUOh6CoEEFvjL+uwoqrsUcH6FhYUV+vNj4NS1jW2QF5b8mN9aWAgPgEz0h8SzSx45yr4SFGe65m6p+1YsuM0zT36ja8Xx7EexRkHO0tlpGXfR+ldUKCMEbd1Nf7wjXngM5J8bv1BMENkd3DWREGGov6OyjdskGoe3h9geB4ueP2AaXJburBIh/3XiLbMpKJYbMQu9Q/2V+3FJNVhFuSATM5JwQWlPlcQYfaeGqSCSXaUhXh1WTd08yJ6LEk/AQrcG3j8nwZrBffgvrbD1w3pI/KWLXFS8o66ufbtzQ+wNKJWYkG/GPqVAdtVHx0CgYEA/MB5lrEKNwufMVciYqThg8F6gSHLHQFSbLSDwZcq39TTt2DLgTfAKyMec4cBn+ZFtW+riXAmEQSHw+Ata8SEu7Xh3Ru/KrVGXb5aivisHCZ1/BwD9HoHipPaHBj6bnI0WhJXw9o8CHAbOqK8OJD7v47yexY1q3sPFslK8msvX7cCgYEAzTqceByUYo9jTGWIwFonqHtJiPIg+FMzb+sstozu9Z8kOP9D1r6zq470r8H+g5QMQ/Yss4P7GzSCL+zzhK7qGM7mV9GeqpqVlZ7UAxs/YlNZnbumXcDpZJRjM8+tXPZ97wrAM8hpmlozQF/SAa+KTF3VN62GtLCMnfnhIEQCzZ8CgYEAvbkVC+J00oDszDKl4vn1NduDw27Eck9Nub9+r1BzpHSzP5Fu8N4SZ88CyqFqvMYzSVA7qPz/9gI6jvO15JEtOWH72uc7BinwWenLOUoUl0Tfh5DX/ymig4kcTb8KLOGZHotqSd+E9KiCJ++D7coWzuHUbkRroP7MgsQyd+mxM90CgYEAi3lIu0xY/76I8nOSg38BsrvO1tYO2BTbQTVnlsyi3aSA3oeEmB3UR0SJQCUsDkoAJ/PWn02TywJdc6qqOMMS7zc1Tft0xGb4P83IMjBDXFRe9BaCUvb25tb9sEl0fVg7b8gzpT2Pn0mryRUVyPV+nLWsYQF6KbRObFJU0DPIX+sCgYBUdnzhXw8NeHv57bZi8uKaH5mK/OH8rcyYY5P2cvcw4ToKoYr2j+o1Z3DYxMGcG+nWEoc1FXq1out4MM7X3rcXPHHeFzGTrF4zjdOSFiZHcnGla/wKROD4cpUVsDGTbkhmLYc+9cWQM7H27qwO8gJc4687uYrn63osGgl6ZUJ0qg==";
    private static final String aliPay_publicKey1 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhKaG/Rb3CGCvQKMDsxo1m6g7Ztv907OrbL9bWkV5A49IphWmJDzkkmIilov0LyXpwHUo3rq+3/FLfdWvjJan7StnZGQK43oLQUy74VB3ARDK98EPZkiKrF2zHF4aRa+6JQqCVPgA5ufI5khybnHKVugHx8bgKtWbYvGIIilhRvYTngXxbRuK6N+XzxP+c6toU7Ir3fddbl5IG3zHUpm4voXbHdiUiubZvX6KaCqDQEiHvX9zPq2Ebm/+QAFPfpAzfTbI5qaIwS/vpsNPGfwsp/Cdipz90adaGQJRQeJJNkYYAE7C33TjVlJ+O72KDKy75u1Anz/abYS5DYvspYUYFQIDAQAB";

//    private static final String aliPay_publicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlKZy4AoHxZEwbHW/hpAo30CrI9pkBB1yHAlgNkjnW3N4/ZhfaC8AxhWux1flqxBGMZ+q0QthSPBRk9PE+hY9vk1eONYyTk5pzr72P8rwUSfXeicBjd/EXiUuCJDHWeHQbuI/C/MA22VdF0q3WZcPOFZ1tnmucuvlGhW6YOKV6eOpgQmWuqlj8Evv8i00GdzVCwDd+qyLTFt2sB1oKuIhDcT9neJ5MAPZcwZcEA2lVCx5qg627GXr6Aza4VmpBILPvQqUjLTYgCaS1496Q8KWAXRSlBrb6vTxIWZefBbBbVnxrPGriOFYPj0XSMgkGVlKmglhebOb7a3sUvSVnps5MwIDAQAB";
//    private static final String aliPay_privateKey ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUpnLgCgfFkTBsdb+GkCjfQKsj2mQEHXIcCWA2SOdbc3j9mF9oLwDGFa7HV+WrEEYxn6rRC2FI8FGT08T6Fj2+TV441jJOTmnOvvY/yvBRJ9d6JwGN38ReJS4IkMdZ4dBu4j8L8wDbZV0XSrdZlw84VnW2ea5y6+UaFbpg4pXp46mBCZa6qWPwS+/yLTQZ3NULAN36rItMW3awHWgq4iENxP2d4nkwA9lzBlwQDaVULHmqDrbsZevoDNrhWakEgs+9CpSMtNiAJpLXj3pDwpYBdFKUGtvq9PEhZl58FsFtWfGs8auI4Vg+PRdIyCQZWUqaCWF5s5vtrexS9JWemzkzAgMBAAECggEAVZVPZjXgK5tf6dIRJwQww/iTj9zY4JWjcQjk39wxd7GP41Gq/GyzYVhyfEra7BY+k6t9yUVoCXpzPU0bh3Dus3YP3WFVLUhwGpxPYoMQwWjS4n06o9hFUn7NdhkE7XbZbj3MVrDduXMumY5m6anTFXiR5TFBguDV0qLX35gU/8cxvrSq7hmn+IyXTdmDX3QPEvhl6GTfnQodXQJFEYk+63rcInvTPE7GS8riK2KeJCNArrjqxmfqYD7dt8Xpr2y4+oKT4fPEDZuAqGtHErleyineY38zCOnoFktQb5BLRFXVUIsR2472whewsjtu3+GukM7YZF3WnJR38oKIPs9sQQKBgQDxXzCVrldPkw9haLwJ9/x1/8iYeZGF1FIiyTk/mYS5BeUZx58V+dnX0F1uzsV/CtHWzYJudzo63mv1gN4mHmyuGEI7oa9PqWyQ4o69nf1sJCTcyF/HJIZuRop8bkk3MbHEBvATdGTSIaYHiC0YURqM4ZB96TOoKWD1ruzA4jWqOwKBgQCdqLYC2P9K0RaQQdgs0ACtJ+HGN9bSU5PM8l+ZP9MFz4W81FYu3CiF8lTPd+gmPZ7idh2L9NcfMVnqMI7TANsn+ON/PAGPBGCECJ/owvfuDcaYeEuh13fr6GDKSbhoJHkU09dLqqPANWfLRKl58XAd9wR7jAG8mrr0Ctex/TvFaQKBgQCW3budYxo5p56ucOtPg8wt6RF1lE7MCnjIO2PoB8m4aCHZqjcaYk+yIA+2SdGR5a5hB5Tl/OnNj+aEzeaMhunBK3KCmT3dmfU83g8AH/cdl73erfOHoqHP3ZGD0t86e3o0H24BivfVi6id/pTJJTBmLDrHKHLcQfjTt2FN8lY+rwKBgCzCbxoT3TayccN2zha63tZKElEiHT0NOVLgJL/vXMuzhPZ9GhKnsmGE0QstsZzZtbf5bJ4FXuWeLzbLnmDNR2rrwDqpIJAn3/bcM/nzEExnYNB8poxGDefNy1zi3RXD9RQyqFb1FRRGlTciQTM7lmcLcBxbPUr8asNKKL6BdGyxAoGAZjyt4aR7mVpKThvTtgPgdvKdZmnCYFwBjW/Z4/LRMtxujwdge4JOH3IXM2ytlg/aZ9Th+fE+zRgXXUMNNMMXS08gneAS3EAfrgDmdFwTGooufhYSLju/FiOEitzXan0HnYalzXOKn8HP5R7FYV0bONtQ4urJSA+aaTNtPY1EMmo=";
//    private static final String aliPay_publicKey1="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgc+Que7FtCXnMhUs2vxPNQ9naPEKCyUysEZoGgwNw40PBmxEiWG4ok1JW26/A8D8e4hxwJdgLDLU6jg0jpSx/ZVr8nj9dZKBNWaY/679oMwo15LFPFmoRtWujfxAjVn1Zzrf3ox2z73pk8TcC6D9G/sKWcoBwp3wAEcYaYlsRBT/poW9Ptfdu3v/l79ae1TKZxH54t4FEyfMc0VG1+GBp2c9NTzku5omBsTLOBbYphJz1jNynV0cCwCHYl2/hsKrKtVEtdm0dBub20KWF+a+9YtHryED/Vi1YV81nvoTdRdrUvKRqpiSkXUMeXrLFQzCtLLYu9821EqlcsDfXixt7QIDAQAB";

    /**
     * 微信预支付
     *
     * @param token
     * @param payOrder
     * @return
     */
    @RequestMapping(value = "/wxprePay", method = RequestMethod.POST)
    public Result wxprePay(@RequestHeader("Authorization") String token, @RequestBody PayOrder payOrder, HttpServletRequest request) {
        logger.info("-----------------------------支付预支付开始--------------------------------");
        Result result = new Result();
        this.initWxPayApiConfig();
        String trade_no = UuidUtil.get32UUID();
        logger.info("trade_no=" + trade_no);
        String reqIp = PublicUtil.getIpAddr(request);
        Map<String, String> params = WxPayApiConfigKit.getWxPayApiConfig().setDetail(PayTypeEnum.queryValueByKey(payOrder.getPayType().toString()))
                .setBody(PayTypeEnum.queryValueByKey(payOrder.getPayType().toString())).setSpbillCreateIp(reqIp).setTotalFee(payOrder.getPayAmount() + "")
                .setTradeType(WxPayApi.TradeType.APP).setNotifyUrl(notify_url)
                .setOutTradeNo(trade_no).build();
        payOrder.setPayorderId(trade_no);
        logger.info("WxPay ServiceImpl unifiedorder request xml:{}", params);
        String responseXml = WxPayApi.pushOrder(false, params);
        logger.info("WxPay ServiceImpl unifiedorder response xml:{}", responseXml);
        Map<String, String> responseMap = prepayIdCreateSignByApp(responseXml);
        logger.info("WxPay ServiceImpl unifiedorder response:{}", responseMap);
        // WxResultDTO wxResultDTO = wechatPayService.pay(request, token, orderDTO);
        WxResultDTO wxResultDTO = new WxResultDTO();
        if (null != responseMap) {
            wxResultDTO.setNonceStr(responseMap.get("noncestr"));
            wxResultDTO.setPackages(responseMap.get("package"));
            wxResultDTO.setPaySign(responseMap.get("sign"));
            wxResultDTO.setPrepayId(responseMap.get("prepayid"));
            wxResultDTO.setTimeStamp(responseMap.get("timestamp"));
            wxResultDTO.setSignType("MD5");
            request.setAttribute("channel_type", "WX");
            wechatPayService.insertOrder(request, token, payOrder);

        }
        //返回结果
        logger.info("wxResultDTO :{}", wxResultDTO);
        result.setData(wxResultDTO);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("请求成功");
        return result;
    }


    /**
     * 微信支付回调接口
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/wxPayNotify", method = RequestMethod.POST)
    public Result wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        logger.info("-----------------------------支付回调开始--------------------------------");
        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
        logger.info("微信支付通知：{}");
        String xmlMsg = HttpKit.readData(request);
        Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
        String result_code = params.get("result_code");
        logger.info("xmlMsg：{}" + xmlMsg);
        String attach = params.get("attach");
        if (PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())) {
            if (("SUCCESS").equals(result_code)) {
                // 更新订单信息
                logger.info("更新订单信息:" + attach);
                PageData pd = new PageData();
                pd.put("PAY_STATUS", "1");
                pd.put("PAYORDER_ID",params.get("trade_no"));
                wechatPayService.updateOrder(pd);
                // 发送通知等
                Map<String, String> xml = new HashMap<String, String>();
                xml.put("return_code", "SUCCESS");
                xml.put("return_msg", "OK");
                return new Result(PaymentKit.toXml(xml));
            }else {
                PageData pd = new PageData();
                pd.put("PAY_STATUS", "2");
                pd.put("PAYORDER_ID",params.get("trade_no"));
                wechatPayService.updateOrder(pd);
            }
        }
        return null;
    }

    /**
     * 支付宝支付
     *
     * @param token
     * @param payOrder
     * @return
     */
    @RequestMapping(value = "/aliPay", method = RequestMethod.POST)
    public Result aliPay(@RequestHeader("Authorization") String token, @RequestBody PayOrder payOrder, HttpServletRequest requests) {
        //实例化客户端
        AliPayApiConfigKit.putApiConfig(this.getApiConfig());
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(PayTypeEnum.queryValueByKey(payOrder.getPayType().toString()));
        String trade_no = UuidUtil.get32UUID();
        model.setSubject("App支付ALI");
        //model.setOutTradeNo(outtradeno);
        model.setOutTradeNo(trade_no);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(payOrder.getPayAmount() + "");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(REQ_PORT + PORT + "/api/pay/aliPayNotify");
        String orderInfo = "";
        payOrder.setPayorderId(trade_no);
        try {
            orderInfo = AliPayApi
                    .appPayToResponse(model, REQ_PORT + PORT + "/api/pay/aliPayNotify").getBody();
            logger.info("orderInfo=" + orderInfo);//orderInfo 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            logger.error(e.getMessage());
        }
        Result result = new Result();
        if (StringUtils.isNotBlank(orderInfo)) {
            requests.setAttribute("channel_type", "ALI");
            wechatPayService.insertOrder(requests, token, payOrder);
            result.setData(orderInfo);
            result.setResult(ResultCode.SUCCESS);
            result.setMsg("请求成功");
            return result;
        }
        result.setResult(ResultCode.FAIL);
        result.setMsg("下单失败");
        return result;
    }

    /**
     * 支付宝回调接口
     *
     * @param request
     */
    @RequestMapping(value = "/aliPayNotify", method = RequestMethod.POST)
    public Result aliPayNotify(HttpServletRequest request) throws AlipayApiException {
        //获取支付宝POST过来反馈信息
        HashMap<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        logger.info("AliPay 通知报文", requestParams);
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
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
        boolean flag = AlipaySignature.rsaCheckV1(params, aliPay_publicKey, "utf-8", "RSA2");
        if (flag) {
            // 验证成功
            logger.info("AliPay ServiceImpl notify_url 验证成功succcess:{}" + params);
            // 更新订单信息
            PageData pd = new PageData();
            pd.put("PAY_STATUS", "1");
            pd.put("PAYORDER_ID",params.get("trade_no"));
            wechatPayService.updateOrder(pd);
            return new Result("success");
        } else {
            logger.info("AliPay ServiceImpl notify_url 验证失败:{}" + params);
            PageData pd = new PageData();
            pd.put("PAY_STATUS", "2");
            pd.put("PAYORDER_ID",params.get("trade_no"));
            wechatPayService.updateOrder(pd);
            return new Result("failure");
        }
    }

    /**
     * 获取支付宝配置信息
     *
     * @return
     */
    public AliPayApiConfig getApiConfig() {
        return AliPayApiConfig.New().setAppId(aliPay_appId).setAlipayPublicKey(aliPay_publicKey1)
                .setCharset("utf-8").setPrivateKey(aliPay_privateKey).setServiceUrl("https://openapi.alipay.com/gateway.do")
                .setSignType("RSA2").build();
    }

    /**
     * 初始化微信配置信息
     */
    private void initWxPayApiConfig() {
        // notify_url = WxConfigUtil.notify_url;
        notify_url = REQ_PORT + PORT + "/api/pay/wxPayNotify";
        WxPayApiConfig wxPayApiConfig = WxPayApiConfig.New().setAppId("wx5f7abea70c318ab0")
                .setMchId("1548223921").setPaternerKey("quLjLa0LhFnbqJfY9gfySJKX9oLEtGOs")
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
        WxPayApiConfigKit.putApiConfig(wxPayApiConfig);
        logger.info("===================wxPayApiConfig:{}" + wxPayApiConfig);
    }

    /**
     * 预付订单再次签名
     *
     * @param responseXml
     * @return <Map<String, String>>
     */
    private Map<String, String> prepayIdCreateSignByApp(String responseXml) {
        Map<String, String> responseMap = new HashMap<String, String>();
        logger.info("responseMap=" + responseMap);
        responseMap = PaymentKit.xmlToMap(responseXml);
        if (PaymentKit.codeIsOK(responseMap.get("result_code"))) {
            String prepayId = responseMap.get("prepay_id");
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", WxPayApiConfigKit.getWxPayApiConfig().getAppId());
            packageParams.put("partnerid", WxPayApiConfigKit.getWxPayApiConfig().getMchId());
            packageParams.put("prepayid", prepayId);
            packageParams.put("package", "Sign=WXPay");
            packageParams.put("noncestr", System.currentTimeMillis() + "");
            packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
            String packageSign = PaymentKit.createSign(packageParams,
                    WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey());
            packageParams.put("sign", packageSign);
            logger.info("WxPay ServiceImpl prepayId CreateSignByApp:{}", packageParams);
            return packageParams;
        } else {
            logger.error(responseMap.get("return_msg"));
        }
        return responseMap;
    }


    /**
     * 退款接口
     *
     * @param requests
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public Result refund(@RequestHeader("Authorization") String token, @RequestBody PayOrder payOrder, HttpServletRequest requests) throws Exception {
        //获取支付宝POST过来反馈信息
        String yardappointId = payOrder.getYardappointId();
        Result result = new Result();
        if (StringUtils.isEmpty(yardappointId)) {
            result.setResult(ResultCode.FAIL);
            result.setMsg("退款失败");
            return result;
        }
        payOrder = wechatPayService.selectPayOrderByYardappointId(requests, token, payOrder);
        if (null == payOrder || StringUtils.isEmpty(payOrder.getChannelType())) {
            result.setResult(ResultCode.FAIL);
            result.setMsg("退款失败");
            return result;
        }
        //支付宝退款
        if ("ALI".equals(payOrder.getChannelType())) {

            //实例化客户端
            AliPayApiConfigKit.putApiConfig(this.getApiConfig());
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setOutTradeNo(payOrder.getPayorderId());
            model.setRefundAmount(payOrder.getPayAmount() + "");
            model.setRefundReason("商户退款");
            String refundInfo = "";
            try {
                refundInfo = AliPayApi
                        .tradeRefundToResponse(model).getBody();
                logger.info("refundInfo=" + refundInfo);//refundInfo 可以直接给客户端请求，无需再做处理。
            } catch (AlipayApiException e) {
                logger.error(e.getMessage());
            }
            if (StringUtils.isNotBlank(refundInfo)) {
                logger.info("退款成功");
                logger.info("ALI  refund response xml:{}", refundInfo);
                result.setResult(ResultCode.SUCCESS);
                result.setData(refundInfo);
                result.setMsg("退款成功");
                return result;
            } else {
                logger.info("退款失败");
                result.setResult(ResultCode.FAIL);
                result.setMsg("退款失败");
                return result;
            }
        }
        //微信退款
        if ("WX".equals(payOrder.getChannelType())) {
            WxPayApiConfigUtil wxPayApiConfig = WxPayApiConfigUtil.New().setAppId("wx5f7abea70c318ab0")
                    .setMchId("1548223921").setPaternerKey("quLjLa0LhFnbqJfY9gfySJKX9oLEtGOs")
                    .setPayModel(WxPayApiConfigUtil.PayModel.BUSINESSMODEL);
            WxPayApiConfigKitUtil.putApiConfig(wxPayApiConfig);
            logger.info("===================wxPayApiConfig:{}" + wxPayApiConfig);
            notify_url = REQ_PORT + PORT + "/api/pay/wxPayNotify";
            String out_refund_no = UuidUtil.get32UUID();
            Map<String, String> wxparams = WxPayApiConfigKitUtil.getWxPayApiConfigUtil()
                    .setTotalFee(payOrder.getPayAmount() + "")
                    .setOutTradeNo(payOrder.getPayorderId()).setOutRefundNo(out_refund_no)
                    .setRefundFee(payOrder.getPayAmount() + "")
                    .setTradeType(WxPayApi.TradeType.APP).build();
            logger.info("WxPay ServiceImpl refund request xml:{}", wxparams);
            String responseXml = WxPayApi.orderRefund(false, wxparams, CER_PATH, "1548223921");
            logger.info("WxPay ServiceImpl refund responseXml xml:{}", responseXml);
            Map<String, String> responseMap = new HashMap<String, String>();
            responseMap = PaymentKit.xmlToMap(responseXml);
            if (PaymentKit.codeIsOK(responseMap.get("result_code"))) {
                logger.info("退款成功");
                result.setData(responseMap);
                result.setResult(ResultCode.SUCCESS);
                result.setMsg("退款成功");
                return result;
            } else {
                logger.info("退款失败");
                result.setResult(ResultCode.FAIL);
                result.setMsg("退款失败");
                return result;
            }

        }
        return null;
    }

}
