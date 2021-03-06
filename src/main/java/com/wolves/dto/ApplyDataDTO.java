package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author gf
 * @date 2019/7/2
 */
@ApiModel(description = "统一申请")
public class ApplyDataDTO {

    @ApiModelProperty(name = "id",value = "ID")
    private String id;

    @ApiModelProperty(name = "title",value = "标题")
    private String title;

    @ApiModelProperty(name = "headImage",value = "头图")
    private String headImage;

    @ApiModelProperty(name = "newsContent",value = "内容")
    private String newsContent;

    @ApiModelProperty(name = "attachUrl",value = "下载链接")
    private String attachUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    @Override
    public String toString() {
        return "ApplyDataDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", attachUrl='" + attachUrl + '\'' +
                '}';
    }
}
