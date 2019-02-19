package com.fh.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.data.Result;
import com.fh.common.data.ResultData;
import com.fh.service.wechat.WechatService;
import com.fh.util.Constants;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by xulu on 2016/3/14.
 */
@Controller
public class ApiController{

    @Autowired
    private WechatService weChatService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *提供wap作刷新获取access_token
     */
    @RequestMapping(value="/api/token", method = RequestMethod.GET)
    @ResponseBody
    public ResultData getAccessTokenByRefreshToken(@RequestParam String refresh_token) {
        //操作结果 0：成功  1：业务错误(msg写失败描述)、 -1：系统异常 -2：服务拒绝访问
        ResultData result = new ResultData();
        if(StringUtils.isEmpty(refresh_token)){
            result.setMsg("参数错误");
            return result;
        }
        JSONObject obj = null;
        try{
            obj = weChatService.getPageToken(refresh_token, Constants.getAppId());
        }catch (Exception e){
            logger.error("获取页面token异常", e);
            result.setResult(-1);
            result.setMsg("error");
        }
        obj.remove("expires_in");
        obj.remove("scope");
        result.setResult(0);
        result.setData(obj);  /* data like { "access_token":"ACCESS_TOKEN", "refresh_token":"REFRESH_TOKEN", "openid":"OPENID", } */
        return result;
    }

    /**
     * 提供sso
     */
    @RequestMapping(value="/api/oauth", method = RequestMethod.POST)
    @ResponseBody
    public Object oauth(@RequestParam String access_token, @RequestParam String openId){
        //操作结果 0：成功  1：业务错误(msg写失败描述)、 -1：系统异常 -2：服务拒绝访问
        JSONObject returnObj = new JSONObject();
        if(StringUtils.isEmpty(access_token) || StringUtils.isEmpty(openId)){
            returnObj.put("errorcode", "1");
            returnObj.put("msg", "参数错误");
            returnObj.put("data", "");
            return returnObj;
        }
        JSONObject obj = null;
        try{
            obj = weChatService.oauth(access_token, openId);
        } catch (Exception e){
            logger.error("sso调用异常", e);
            returnObj.put("errorcode", "-1");
            returnObj.put("msg", "认证异常");
            returnObj.put("data", "");
            return returnObj;
        }
        returnObj.put("errorcode", "0");
        returnObj.put("msg", "");
        returnObj.put("data", obj);
        logger.info("outh:"+obj);
        return returnObj;
    }
    /**
     * 创建自定义菜单
     */
    @RequestMapping(value="/api/menu", method = RequestMethod.POST)
    @ResponseBody
    public void createMenu(@RequestParam String data){
        weChatService.createMenu(data, WechatUtil.getToken());
    }

    /**
     *pc端调用生成二维码ticket(用做扫码绑定)
     * @return
     */
    @RequestMapping(value="/api/ticket", method = RequestMethod.GET)
    @ResponseBody
    public Object getTicket(HttpServletRequest request, @RequestParam String userId) {
        String ticket = "";
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval( 60 * 60 );
        ticket = (String)session.getAttribute("weixin_ticket");
        if(StringUtils.isEmpty(ticket)){
            try{
                ticket = weChatService.getWechatTicket(userId, WechatUtil.getToken());
                session.setAttribute("weixin_ticket", ticket);
            } catch (Exception e){
                logger.error("生成二维码ticket异常", e);
                ticket = "";
            }
        }
        //返回示例：{"ticket":"gQEu8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL09uVlVmM25saDNyX292WjBBRnZBAAIEv7XeVgMEgDoJAA==","expire_seconds":604800,"url":"http://weixin.qq.com/q/OnVUf3nlh3r_ovZ0AFvA"}
        return JSONObject.toJSON(ticket);
    }

    /**
     * 批量获取微信用户信息
     */
    @RequestMapping(value="/api/userBatch", method = RequestMethod.GET)
    @ResponseBody
    public Result getUserBatch(@RequestParam String user_list) throws Exception{
        Result result = new Result();
        Object obj = weChatService.getUserInfoBatch(WechatUtil.getToken(), user_list);
        if(null == obj){
            result.setResult(1);
            result.setMsg("无数据");
        } else {
            result.setResult(0);
            result.setData(obj);
        }
        return result;
    }

    /**
     *投资消息
     */
    @RequestMapping(value="/api/sendMsg/test", method = RequestMethod.GET)
    @ResponseBody
    public Result sendInvestMsg(String openId, Date investTime, String amount, String billName) throws Exception{
        Result result = new Result();
        String tempId = "investTemp";
        openId = "o2TBWw1WB1gZw5cNxhVFlMDMGaYw";
        String tempMsgId = (String) TempConstants.getTempMap().get(tempId);
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        obj.put("touser", openId);
        obj.put("template_id", tempMsgId);
        obj.put("url","http://www.baidu.com");
        obj.put("topcolor","#FF0000");

        JSONObject time = new JSONObject();
        time.put("value",investTime);
        time.put("color","#173177");

        JSONObject money = new JSONObject();
        money.put("value",amount);
        money.put("color","#173177");

        JSONObject bname = new JSONObject();
        bname.put("value",billName);
        bname.put("color","#173177");

        data.put("time",time);
        data.put("money",money);
        data.put("date", bname);

        obj.put("data", data);

        String msg = JSONObject.toJSONString(obj);
        String token = WechatUtil.getToken();
        weChatService.sendTemp(token, msg);
        result.setResult(0);
        result.setMsg("发送成功！");
        return result;
    }

}
