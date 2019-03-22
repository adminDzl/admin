package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author Administrator
 * @date 2019/3/5
 */
@ApiModel(description = "忘记密码")
public class ForgetDTO {

    @ApiModelProperty(name = "telephone",value = "手机号码")
    private String telephone;

    @ApiModelProperty(name = "code",value = "验证码")
    private String code;

    @ApiModelProperty(name = "password",value = "密码")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ForgetDTO{" +
                "telephone='" + telephone + '\'' +
                ", code='" + code + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
