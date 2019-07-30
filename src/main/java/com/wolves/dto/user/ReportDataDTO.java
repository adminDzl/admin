package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/7/31.
 */
@ApiModel(description = "数据报表信息")
public class ReportDataDTO {

    @ApiModelProperty(name = "companyNum",value = "入住企业")
    private String companyNum;

    @ApiModelProperty(name = "personNum",value = "园区人数")
    private String personNum;

    @ApiModelProperty(name = "income",value = "园区缴费收入合计")
    private String income;

    @ApiModelProperty(name = "noPayCompanyNum",value = "尚未缴费企业")
    private String noPayCompanyNum;

    @ApiModelProperty(name = "warrantyNum",value = "当前报修数量")
    private String warrantyNum;

    @ApiModelProperty(name = "warrantyAuditNum",value = "报修审核中")
    private String warrantyAuditNum;

    @ApiModelProperty(name = "warrantyInitNum",value = "正在维修中数量")
    private String warrantyInitNum;

    @ApiModelProperty(name = "monthNewwarranty",value = "本月新增报修")
    private String monthNewWarranty;

    @ApiModelProperty(name = "monthAchieveWarranty",value = "本月完成报修")
    private String monthAchieveWarranty;

    public String getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getNoPayCompanyNum() {
        return noPayCompanyNum;
    }

    public void setNoPayCompanyNum(String noPayCompanyNum) {
        this.noPayCompanyNum = noPayCompanyNum;
    }

    public String getWarrantyNum() {
        return warrantyNum;
    }

    public void setWarrantyNum(String warrantyNum) {
        this.warrantyNum = warrantyNum;
    }

    public String getWarrantyAuditNum() {
        return warrantyAuditNum;
    }

    public void setWarrantyAuditNum(String warrantyAuditNum) {
        this.warrantyAuditNum = warrantyAuditNum;
    }

    public String getWarrantyInitNum() {
        return warrantyInitNum;
    }

    public void setWarrantyInitNum(String warrantyInitNum) {
        this.warrantyInitNum = warrantyInitNum;
    }

    public String getMonthNewWarranty() {
        return monthNewWarranty;
    }

    public void setMonthNewWarranty(String monthNewWarranty) {
        this.monthNewWarranty = monthNewWarranty;
    }

    public String getMonthAchieveWarranty() {
        return monthAchieveWarranty;
    }

    public void setMonthAchieveWarranty(String monthAchieveWarranty) {
        this.monthAchieveWarranty = monthAchieveWarranty;
    }

    @Override
    public String toString() {
        return "ReportDataDTO{" +
                "companyNum='" + companyNum + '\'' +
                ", personNum='" + personNum + '\'' +
                ", income='" + income + '\'' +
                ", noPayCompanyNum='" + noPayCompanyNum + '\'' +
                ", warrantyNum='" + warrantyNum + '\'' +
                ", warrantyAuditNum='" + warrantyAuditNum + '\'' +
                ", warrantyInitNum='" + warrantyInitNum + '\'' +
                ", monthNewWarranty='" + monthNewWarranty + '\'' +
                ", monthAchieveWarranty='" + monthAchieveWarranty + '\'' +
                '}';
    }
}
