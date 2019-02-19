package com.fh.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.data.Result;
import com.fh.service.wechat.WechatService;
import com.fh.util.StringUtils;
import com.fh.util.TempConstants;
import com.fh.util.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xulu on 2016/4/11.
 */
@Controller
public class MsgTemplateController {

    @Autowired
    private WechatService weChatService;

    private static final String MSG_TEMP_INVEST = "investTemp";
    private static final String MSG_TEMP_RECHARGE = "rechargeTemp";
    private static final String MSG_TEMP_DEPOSIT = "depositsTemp";
    private static final String MSG_TEMP_ADDBILL = "addBillTemp";

    private static final String MSG_REMARK = "备注：如有疑问，请致电银票网客服热线：400-888-9858";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *支付通知
     */
    @RequestMapping(value="/api/sendMsg/invest", method = RequestMethod.GET)
    @ResponseBody
    public Result sendInvestMsg(@RequestParam String openId, @RequestParam String amount, @RequestParam String billName) throws Exception{
        //{{first.DATA}} 支付金额：{{orderMoneySum.DATA}} 商品信息：{{orderProductName.DATA}} {{Remark.DATA}}
        Result result = new Result();
        if(StringUtils.isEmpty(openId) || StringUtils.isEmpty(amount) || StringUtils.isEmpty(billName)){
            result.setResult(1);
            result.setMsg("必要参数不能为空");
            return result;
        }
        boolean flag = false;//acctService.getUserBind(openId);
        if(!flag){
            result.setResult(1);
            result.setMsg("openId错误或未绑定");
            return result;
        }
        String first = "我们已收到您的投资款项。";
        String tempMsgId = (String) TempConstants.getTempMap().get(MSG_TEMP_INVEST);

        JSONObject obj = new JSONObject();
        obj.put("touser", openId);
        obj.put("template_id", tempMsgId);
        obj.put("url","http://www.baidu.com");
        obj.put("topcolor","#FF0000");

        JSONObject firstObj = new JSONObject();
        firstObj.put("value",first);
        firstObj.put("color","#173177");

        JSONObject orderMoneySumObj = new JSONObject();
        orderMoneySumObj.put("value",amount);
        orderMoneySumObj.put("color","#173177");

        JSONObject orderProductNameObj = new JSONObject();
        orderProductNameObj.put("value",billName);
        orderProductNameObj.put("color","#173177");

        JSONObject remarkObj = new JSONObject();
        remarkObj.put("value", MSG_REMARK);
        remarkObj.put("color","#173177");

        JSONObject data = new JSONObject();
        data.put("first", firstObj);
        data.put("orderMoneySum", orderMoneySumObj);
        data.put("orderProductName", orderProductNameObj);
        data.put("Remark", remarkObj);

        obj.put("data", data);

        String msg = JSONObject.toJSONString(obj);
        try{
            String token = WechatUtil.getToken();
            weChatService.sendTemp(token, msg);
        } catch (Exception e){
            logger.error("投资消息发送异常", e);
            result.setResult(-1);
            result.setMsg("发送失败");
            return result;
        }
        result.setResult(0);
        result.setMsg("发送成功");
        return result;
    }

    /**
     *充值通知
     * @param status(1失败,0成功)
     */
    @RequestMapping(value="/api/sendMsg/charge", method = RequestMethod.GET)
    @ResponseBody
    public Result chargeMsg(@RequestParam String openId, @RequestParam String tel, @RequestParam String amount, @RequestParam String status) throws Exception {
        //	{{first.DATA}} {{accountType.DATA}}:{{account.DATA}} 充值金额：{{amount.DATA}} 充值状态：{{result.DATA}} {{remark.DATA}}
        Result result = new Result();
        if(StringUtils.isEmpty(openId) || StringUtils.isEmpty(tel) || StringUtils.isEmpty(amount) || StringUtils.isEmpty(status)){
            result.setResult(1);
            result.setMsg("必要参数不能为空");
            return result;
        }
        boolean flag = false;//acctService.getUserBind(openId);
        if(!flag){
            result.setResult(1);
            result.setMsg("openId错误或未绑定");
            return result;
        }
        String first = "您好，您已成功进行理财账户充值。";
        String accountType = "理财账户";
        String resultMsg = "";
        if("1".equals(status)){
            resultMsg = "充值失败";
        } else if("0".equals(status)){
            resultMsg = "充值成功";
        } else {
            result.setResult(1);
            result.setMsg("status状态错误");
            return result;
        }
        String tempMsgId = (String) TempConstants.getTempMap().get(MSG_TEMP_RECHARGE);

        JSONObject obj = new JSONObject();
        obj.put("touser", openId);
        obj.put("template_id", tempMsgId);
        obj.put("url","http://www.baidu.com");
        obj.put("topcolor","#FF0000");

        JSONObject firstObj = new JSONObject();
        firstObj.put("value",first);
        firstObj.put("color","#173177");

        JSONObject accountTypeObj = new JSONObject();
        accountTypeObj.put("value",accountType);
        accountTypeObj.put("color","#000000");

        JSONObject accountObj = new JSONObject();
        accountObj.put("value",tel);
        accountObj.put("color","#173177");

        JSONObject amountObj = new JSONObject();
        amountObj.put("value",amount);
        amountObj.put("color","#173177");

        JSONObject resultMsgObj = new JSONObject();
        resultMsgObj.put("value",resultMsg);
        resultMsgObj.put("color","#173177");

        JSONObject remarkObj = new JSONObject();
        remarkObj.put("value", MSG_REMARK);
        remarkObj.put("color","#173177");

        JSONObject data = new JSONObject();
        data.put("first", firstObj);
        data.put("accountType", accountTypeObj);
        data.put("account", accountObj);
        data.put("amount", amountObj);
        data.put("result", resultMsgObj);
        data.put("remark", remarkObj);

        obj.put("data", data);

        String msg = JSONObject.toJSONString(obj);
        try{
            String token = WechatUtil.getToken();
            weChatService.sendTemp(token, msg);
        } catch (Exception e){
            logger.error("充值消息发送异常", e);
            result.setResult(-1);
            result.setMsg("发送失败");
            return result;
        }
        result.setResult(0);
        result.setMsg("发送成功");
        return result;
    }
}
