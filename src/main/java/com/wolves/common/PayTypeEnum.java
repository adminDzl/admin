package com.wolves.common;

/**
 * Created by gf on 2019/3/13.
 */
public enum PayTypeEnum {

    //1-水费，2-电费，3-物业费，4-停车费，5-场地预定费，6-一卡通费用
    WATER_RETE("1", "水费"),
    ELECTRICITY("2", "电费"),
    HOA_RETE("3", "物业费"),
    PARKING_RETE("4", "停车费"),
    HIRE("5", "场地预定费"),
    SMOOTH_RETE("6", "一卡通费用"),

    ;

    private String key;
    private String Value;

    PayTypeEnum(String key, String Value) {
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
        for (PayTypeEnum t : PayTypeEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }
}
