package com.wolves.common;

/**
 * Created by Administrator on 2019/4/1.
 */
public enum TimeEnum {


    ;

    private String key;
    private String Value;

    TimeEnum(String key, String Value) {
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
        for (TimeEnum t : TimeEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }
}
