package com.wolves.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 * @author Administrator
 * @date 2019/3/17
 */
@ApiModel(description = "申请场地信息")
public class NewsTipDTO {

    @ApiModelProperty(name = "newstipId",value = "Id")
    private String newstipId;

    @ApiModelProperty(name = "newsTitle",value = "标题")
    private String newsTitle;

    @ApiModelProperty(name = "headImage",value = "头图")
    private String headImage;

    @ApiModelProperty(name = "newsContent",value = "内容")
    private String newsContent;

    @ApiModelProperty(name = "breviaryContent",value = "缩略内容")
    private String breviaryContent;

    @ApiModelProperty(name = "attachUrl",value = "链接")
    private String attachUrl;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
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

    public String getBreviaryContent() {
        return breviaryContent;
    }

    public void setBreviaryContent(String breviaryContent) {
        this.breviaryContent = breviaryContent;
    }

    @Override
    public String toString() {
        return "NewsTipDTO{" +
                "newstipId='" + newstipId + '\'' +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsContent='" + newsContent + '\'' +
                ", attachUrl='" + attachUrl + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
