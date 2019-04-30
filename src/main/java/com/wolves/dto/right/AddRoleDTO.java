package com.wolves.dto.right;

import com.wolves.dto.ResourceDTO;
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
    @ApiModelProperty(value = "资源权限列表")
    private List<ResourceDTO> resourceDTOList;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<ResourceDTO> getResourceDTOList() {
        return resourceDTOList;
    }

    public void setResourceDTOList(List<ResourceDTO> resourceDTOList) {
        this.resourceDTOList = resourceDTOList;
    }
}
