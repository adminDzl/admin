package com.wolves.dto;

import java.util.Date;

/**
 * Created by Administrator on 2019/3/17.
 */
public class NewsTipDTO {

    private String newstipId;

    private String newsTitle;

    private String newsContent;

    private String attachUrl;

    private Date createTime;

    public String getNewstipId() {
        return newstipId;
    }

    public void setNewstipId(String newstipId) {
        this.newstipId = newstipId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
