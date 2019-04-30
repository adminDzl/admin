package com.wolves.dto.role;

import com.wolves.dto.ResourceDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author xulu
 */
@ApiModel(description = "修改角色model")
public class UpdateRoleDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer roleId;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "资源权限列表")
    private List<ResourceDTO> resourceList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<ResourceDTO> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<ResourceDTO> resourceList) {
        this.resourceList = resourceList;
    }
}
