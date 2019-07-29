package com.wolves.dto.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 缴费
 * @author gf
 * @date 2019/7/29
 */
@ApiModel(description = "缴费")
public class PayMentDTO {

    @ApiModelProperty(name = "paymentId",value = "ID")
    private String paymentId;

    @ApiModelProperty(name = "companyId",value = "公司id")
    private String companyId;

    @ApiModelProperty(name = "paymentType",value = "缴费备注")
    private String paymentType;

    @ApiModelProperty(name = "amount",value = "金额")
    private String amount;

    @ApiModelProperty(name = "paymentDate",value = "费用月度")
    private String paymentDate;

    @ApiModelProperty(name = "status",value = "状态(0:待支付,1支付成功,2取消支付)")
    private Integer status;

    @ApiModelProperty(name = "createTime",value = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
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
}
