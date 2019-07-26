package com.wolves.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author xulu
 */
@ApiModel(description = "资源权限")
public class ResourceDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer resourceId;
    @ApiModelProperty(value = "名称")
    private String resourceName;
    @ApiModelProperty(value = "是否拥有（1是，0否）")
    private Integer hasResource;

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getHasResource() {
        return hasResource;
    }

    public void setHasResource(Integer hasResource) {
        this.hasResource = hasResource;
    }

}
