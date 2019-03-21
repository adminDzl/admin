package com.wolves.common;

/**
 * Created by Administrator on 2019/3/21.
 */
public enum  SmsTypeEnum {

    register("1", "注册"),
    login("2", "登录"),
    forget("3", "遗忘"),
    ;

    private String key;
    private String Value;

    SmsTypeEnum(String key, String Value) {
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
        for (SmsTypeEnum t : SmsTypeEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }
}
