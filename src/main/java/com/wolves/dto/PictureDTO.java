package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Administrator on 2019/3/30.
 */
@ApiModel(description = "轮播")
public class PictureDTO {

    @ApiModelProperty(name = "picturesId",value = "图片id")
    private String picturesId;

    @ApiModelProperty(name = "title",value = "标题")
    private String title;

    @ApiModelProperty(name = "path",value = "图片地址")
    private String path;

    @ApiModelProperty(name = "linkId",value = "跳转链接")
    private String linkId;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    private Date createTime;

    public String getPicturesId() {
        return picturesId;
    }

    public void setPicturesId(String picturesId) {
        this.picturesId = picturesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PictureDTO{" +
                "picturesId='" + picturesId + '\'' +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", linkId='" + linkId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
