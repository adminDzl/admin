package com.wolves.common;

/**
 * Created by Administrator on 2019/3/17.
 */
public enum NewsTypeEnum {

    news("1", "新闻"),
    declare("2", "项目申报"),
    ;

    private String key;
    private String Value;

    NewsTypeEnum(String key, String Value) {
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
        for (NewsTypeEnum t : NewsTypeEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }
}
