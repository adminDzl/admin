package com.wolves.common;

/**
 * 类型(1-楼宇装修申请，2-出门证申请，3-一卡通申请，4-楼宇施工许可申请，5-楼宇装饰工程竣工验收申请)
 * Created by gf on 2019/4/18.
 */
public enum ApplyTypeEnum {

    apply_build("1", "楼宇装修申请"),
    apply_out("2", "出门证申请"),
    apply_ic_tl("3", "一卡通申请"),
    apply_work("4", "楼宇施工许可申请"),
    apply_adorn("5", "楼宇装饰工程竣工验收申请"),
    ;

    private String key;
    private String Value;

    ApplyTypeEnum(String key, String Value) {
        this.key = key;
        this.Value = Value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    /**
     * 根据key查询对应的value
     *
     * @param key
     * @return
     */
    public static String queryValueByKey(String key) {
        for (ApplyTypeEnum t : ApplyTypeEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }
}
