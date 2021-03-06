package com.wolves.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2019/3/9
 */
@ApiModel(description = "申请场地信息")
public class YardDTO {

    @ApiModelProperty(name = "yardId",value = "场地ID")
    private String yardId;

    @ApiModelProperty(name = "placeType",value = "场地类型（1.会议室，2.活动室，3健身房）")
    private Integer placeType;

    @ApiModelProperty(name = "position",value = "所处位置")
    private String position;

    @ApiModelProperty(name = "imageUrl",value = "图片地址")
    private String imageUrl;

    @ApiModelProperty(name = "imageUrls",value = "图片地址")
    private List<String> imageUrls;

    @ApiModelProperty(name = "equipment",value = "设备描述")
    private String equipment;

    @ApiModelProperty(name = "rentFee",value = "价格")
    private BigDecimal rentFee;

    @ApiModelProperty(name = "servicePhone",value = "客服号码")
    private String servicePhone;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public String getYardId() {
        return yardId;
    }

    public void setYardId(String yardId) {
        this.yardId = yardId;
    }

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public BigDecimal getRentFee() {
        return rentFee;
    }

    public void setRentFee(BigDecimal rentFee) {
        this.rentFee = rentFee;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "YardParamsDTO{" +
                "yardId='" + yardId + '\'' +
                ", placeType=" + placeType +
                ", position='" + position + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", equipment='" + equipment + '\'' +
                ", rentFee=" + rentFee +
                ", createTime=" + createTime +
                '}';
    }
}
