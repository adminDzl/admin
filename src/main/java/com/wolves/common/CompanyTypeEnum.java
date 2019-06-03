package com.wolves.common;

/**
 * Created by Administrator on 2019/3/21.
 */
public enum CompanyTypeEnum {

    inner("1", "内部企业"),
    out("2", "外部企业"),

    ;

    private String key;
    private String Value;

    CompanyTypeEnum(String key, String Value) {
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
        for (CompanyTypeEnum t : CompanyTypeEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }
}
