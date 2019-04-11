package com.wolves.dto.right;

import java.util.List;

public class RoleDTO {

    private Integer id;
    private String roleName;
    private List<UserRightDTO> userDTOList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserRightDTO> getUserDTOList() {
        return userDTOList;
    }

    public void setUserDTOList(List<UserRightDTO> userDTOList) {
        this.userDTOList = userDTOList;
    }
}
