package com.wolves.service.pay;

import com.alibaba.fastjson.JSONObject;
import com.wolves.common.WxPayStatusEnum;
import com.wolves.entity.app.PayOrder;
import com.wolves.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author xulu
 * @date 2019/2/28
 * @link https://github.com/xulu163
 */
public class WechatPayService implements PayService {
    /** 本地IP */
    String LOCAL_NET_IP = "0:0:0:0:0:0:0:1";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void pay() {

    }

    @Override
    public void reback() {

    }

    /**
     * 统一下单
     * @param request
     * @param token
     * @param payOrder
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void unifiedOrder(HttpServletRequest request, String token, PayOrder payOrder){
        try {
            PayOrder order = new PayOrder();
            SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
            //APPID
            parameters.put("appid", WxConfigUtil.AppId);
            //商户号
            parameters.put("mch_id", WxConfigUtil.MchId);
            parameters.put("device_info", "WEB");
            String nonce_str = PayCommonUtil.createNoncestr();

            //随机字符串
            parameters.put("nonce_str", nonce_str);
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            // 签名
            parameters.put("sign", sign);
            //默认MD5，非必传
            parameters.put("sign_type", "MD5");
            //商品描述
            parameters.put("body", WxConfigUtil.body);
            String trade_no = UuidUtil.get32UUID();
            order.setPayorderId(trade_no);
            //商户订单号
            parameters.put("out_trade_no", trade_no);
            //标价金额 接口单位为分，账单则为元，如10分，对应账单为0.1元
            parameters.put("total_fee", payOrder.getPayAmount());

            logger.warn("unifiedOrder ======> ip" + request.getHeader("x-forwarded-for"));
            String reqIp = PublicUtil.getIpAddr(request);
            if (!LOCAL_NET_IP.equals(reqIp)) {
                //终端IP
                parameters.put("spbill_create_ip", reqIp);
            }
            //通知地址
            parameters.put("notify_url", WxConfigUtil.notify_url);
            //交易类型
            parameters.put("trade_type", "APP");

            String requestXML = PayCommonUtil.getRequestXml(parameters);
            Map map = PayCommonUtil.httpsRequest(WxConfigUtil.PrepayUrl, "POST", requestXML);
            if (null != map && map.size() > 0) {

                String prepay_id = (String)map.get("prepay_id");
                if (StringUtils.isEmpty(prepay_id)) {
                    //throw new OureaException(OureaException.Type.PAY_WX_PRE_SIGN_ERROR, OureaException.Type.PAY_WX_PRE_SIGN_ERROR.getError()+"~");
                }
                long timeStamp = System.currentTimeMillis();
//                payRespDTO.setTimeStamp(timeStamp+"");

                String packages = "prepay_id=" + prepay_id;
//                payRespDTO.setPackages(packages);
//                payRespDTO.setPrepayId(prepay_id);

                parameters = new TreeMap<Object,Object>();
                //APPID
                parameters.put("appId", WxConfigUtil.AppId);
                //随机字符串
                parameters.put("nonceStr", nonce_str);
                //随机字符串
                parameters.put("package", packages);
                //随机字符串
                parameters.put("signType", "MD5");
                //随机字符串
                parameters.put("timeStamp", timeStamp);
                String paySign = PayCommonUtil.createSign("UTF-8", parameters);
//                payRespDTO.setPaySign(paySign);
//                payRespDTO.setSignType(WX_DEFAULT_SIGN_TYPE);

                //充值记录
                order.setUserId(payOrder.getUserId());
                order.setPayType(payOrder.getPayType());
                order.setPayAmount(payOrder.getPayAmount());
                order.setPayStatus(Integer.valueOf(WxPayStatusEnum.INIT.getKey()));
                order.setPayTime(new Date());

                //保存

//                return payRespDTO;
            }
//            throw new OureaException(OureaException.Type.PAY_WX_PRE_ORDER_ERROR, OureaException.Type.PAY_WX_PRE_ORDER_ERROR.getError()+"~");
        } catch (Exception e) {
            logger.error("下单异常==>" + e.getMessage(),e);
//            if (e instanceof OureaException){
//                OureaException oe = (OureaException)e;
//                throw oe;
//            }
//            throw new OureaException(OureaException.Type.PAY_WX_PRE_ORDER_ERROR, OureaException.Type.PAY_WX_PRE_ORDER_ERROR.getError());
        }
    }

    /**
     * 微信支付回调
     * @see /https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7
     * @param request
     * @param response
     */
    @Transactional
    public void wxPayNotify(HttpServletRequest request,HttpServletResponse response) {
        String inputLine = "";
        String notityXml = "";
        try {
            //读取参数
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            logger.info("微信回调内容信息：" + notityXml);

            //解析xml成map
            Map<String, String> m = new HashMap<String, String>();
            m = XMLUtil.doXMLParse(notityXml);


            //过滤空 设置 TreeMap
            SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = m.get(parameter);
                String v = "";
                if(null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            // 账号信息
            String key = WxConfigUtil.API_KEY; // key

//            logger.debug("回调响应的结果==>" + ReflectionToStringBuilder.toString(packageParams));
            //判断签名是否正确
            if(PayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {

                //------------------------------
                //处理业务开始
                //------------------------------
                String resXml = "";
                if("SUCCESS".equals((String)packageParams.get("result_code"))){
                    // 这里是支付成功
                    //////////执行自己的业务逻辑////////////////
                    String mch_id = (String)packageParams.get("mch_id");
                    String out_trade_no = (String)packageParams.get("out_trade_no");
                    String appid = (String)packageParams.get("appid");
                    String device_info = (String)packageParams.get("device_info");
                    String transaction_id = (String)packageParams.get("transaction_id");
                    String time_end = (String)packageParams.get("time_end");
                    String total_fee = (String)packageParams.get("total_fee");//需要做处理


                    //微信支付回调业务逻辑
                    BigDecimal orderAmount = BigDecimal.ZERO;//new BigDecimal(total_fee).divide(WX_UNIT);
                    PayOrder order = new PayOrder();//根据查询out_trade_no
                    if (null != order) {

                        logger.warn("orderAmount==" + orderAmount + ", dbAmount==" + order.getPayAmount() + ", equals==" + orderAmount.compareTo(order.getPayAmount()));

                        //更新支付状态为已成功
                        if (orderAmount.compareTo(order.getPayAmount()) == 0 && !WxPayStatusEnum.SUCCESS.getKey().equals(order.getPayStatus())) {
                            logger.warn("更新数据信息..........." + out_trade_no);

                            order.setPayStatus(Integer.valueOf(WxPayStatusEnum.SUCCESS.getKey()));
                            logger.info(" =====支付成功====:" + out_trade_no);
                        }

                        logger.warn("未更新积分信息..........." + out_trade_no);
                        //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
                        logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                                + "<return_msg><![CDATA[无效订单，订单金额一致]]></return_msg>" + "</xml> ";
                    }
                } else {
                    logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                }

                //------------------------------
                //处理业务完毕
                //------------------------------
                BufferedOutputStream out = new BufferedOutputStream(
                        response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            } else{
                logger.info("通知签名验证失败");
            }
        } catch (Exception e) {
            logger.error("微信支付回调异常==>" + e.getMessage(),e);
        }

    }

}
