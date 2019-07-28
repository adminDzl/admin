package com.wolves.dto.right;

import java.util.List;

public class RoleDTO {

    private Integer roleId;
    private String roleName;
    private List<UserRightDTO> userRightDTOList;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<UserRightDTO> getUserRightDTOList() {
        return userRightDTOList;
    }

    public void setUserRightDTOList(List<UserRightDTO> userRightDTOList) {
        this.userRightDTOList = userRightDTOList;
    }
}
