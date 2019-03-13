package com.wolves.dto.pay;

/**
 * Created by gf on 2019/3/13.
 */
public class WxResultDTO {

    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * packages!!!不是package, 统一下单接口返回的 prepay_id
     */
    private String packages;
    /**
     * 签名类型，默认为MD5
     */
    private String signType;
    /**
     * 签名
     */
    private String paySign;
    /**
     * 统一下单接口返回的 prepay_id
     */
    private String prepayId;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    @Override
    public String toString() {
        return "WxResultDTO{" +
                "timeStamp='" + timeStamp + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", packages='" + packages + '\'' +
                ", signType='" + signType + '\'' +
                ", paySign='" + paySign + '\'' +
                ", prepayId='" + prepayId + '\'' +
                '}';
    }
}
