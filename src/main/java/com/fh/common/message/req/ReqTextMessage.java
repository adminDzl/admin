package com.fh.common.message.req;

/**
 * Created by xulu on 2016/3/7.
 */
public class ReqTextMessage extends ReqBaseMessage{
    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
