package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author gf
 * @date 2019/3/22
 */
@ApiModel(description = "验证码信息")
public class SmsDataDTO {

    @ApiModelProperty(name = "phone",value = "手机号码")
    private String phone;

    @ApiModelProperty(name = "type",value = "1.注册，2登陆，3忘记密码")
    private Integer type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SmsDataDTO{" +
                "phone='" + phone + '\'' +
                ", type=" + type +
                '}';
    }
}
