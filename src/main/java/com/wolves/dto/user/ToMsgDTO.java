package com.wolves.dto.user;

/**
 * Created by Administrator on 2019/7/31.
 */
public class ToMsgDTO {

    private String tipmsgId;

    private Integer type;

    private Integer msgType;

    private String userId;

    private String title;

    private String content;

    public String getTipmsgId() {
        return tipmsgId;
    }

    public void setTipmsgId(String tipmsgId) {
        this.tipmsgId = tipmsgId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
