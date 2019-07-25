package com.wolves.dto.user;

/**
 * 停车场赋值数据
 * @author gf
 * @date 2019/4/21
 */
public class ExportDTO {

    private String id;

    private String companyId;

    private String companyName;

    /**
     * 年-月-日
     */
    private String payTime;
    /**
     * 主楼
     */
    private String build;

    /**
     * 南北楼
     */
    private String house;

    private String other;

    private String building;

    private String floor;

    /**
     * 首次开通
     */
    private String first;

    /**
     * 再次续费
     */
    private String again;

    /**
     * 修改信息
     */
    private String change;

    /**
     * 年-月-日
     */
    private String expireTime;

    /**
     * 车牌
     */
    private String plate;

    /**
     * 金额
     */
    private String amount;

    private String name;

    private String phone;

    private String idcard;

    private String image;

    private String imageBack;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getAgain() {
        return again;
    }

    public void setAgain(String again) {
        this.again = again;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageBack() {
        return imageBack;
    }

    public void setImageBack(String imageBack) {
        this.imageBack = imageBack;
    }

    @Override
    public String toString() {
        return "ExportDTO{" +
                "companyName='" + companyName + '\'' +
                ", payTime='" + payTime + '\'' +
                ", build=" + build +
                ", house=" + house +
                ", other=" + other +
                ", building='" + building + '\'' +
                ", floor='" + floor + '\'' +
                ", first=" + first +
                ", again=" + again +
                ", change=" + change +
                ", expireTime='" + expireTime + '\'' +
                ", plate='" + plate + '\'' +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", idcard='" + idcard + '\'' +
                '}';
    }
}
