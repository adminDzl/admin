package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 * @author gf
 * @date 2019/3/5
 */
@ApiModel(description = "登陆")
public class LoginDTO implements Serializable{

    @ApiModelProperty(name = "telephone",value = "手机号码")
    private String telephone;

    @ApiModelProperty(name = "password",value = "密码")
    private String password;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
