package com.wolves.dto.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/3/25.
 */
@ApiModel(description = "场地参数")
public class YardParamsDTO {

    @ApiModelProperty(name = "type",value = "场地类型（1.会议室，2.活动室，3健身房）")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "YardParamsDTO{" +
                "type=" + type +
                '}';
    }
}
