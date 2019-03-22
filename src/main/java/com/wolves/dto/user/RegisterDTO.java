package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author Administrator
 * @date 2019/3/5
 */
@ApiModel(description = "注册信息")
public class RegisterDTO {

    @ApiModelProperty(name = "companyId",value = "公司id")
    private String companyId;

    @ApiModelProperty(name = "telephone",value = "手机号码")
    private String telephone;

    @ApiModelProperty(name = "code",value = "验证码")
    private String code;

    @ApiModelProperty(name = "idCardFrontUrl",value = "身份证正面照")
    private String idCardFrontUrl;

    @ApiModelProperty(name = "idCardBackUrl",value = "身份证反面照")
    private String idCardBackUrl;

    @ApiModelProperty(name = "password",value = "密码")
    private String password;

    @ApiModelProperty(name = "name",value = "姓名")
    private String name;

    @ApiModelProperty(name = "sex",value = "性别(男,女)")
    private String sex;

    @ApiModelProperty(name = "email",value = "邮箱地址")
    private String email;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdCardFrontUrl() {
        return idCardFrontUrl;
    }

    public void setIdCardFrontUrl(String idCardFrontUrl) {
        this.idCardFrontUrl = idCardFrontUrl;
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "companyId='" + companyId + '\'' +
                ", telephone='" + telephone + '\'' +
                ", code='" + code + '\'' +
                ", idCardFrontUrl='" + idCardFrontUrl + '\'' +
                ", idCardBackUrl='" + idCardBackUrl + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
