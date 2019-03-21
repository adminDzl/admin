package com.wolves.dto.user;

/**
 * Created by gf on 2019/3/21.
 */
public class BaseCompanyDTO {
    private String companyId;

    private String companyName;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "BaseCompanyDTO{" +
                "companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
