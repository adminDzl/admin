package com.wolves.dto.right;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author xulu
 */
@ApiModel(description = "添加角色")
public class AddRoleDTO implements Serializable {

    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "资源权限id")
    private List<Integer> resourceId;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getResourceId() {
        return resourceId;
    }

    public void setResourceId(List<Integer> resourceId) {
        this.resourceId = resourceId;
    }
}
