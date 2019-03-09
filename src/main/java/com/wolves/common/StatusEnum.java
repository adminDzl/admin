package com.wolves.common;

/**
 * Created by gf on 2019/3/9.
 */
public enum StatusEnum {

    INIT("0", "默认"),
    SUCCESS("1", "成功"),
    REJECT("2", "驳回"),
    ;

    private String key;
    private String Value;

    StatusEnum(String key, String Value) {
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
        for (StatusEnum t : StatusEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }
}
