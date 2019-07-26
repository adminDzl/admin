package com.wolves.dto.resource;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author xulu  E-mail:java_xul@163.com
 * @version 1.0
 * @create 2019-07-26 12:49
 **/
public class AppResourceDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "资源名")
    private String resourceName;
    @ApiModelProperty(value = "说明")
    private String resourceRemark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceRemark() {
        return resourceRemark;
    }

    public void setResourceRemark(String resourceRemark) {
        this.resourceRemark = resourceRemark;
    }
}
