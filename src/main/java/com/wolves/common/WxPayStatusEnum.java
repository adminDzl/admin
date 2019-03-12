package com.wolves.common;

/**
 * Created by gf on 2019/3/12.
 */
public enum  WxPayStatusEnum {

    INIT("0", "待支付"),
    SUCCESS("1", "支付成功"),
    REJECT("2", "支付失败"),
            ;

    private String key;
    private String Value;

    WxPayStatusEnum(String key, String Value) {
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
     * @param key
     * @return
     */
    public static String queryValueByKey(String key) {
        for (WxPayStatusEnum t : WxPayStatusEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }
}
