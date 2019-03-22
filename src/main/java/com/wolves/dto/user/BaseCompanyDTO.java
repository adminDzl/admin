package com.wolves.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author gf
 * @date 2019/3/21
 */
@ApiModel(description = "公司信息")
public class BaseCompanyDTO {

    @ApiModelProperty(name = "companyId",value = "公司id")
    private String companyId;

    @ApiModelProperty(name = "companyName",value = "公司名称")
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
