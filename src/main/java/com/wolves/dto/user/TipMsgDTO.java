package com.wolves.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author gf
 */
@ApiModel(description = "保修明细")
public class TipMsgDTO {

    @ApiModelProperty(name = "applyContent",value = "ID")
    private String tipMsgId;

    @ApiModelProperty(name = "applyContent",value = "类型")
    private Integer type;

    @ApiModelProperty(name = "applyContent",value = "类型")
    private Integer msgType;

    @ApiModelProperty(name = "applyContent",value = "面向人群")
    private String toUser;

    @ApiModelProperty(name = "applyContent",value = "申请标题")
    private String alertTitle;

    @ApiModelProperty(name = "applyContent",value = "是否已读")
    private Integer read;

    @ApiModelProperty(name = "applyContent",value = "申请内容")
    private String alertContent;

    @ApiModelProperty(name = "applyContent",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(name = "applyContent",value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    public String getTipMsgId() {
        return tipMsgId;
    }

    public void setTipMsgId(String tipMsgId) {
        this.tipMsgId = tipMsgId;
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

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
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
        return "TipMsgDTO{" +
                "tipMsgId='" + tipMsgId + '\'' +
                ", type=" + type +
                ", msgType=" + msgType +
                ", toUser='" + toUser + '\'' +
                ", alertTitle='" + alertTitle + '\'' +
                ", read=" + read +
                ", alertContent='" + alertContent + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
