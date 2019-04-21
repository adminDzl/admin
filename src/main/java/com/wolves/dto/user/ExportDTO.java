package com.wolves.dto.user;

/**
 * 一卡通赋值数据
 * @author gf
 * @date 2019/4/21
 */
public class ExportDTO {
    private String name;

    private String phone;

    private String idcard;

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

    @Override
    public String toString() {
        return "ExportDTO{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", idcard='" + idcard + '\'' +
                '}';
    }
}
