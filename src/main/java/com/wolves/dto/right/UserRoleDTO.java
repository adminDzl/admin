package com.wolves.dto.right;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * @author xulu
 */
@ApiModel(description = "给角色添加用户")
public class UserRoleDTO implements Serializable {

    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
