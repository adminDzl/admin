package com.wolves.dto.user;

import java.util.Date;

public class RepairApplyDetailDTO {

    private String applyContent;

    private String imageUrls;

    private Integer applyStatus;

    private Date createTimne;

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

    public Date getCreateTimne() {
        return createTimne;
    }

    public void setCreateTimne(Date createTimne) {
        this.createTimne = createTimne;
    }
}
