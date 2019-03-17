package com.wolves.dto.user;

import java.util.Date;

public class RepairApplyDTO {

    private String repairApplyId;

    private String applyContent;

    private Integer applyStatus;

    private Date createTime;

    public String getRepairApplyId() {
        return repairApplyId;
    }

    public void setRepairApplyId(String repairApplyId) {
        this.repairApplyId = repairApplyId;
    }

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
