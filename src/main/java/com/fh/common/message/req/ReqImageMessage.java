package com.fh.common.message.req;

/**
 * Created by xulu on 2016/3/7.
 */
public class ReqImageMessage extends ReqBaseMessage{
    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
