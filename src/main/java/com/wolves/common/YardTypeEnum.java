package com.wolves.common;

/**
 * Created by gf on 2019/4/27.
 */
public enum YardTypeEnum {

    MEETING("1", "会议室"),
    MOVEMENT("2", "活动室"),
    FITNESS("3", "健身房"),
    ;

    private String key;
    private String Value;

    YardTypeEnum(String key, String Value) {
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
        for (YardTypeEnum t : YardTypeEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }
}
