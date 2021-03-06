package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/3/30.
 */
@ApiModel(description = "个人信息")
public class UserDTO {
    @ApiModelProperty(name = "userId",value = "用户ID")
    private String userId;

    @ApiModelProperty(name = "name",value = "姓名")
    private String name;

    @ApiModelProperty(name = "headImage",value = "头像")
    private String headImage;

    @ApiModelProperty(name = "sex",value = "性别")
    private String sex;

    @ApiModelProperty(name = "email",value = "邮箱")
    private String email;

    @ApiModelProperty(name = "phone",value = "电话")
    private String phone;

    @ApiModelProperty(name = "companyId",value = "企业ID")
    private String companyId;

    @ApiModelProperty(name = "companyName",value = "企业名称")
    private String companyName;

    @ApiModelProperty(name = "logo",value = "企业logo")
    private String logo;

    @ApiModelProperty(name = "companyCertify",value = "企业认证信息")
    private String companyCertify;

    @ApiModelProperty(name = "status",value = "状态(0:冻结，1:正常，2:离职)")
    private String status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCompanyCertify() {
        return companyCertify;
    }

    public void setCompanyCertify(String companyCertify) {
        this.companyCertify = companyCertify;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", logo='" + logo + '\'' +
                ", companyCertify='" + companyCertify + '\'' +
                '}';
    }
}
