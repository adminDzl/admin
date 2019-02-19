package com.fh.controller.wechat;

import com.fh.common.message.resp.RespTextMessage;
import com.fh.util.MessageUtil;
import com.fh.service.wechat.WechatService;
import com.fh.util.Constants;
import com.fh.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@Controller
public class CoreController {

    @Autowired
    private WechatService wechatService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = { "/appcore/coreJoin" }, method = RequestMethod.GET)
    public void coreJoinGet(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        logger.info("hello");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
    }

    @RequestMapping(value = { "/appcore/coreJoin" }, method = RequestMethod.POST)
    public void coreJoinPost(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String respContent = "";

        Map<String, String> requestMap = MessageUtil.parseXml(request);
        System.out.println("requestMap:"+requestMap);
        String fromUserName = requestMap.get("FromUserName");
        String toUserName = requestMap.get("ToUserName");
        String msgType = requestMap.get("MsgType");

        // 回复文本消息
        RespTextMessage textMessage = new RespTextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);

        System.out.println("msgType:"+msgType);
        if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType) || MessageUtil.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)
                || MessageUtil.REQ_MESSAGE_TYPE_LOCATION.equals(msgType) || MessageUtil.REQ_MESSAGE_TYPE_LINK.equals(msgType)
                || MessageUtil.REQ_MESSAGE_TYPE_VOICE.equals(msgType)) {
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_CUSTOMER);
        } else if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
            String eventType = requestMap.get("Event");
            if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
                respContent = "您好，感谢您的关注，现在点击【我要理财】－【绑定送银两】菜单，绑定您的银票网账号，就能领取银两，100%可获得\n" +
                        "绑定账户后，您还可以享受以下服务：\n" +
                        "－好友推荐领奖励\n" +
                        "－签到领取银两\n" +
                        "－查询下一收款日\n" +
                        "－尊享VIP\n" +
                        "－金牌理财师\n" +
                        "想进一步了解银票网吗？现在就开始向我提问吧～";
            } else if (MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
                return;
            } else if (MessageUtil.EVENT_TYPE_CLICK.equals(eventType)) {
                if(Constants.MENU_CLICK_SIGN_IN.equals(requestMap.get("EventKey"))){
                    String openId = requestMap.get("FromUserName");
                    respContent = wechatService.signIn(openId).getMsg();
                }
            } else if (MessageUtil.EVENT_TYPE_VIEW.equals(eventType)){
                return;
            } else {
                return;
            }
        }

        textMessage.setContent(respContent);
        String respMessage = MessageUtil.textMessageToXml(textMessage);

        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }
}