package com.wolves.dto.right;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * @author xulu
 */
@ApiModel(description = "添加角色")
public class DeleteRoleDTO implements Serializable {

    @ApiModelProperty(value = "角色id")
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
