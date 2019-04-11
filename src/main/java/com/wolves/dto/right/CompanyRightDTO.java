package com.wolves.dto.right;

import java.io.Serializable;
import java.util.List;

/**
 * 企业权限
 */
public class CompanyRightDTO implements Serializable {

    private String companyId;
    private String logUrl;
    List<RoleDTO> roleDTOList;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public List<RoleDTO> getRoleDTOList() {
        return roleDTOList;
    }

    public void setRoleDTOList(List<RoleDTO> roleDTOList) {
        this.roleDTOList = roleDTOList;
    }
}
