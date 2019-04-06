package com.wolves.entity.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 统一申请
 * @author gf
 * @date 2019/4/6
 */
@ApiModel(description = "统一申请")
public class Decorate {

    @ApiModelProperty(name = "decorateId",value = "ID")
    private String decorateId;

    @ApiModelProperty(name = "decorateNo",value = "编号")
    private String decorateNo;

    @ApiModelProperty(name = "type",value = "类型(1-装修申请，2-其他)")
    private Integer type;

    @ApiModelProperty(name = "userId",value = "用户Id")
    private String userId;

    @ApiModelProperty(name = "title",value = "申请标题")
    private String title;

    @ApiModelProperty(name = "content",value = "申请内容")
    private String content;

    @ApiModelProperty(name = "status",value = "审核状态（0-待审核，1-审核通过，2-已驳回）")
    private Integer status;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(name = "updateTime",value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    public String getDecorateId() {
        return decorateId;
    }

    public void setDecorateId(String decorateId) {
        this.decorateId = decorateId;
    }

    public String getDecorateNo() {
        return decorateNo;
    }

    public void setDecorateNo(String decorateNo) {
        this.decorateNo = decorateNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Decorate{" +
                "decorateId='" + decorateId + '\'' +
                ", decorateNo='" + decorateNo + '\'' +
                ", type=" + type +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
