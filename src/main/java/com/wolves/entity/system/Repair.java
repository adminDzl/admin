package com.wolves.entity.system;

import java.util.Date;

/**
 *
 * @author gf
 * @date 2019/3/24
 */
public class Repair {

    private String repairApplyId;

    private String userId;

    private String applyContent;

    private String imageUrls;

    private Integer applyStatus;

    private Date createTime;

    private Date updateTime;

    public String getRepairApplyId() {
        return repairApplyId;
    }

    public void setRepairApplyId(String repairApplyId) {
        this.repairApplyId = repairApplyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "repairApplyId='" + repairApplyId + '\'' +
                ", userId='" + userId + '\'' +
                ", applyContent='" + applyContent + '\'' +
                ", imageUrls='" + imageUrls + '\'' +
                ", applyStatus=" + applyStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
