package com.wolves.dto.user;

/**
 * Created by Administrator on 2019/8/8.
 */
public class UserListDTO extends ManagerUserDTO{

    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
