package com.wolves.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gf on 2019/3/9.
 */
public enum LicensePlateEnum {

    //城市
    beijing("北京","京"),
    tianjin("天津", "津"),
    hebei("河北", "冀"),
    shanxi("山西", "晋"),
    neimenggu("内蒙古", "蒙"),
    liaoning("辽宁", "辽"),
    jilin("吉林", "吉"),

    heilongjiang("黑龙江", "黑"),
    shanghai("上海", "沪"),
    jiangsu("江苏", "苏"),
    zhejiang("浙江", "浙"),
    anhui("安徽", "皖"),
    fujian("福建", "闽"),
    jiangxi("江西", "赣"),

    shandong("山东", "鲁"),
    henan("河南", "豫"),
    hubei("湖北", "鄂"),
    hunan("湖南", "湘"),
    guangdong("广东", "粤"),
    guangxi("广西", "桂"),
    hainan("海南", "琼"),

    chongqing("重庆", "渝"),
    sichuan("四川", "川"),
    guizhou("贵州", "黔"),
    yunnan("云南", "滇"),
    xizang("西藏", "藏"),
    shanxii("陕西", "陕"),

    gansu("甘肃", "甘"),
    qinghai("青海", "青"),
    ningxia("宁夏", "宁"),
    xinjiang("新疆", "新"),
    taiwan("台湾", "台"),
    xianggang("香港", "港"),
    aomen("澳门", "澳"),
    ;

    private String key;
    private String Value;

    LicensePlateEnum(String key, String Value) {
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
     * 查询所有key,value
     */
    public static Map<String, Object> queryAll() {
        Map<String, Object> tempMap = new HashMap<String, Object>();
        for (LicensePlateEnum t : LicensePlateEnum.values()) {
            tempMap.put(t.getKey(), t.getValue());
        }
        return tempMap;
    }

    /**
     * 根据key查询对应的value
     * @param key
     * @return
     */
    public static String queryValueByKey(String key) {
        for (LicensePlateEnum t : LicensePlateEnum.values()) {
            if (t.getKey().equals(key)) {
                return t.getValue();
            }
        }
        return "";
    }

    /**
     * 根据value查询对应的key
     * @param value
     * @return
     */
    public static String queryKeyByValue(String value) {
        for (LicensePlateEnum t : LicensePlateEnum.values()) {
            if (t.getValue().equals(value)) {
                return t.getKey();
            }
        }
        return "";
    }
}
