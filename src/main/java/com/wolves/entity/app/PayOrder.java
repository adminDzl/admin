package com.wolves.entity.app;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gf on 2019/3/12.
 */
public class PayOrder {
    private String payorderId;

    private Integer payType;

    private String userId;

    private BigDecimal payAmount;

    private Date payTime;

    private Date returnTime;

    private String remark;

    private Integer payStatus;

    private String channelType;

    private String yardappointId;

    private Date createTime;

    private Date updateTime;

    public String getPayorderId() {
        return payorderId;
    }

    public void setPayorderId(String payorderId) {
        this.payorderId = payorderId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getYardappointId() {
        return yardappointId;
    }

    public void setYardappointId(String yardappointId) {
        this.yardappointId = yardappointId;
    }

    @Override
    public String toString() {
        return "PayOrder{" +
                "payorderId='" + payorderId + '\'' +
                ", payType=" + payType +
                ", userId='" + userId + '\'' +
                ", payAmount=" + payAmount +
                ", payTime=" + payTime +
                ", returnTime=" + returnTime +
                ", remark='" + remark + '\'' +
                ", payStatus=" + payStatus +
                ", channelType='" + channelType + '\'' +
                ", yardappointId='" + yardappointId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
