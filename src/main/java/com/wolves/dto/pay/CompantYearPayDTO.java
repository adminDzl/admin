package com.wolves.dto.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 缴费
 * @author gf
 * @date 2019/7/24
 */
@ApiModel(description = "缴费")
public class CompantYearPayDTO {

    @ApiModelProperty(name = "id",value = "ID")
    private String id;

    @ApiModelProperty(name = "companyId",value = "公司ID")
    private String companyId;

    @ApiModelProperty(name = "companyName",value = "公司名称")
    private String companyName;

    @ApiModelProperty(name = "yearTotalPay",value = "当年全部需要缴纳费用")
    private String yearTotalPay;

    @ApiModelProperty(name = "yearYetPay",value = "当年已经缴费")
    private String yearYetPay;

    @ApiModelProperty(name = "yearWaitPay",value = "当年待缴纳费用")
    private String yearWaitPay;

    @ApiModelProperty(name = "createTime",value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getYearTotalPay() {
        return yearTotalPay;
    }

    public void setYearTotalPay(String yearTotalPay) {
        this.yearTotalPay = yearTotalPay;
    }

    public String getYearYetPay() {
        return yearYetPay;
    }

    public void setYearYetPay(String yearYetPay) {
        this.yearYetPay = yearYetPay;
    }

    public String getYearWaitPay() {
        return yearWaitPay;
    }

    public void setYearWaitPay(String yearWaitPay) {
        this.yearWaitPay = yearWaitPay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CompantYearPayDTO{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", yearTotalPay='" + yearTotalPay + '\'' +
                ", yearYetPay='" + yearYetPay + '\'' +
                ", yearWaitPay='" + yearWaitPay + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
