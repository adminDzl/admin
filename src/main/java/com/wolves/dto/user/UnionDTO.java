package com.wolves.dto.user;

/**
 *  一卡通申请赋值数据
 * @author gf
 * @date 2019/7/25
 */
public class UnionDTO {

    private String user_id;

    private String buildman_id;

    private String companyId;

    private String companyName;

    private String name;

    private String phone;

    private String applyTime;

    /**
     * 园区选项
     */
    private String enter;

    private String waitEnter;

    private String non;

    /**
     * 判断类型
     */
    private String type;

    private String status;

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
     * 申请卡选项
     */
    private String fresh;

    private String change;

    private String lose;

    private String logout;

    /**
     * 男nv
     */
    private String man;

    private String woman;

    private String idCard;

    private String plate;

    private String sex;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBuildman_id() {
        return buildman_id;
    }

    public void setBuildman_id(String buildman_id) {
        this.buildman_id = buildman_id;
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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getEnter() {
        return enter;
    }

    public void setEnter(String enter) {
        this.enter = enter;
    }

    public String getWaitEnter() {
        return waitEnter;
    }

    public void setWaitEnter(String waitEnter) {
        this.waitEnter = waitEnter;
    }

    public String getNon() {
        return non;
    }

    public void setNon(String non) {
        this.non = non;
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

    public String getFresh() {
        return fresh;
    }

    public void setFresh(String fresh) {
        this.fresh = fresh;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getLose() {
        return lose;
    }

    public void setLose(String lose) {
        this.lose = lose;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public String getWoman() {
        return woman;
    }

    public void setWoman(String woman) {
        this.woman = woman;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UnionDTO{" +
                "user_id='" + user_id + '\'' +
                ", buildman_id='" + buildman_id + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", applyTime='" + applyTime + '\'' +
                ", enter='" + enter + '\'' +
                ", waitEnter='" + waitEnter + '\'' +
                ", non='" + non + '\'' +
                ", other='" + other + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", build='" + build + '\'' +
                ", house='" + house + '\'' +
                ", other='" + other + '\'' +
                ", building='" + building + '\'' +
                ", floor='" + floor + '\'' +
                ", fresh='" + fresh + '\'' +
                ", change='" + change + '\'' +
                ", lose='" + lose + '\'' +
                ", logout='" + logout + '\'' +
                ", man='" + man + '\'' +
                ", woman='" + woman + '\'' +
                ", idCard='" + idCard + '\'' +
                ", plate='" + plate + '\'' +
                '}';
    }
}
