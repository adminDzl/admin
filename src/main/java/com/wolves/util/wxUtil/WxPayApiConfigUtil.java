package com.wolves.util.wxUtil;

import com.jpay.ext.kit.PaymentKit;
import com.jpay.ext.kit.StrKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zej on 2019/8/5.
 */
public class WxPayApiConfigUtil implements Serializable {
    private static final long serialVersionUID = -6447075676732210047L;
    private String appId;
    private String mchId;
    private String subAppId;
    private String subMchId;
    private String paternerKey;
    private String nonceStr;
    private String body;
    private String attach;
    private String transactionId;
    private String outTradeNo;
    private String outRefundNo;
    private String refundFee;
    private String totalFee;
    private String spbillCreateIp;
    private String notifyUrl;
    private WxPayApi.TradeType tradeType;
    private String openId;
    private String subOpenId;
    private String authCode;
    private String sceneInfo;
    private String planId;
    private String contractCode;
    private String requestSerial;
    private String contractDisplayAccount;
    private String version;
    private String timestamp;
    private String returnApp;
    private String returnWeb;
    private String contractNotifyUrl;
    private String contractId;
    private WxPayApiConfigUtil.PayModel payModel;
    private String profitSharing;
    private WxPayApiConfigUtil.SignType signType;
    private String deviceInfo;
    private String detail;
    private String feeType;
    private String timeStart;
    private String timeExpire;
    private String goodsTag;
    private String limitPay;
    private String receipt;
    private String beginTime;
    private String endTime;
    private int offset;
    private int limit;

    private WxPayApiConfigUtil() {
    }

    public static WxPayApiConfigUtil New() {
        return new WxPayApiConfigUtil();
    }

    public Map<String, String> createMap() {
        HashMap map = new HashMap();
        if(this.getPayModel().equals(WxPayApiConfigUtil.PayModel.SERVICEMODE)) {
            map.put("sub_mch_id", this.getSubMchId());
            if(StrKit.notBlank(this.getSubAppId())) {
                map.put("sub_appid", this.subAppId);
            }
        }

        if(this.getTradeType().equals(WxPayApi.TradeType.JSAPI)) {
            if(StrKit.notBlank(this.getSubAppId())) {
                map.put("sub_appid", this.subAppId);
                map.put("sub_openid", this.getSubOpenId());
            } else {
                map.put("openid", this.getOpenId());
            }
        }

        if(this.getTradeType().equals(WxPayApi.TradeType.MWEB)) {
            if(StrKit.isBlank(this.getSceneInfo())) {
                throw new IllegalArgumentException("微信H5支付 scene_info 不能同时为空");
            }

            map.put("scene_info", this.getSceneInfo());
        }

        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        map.put("nonce_str", this.getNonceStr());
        map.put("out_trade_no", this.getOutTradeNo());
        map.put("out_refund_no", this.getOutRefundNo());
        map.put("total_fee", this.getTotalFee());
        map.put("refund_fee", this.getTotalFee());

        return map;
    }

    public Map<String, String> build() {
        Map map = this.createMap();
        System.out.print("singP="+map);
        map.put("sign", PaymentKit.createSign(map, this.getPaternerKey()));
        return map;
    }

    public Map<String, String> build(WxPayApiConfigUtil.SignType signType) throws Exception {
        Map map = this.createMap();
        if(WxPayApiConfigUtil.SignType.MD5 == signType) {
            map.put("sign_type", "MD5");
        } else {
            map.put("sign_type", "HMAC-SHA256");
        }

        map.put("sign", PaymentKit.createSign(map, this.getPaternerKey(), WxPayApiConfig.SignType.MD5));
        return map;
    }

    public Map<String, String> orderQueryBuild() {
        HashMap map = new HashMap();
        if(this.getPayModel().equals(WxPayApiConfigUtil.PayModel.SERVICEMODE)) {
            map.put("sub_mch_id", this.getSubMchId());
            map.put("sub_appid", this.getSubAppId());
        }

        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        if(StrKit.notBlank(this.getTransactionId())) {
            map.put("transaction_id", this.getTransactionId());
        } else {
            if(StrKit.isBlank(this.getOutTradeNo())) {
                throw new IllegalArgumentException("out_trade_no,transaction_id 不能同时为空");
            }

            map.put("out_trade_no", this.getOutTradeNo());
        }

        map.put("nonce_str", String.valueOf(System.currentTimeMillis()));
        map.put("sign", PaymentKit.createSign(map, this.getPaternerKey()));
        return map;
    }

    public Map<String, String> entrustwebBuild() throws UnsupportedEncodingException {
        HashMap map = new HashMap();
        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        map.put("plan_id", this.getPlanId());
        map.put("contract_code", this.getContractCode());
        map.put("request_serial", this.getRequestSerial());
        map.put("contract_display_account", this.getContractDisplayAccount());
        map.put("notify_url", this.getNotifyUrl());
        map.put("version", this.getVersion());
        map.put("timestamp", this.getTimestamp());
        map.put("sign", PaymentKit.createSign(map, this.getPaternerKey()));
        Iterator var2 = map.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry param = (Map.Entry)var2.next();
            String key = (String)param.getKey();
            String value = (String)param.getValue();
            value = PaymentKit.urlEncode(value);
            map.put(key, value);
        }

        return map;
    }

    public Map<String, String> contractorderBuild() {
        HashMap map = new HashMap();
        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        map.put("contract_appid", this.getAppId());
        map.put("contract_mchid", this.getMchId());
        map.put("out_trade_no", this.getOutTradeNo());
        map.put("nonce_str", this.getNonceStr());
        map.put("body", this.getBody());
        map.put("attach", this.getAttach());
        map.put("notify_url", this.getNotifyUrl());
        map.put("total_fee", this.getTotalFee());
        map.put("spbill_create_ip", this.getSpbillCreateIp());
        map.put("trade_type", this.getTradeType().name());
        if(this.getTradeType().equals(WxPayApi.TradeType.JSAPI)) {
            map.put("openid", this.getOpenId());
        }

        map.put("plan_id", this.getPlanId());
        map.put("contract_code", this.getContractCode());
        map.put("request_serial", this.getRequestSerial());
        map.put("contract_display_account", this.getContractDisplayAccount());
        map.put("contract_notify_url", this.getContractNotifyUrl());
        map.put("sign", PaymentKit.createSign(map, this.getPaternerKey()));
        return map;
    }

    public Map<String, String> querycontractBuild() {
        HashMap map = new HashMap();
        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        if(StrKit.notBlank(this.getPlanId())) {
            map.put("plan_id", this.getPlanId());
            map.put("contract_code", this.getContractCode());
        } else {
            map.put("contract_id", this.getContractId());
        }

        map.put("version", this.getVersion());
        map.put("sign", PaymentKit.createSign(map, this.getPaternerKey()));
        return map;
    }

    public Map<String, String> pappayapplyBuild() {
        HashMap map = new HashMap();
        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        map.put("nonce_str", this.getNonceStr());
        map.put("body", this.getBody());
        map.put("attach", this.getAttach());
        map.put("out_trade_no", this.getOutTradeNo());
        map.put("total_fee", this.getTotalFee());
        map.put("spbill_create_ip", this.getSpbillCreateIp());
        map.put("notify_url", this.getNotifyUrl());
        map.put("trade_type", this.getTradeType().name());
        map.put("contract_id", this.getContractId());
        map.put("sign", PaymentKit.createSign(map, this.getPaternerKey()));
        return map;
    }

    public String getAppId() {
        if(StrKit.isBlank(this.appId)) {
            throw new IllegalArgumentException("appId 未被赋值");
        } else {
            return this.appId;
        }
    }

    public WxPayApiConfigUtil setAppId(String appId) {
        if(StrKit.isBlank(appId)) {
            throw new IllegalArgumentException("appId 值不能为空");
        } else {
            this.appId = appId;
            return this;
        }
    }

    public String getMchId() {
        if(StrKit.isBlank(this.mchId)) {
            throw new IllegalArgumentException("mchId 未被赋值");
        } else {
            return this.mchId;
        }
    }

    public WxPayApiConfigUtil setMchId(String mchId) {
        if(StrKit.isBlank(mchId)) {
            throw new IllegalArgumentException("mchId 值不能为空");
        } else {
            this.mchId = mchId;
            return this;
        }
    }

    public String getSubAppId() {
        return this.subAppId;
    }

    public WxPayApiConfigUtil setSubAppId(String subAppId) {
        if(StrKit.isBlank(subAppId)) {
            throw new IllegalArgumentException("subAppId 值不能为空");
        } else {
            this.subAppId = subAppId;
            return this;
        }
    }

    public String getSubMchId() {
        if(StrKit.isBlank(this.subMchId)) {
            throw new IllegalArgumentException("subMchId 未被赋值");
        } else {
            return this.subMchId;
        }
    }

    public WxPayApiConfigUtil setSubMchId(String subMchId) {
        if(StrKit.isBlank(subMchId)) {
            throw new IllegalArgumentException("subMchId 值不能为空");
        } else {
            this.subMchId = subMchId;
            return this;
        }
    }

    public String getNonceStr() {
        if(StrKit.isBlank(this.nonceStr)) {
            this.nonceStr = String.valueOf(System.currentTimeMillis());
        }

        return this.nonceStr;
    }

    public WxPayApiConfigUtil setNonceStr(String nonceStr) {
        if(StrKit.isBlank(nonceStr)) {
            throw new IllegalArgumentException("nonceStr 值不能为空");
        } else {
            this.nonceStr = nonceStr;
            return this;
        }
    }

    public String getBody() {
        if(StrKit.isBlank(this.body)) {
            throw new IllegalArgumentException("body 未被赋值");
        } else {
            return this.body;
        }
    }

    public WxPayApiConfigUtil setBody(String body) {
        if(StrKit.isBlank(body)) {
            throw new IllegalArgumentException("body 值不能为空");
        } else {
            this.body = body;
            return this;
        }
    }

    public String getAttach() {
        return this.attach;
    }

    public WxPayApiConfigUtil setAttach(String attach) {
        if(StrKit.isBlank(attach)) {
            throw new IllegalArgumentException("attach 值不能为空");
        } else {
            this.attach = attach;
            return this;
        }
    }

    public String getOutTradeNo() {
        if(StrKit.isBlank(this.outTradeNo)) {
            throw new IllegalArgumentException("outTradeNo 未被赋值");
        } else {
            return this.outTradeNo;
        }
    }

    public WxPayApiConfigUtil setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getTotalFee() {
        if(StrKit.isBlank(this.totalFee)) {
            throw new IllegalArgumentException("totalFee 未被赋值");
        } else {
            return this.totalFee;
        }
    }

    public WxPayApiConfigUtil setTotalFee(String totalFee) {
        if(StrKit.isBlank(totalFee)) {
            throw new IllegalArgumentException("totalFee 值不能为空");
        } else {
            this.totalFee = totalFee;
            return this;
        }
    }

    public String getSpbillCreateIp() {
        if(StrKit.isBlank(this.spbillCreateIp)) {
            throw new IllegalArgumentException("spbillCreateIp 未被赋值");
        } else {
            return this.spbillCreateIp;
        }
    }

    public WxPayApiConfigUtil setSpbillCreateIp(String spbillCreateIp) {
        if(StrKit.isBlank(spbillCreateIp)) {
            throw new IllegalArgumentException("spbillCreateIp 值不能为空");
        } else {
            this.spbillCreateIp = spbillCreateIp;
            return this;
        }
    }

    public String getNotifyUrl() {
        if(StrKit.isBlank(this.notifyUrl)) {
            throw new IllegalArgumentException("notifyUrl 未被赋值");
        } else {
            return this.notifyUrl;
        }
    }

    public WxPayApiConfigUtil setNotifyUrl(String notifyUrl) {
        if(StrKit.isBlank(notifyUrl)) {
            throw new IllegalArgumentException("notifyUrl 值不能为空");
        } else {
            this.notifyUrl = notifyUrl;
            return this;
        }
    }

    public WxPayApi.TradeType getTradeType() {
        if(this.tradeType == null) {
            throw new IllegalArgumentException("tradeType 未被赋值");
        } else {
            return this.tradeType;
        }
    }

    public WxPayApiConfigUtil setTradeType(WxPayApi.TradeType tradeType) {
        if(tradeType == null) {
            throw new IllegalArgumentException("mchId 值不能为空");
        } else {
            this.tradeType = tradeType;
            return this;
        }
    }

    public String getOpenId() {
        if(StrKit.isBlank(this.openId)) {
            throw new IllegalArgumentException("openId 未被赋值");
        } else {
            return this.openId;
        }
    }

    public WxPayApiConfigUtil setOpenId(String openId) {
        if(StrKit.isBlank(openId)) {
            throw new IllegalArgumentException("openId 值不能为空");
        } else {
            this.openId = openId;
            return this;
        }
    }

    public String getSubOpenId() {
        if(StrKit.isBlank(this.subOpenId)) {
            throw new IllegalArgumentException("subOpenId 未被赋值");
        } else {
            return this.subOpenId;
        }
    }

    public WxPayApiConfigUtil setSubOpenId(String subOpenId) {
        if(StrKit.isBlank(subOpenId)) {
            throw new IllegalArgumentException("subOpenId 值不能为空");
        } else {
            this.subOpenId = subOpenId;
            return this;
        }
    }

    public String getPaternerKey() {
        if(StrKit.isBlank(this.paternerKey)) {
            throw new IllegalArgumentException("paternerKey 未被赋值");
        } else {
            return this.paternerKey;
        }
    }

    public WxPayApiConfigUtil setPaternerKey(String paternerKey) {
        if(StrKit.isBlank(paternerKey)) {
            throw new IllegalArgumentException("paternerKey 值不能为空");
        } else {
            this.paternerKey = paternerKey;
            return this;
        }
    }

    public WxPayApiConfigUtil.PayModel getPayModel() {
        if(this.payModel == null) {
            this.payModel = WxPayApiConfigUtil.PayModel.BUSINESSMODEL;
        }

        return this.payModel;
    }

    public WxPayApiConfigUtil setPayModel(WxPayApiConfigUtil.PayModel payModel) {
        if(payModel == null) {
            payModel = WxPayApiConfigUtil.PayModel.BUSINESSMODEL;
        }

        this.payModel = payModel;
        return this;
    }

    public WxPayApiConfigUtil.SignType getSignType() {
        if(this.signType == null) {
            this.signType = WxPayApiConfigUtil.SignType.MD5;
        }

        return this.signType;
    }

    public WxPayApiConfigUtil setSignType(WxPayApiConfigUtil.SignType signType) {
        if(signType == null) {
            signType = WxPayApiConfigUtil.SignType.MD5;
        }

        this.signType = signType;
        return this;
    }

    public String getAuthCode() {
        if(StrKit.isBlank(this.authCode)) {
            throw new IllegalArgumentException("authCode 未被赋值");
        } else {
            return this.authCode;
        }
    }

    public WxPayApiConfigUtil setAuthCode(String authCode) {
        if(StrKit.isBlank(this.paternerKey)) {
            throw new IllegalArgumentException("authCode 值不能为空");
        } else {
            this.authCode = authCode;
            return this;
        }
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public WxPayApiConfigUtil setTransactionId(String transactionId) {
        if(StrKit.isBlank(transactionId)) {
            throw new IllegalArgumentException("transactionId 值不能为空");
        } else {
            this.transactionId = transactionId;
            return this;
        }
    }

    public String getSceneInfo() {
        return this.sceneInfo;
    }

    public WxPayApiConfigUtil setSceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
        return this;
    }

    public String getPlanId() {
        if(StrKit.isBlank(this.planId)) {
            throw new IllegalArgumentException("planId 未被赋值");
        } else {
            return this.planId;
        }
    }

    public WxPayApiConfigUtil setPlanId(String planId) {
        if(StrKit.isBlank(planId)) {
            throw new IllegalArgumentException("planId 值不能为空");
        } else {
            this.planId = planId;
            return this;
        }
    }

    public String getContractCode() {
        if(StrKit.isBlank(this.contractCode)) {
            throw new IllegalArgumentException("contractCode 未被赋值");
        } else {
            return this.contractCode;
        }
    }

    public WxPayApiConfigUtil setContractCode(String contractCode) {
        if(StrKit.isBlank(contractCode)) {
            throw new IllegalArgumentException("contractCode 值不能为空");
        } else {
            this.contractCode = contractCode;
            return this;
        }
    }

    public String getRequestSerial() {
        if(StrKit.isBlank(this.requestSerial)) {
            throw new IllegalArgumentException("requestSerial 未被赋值");
        } else {
            return this.requestSerial;
        }
    }

    public WxPayApiConfigUtil setRequestSerial(String requestSerial) {
        if(StrKit.isBlank(requestSerial)) {
            throw new IllegalArgumentException("requestSerial 值不能为空");
        } else {
            this.requestSerial = requestSerial;
            return this;
        }
    }

    public String getContractDisplayAccount() {
        if(StrKit.isBlank(this.contractDisplayAccount)) {
            throw new IllegalArgumentException("contractDisplayAccount 未被赋值");
        } else {
            return this.contractDisplayAccount;
        }
    }

    public WxPayApiConfigUtil setContractDisplayAccount(String contractDisplayAccount) {
        if(StrKit.isBlank(contractDisplayAccount)) {
            throw new IllegalArgumentException("contractDisplayAccount 值不能为空");
        } else {
            this.contractDisplayAccount = contractDisplayAccount;
            return this;
        }
    }

    public String getVersion() {
        if(StrKit.isBlank(this.version)) {
            this.version = "1.0";
        }

        return this.version;
    }

    public WxPayApiConfigUtil setVersion(String version) {
        if(StrKit.isBlank(version)) {
            throw new IllegalArgumentException("version 值不能为空");
        } else {
            this.version = version;
            return this;
        }
    }

    public String getTimestamp() {
        if(StrKit.isBlank(this.timestamp)) {
            this.timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
        }

        return this.timestamp;
    }

    public WxPayApiConfigUtil setTimestamp(String timestamp) {
        if(StrKit.isBlank(timestamp)) {
            throw new IllegalArgumentException("timestamp 值不能为空");
        } else if(timestamp.length() != 10) {
            throw new IllegalArgumentException("timestamp 值必须为10位");
        } else {
            this.timestamp = timestamp;
            return this;
        }
    }

    public String getReturnApp() {
        return this.returnApp;
    }

    public WxPayApiConfigUtil setReturnApp(String returnApp) {
        this.returnApp = returnApp;
        return this;
    }

    public String getReturnWeb() {
        return this.returnWeb;
    }

    public WxPayApiConfigUtil setReturnWeb(String returnWeb) {
        this.returnWeb = returnWeb;
        return this;
    }

    public String getContractNotifyUrl() {
        if(StrKit.isBlank(this.contractNotifyUrl)) {
            throw new IllegalArgumentException("contractNotifyUrl 未被赋值");
        } else {
            return this.contractNotifyUrl;
        }
    }

    public WxPayApiConfigUtil setContractNotifyUrl(String contractNotifyUrl) {
        this.contractNotifyUrl = contractNotifyUrl;
        return this;
    }

    public String getContractId() {
        if(StrKit.isBlank(this.contractId)) {
            throw new IllegalArgumentException("contractId 未被赋值");
        } else {
            return this.contractId;
        }
    }

    public WxPayApiConfigUtil setContractId(String contractId) {
        this.contractId = contractId;
        return this;
    }

    public String getProfitSharing() {
        return this.profitSharing;
    }

    public WxPayApiConfigUtil setProfitSharing(String profitSharing) {
        this.profitSharing = profitSharing;
        return this;
    }

    public String getDeviceInfo() {
        return this.deviceInfo;
    }

    public WxPayApiConfigUtil setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public String getDetail() {
        return this.detail;
    }

    public WxPayApiConfigUtil setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public String getFeeType() {
        return this.feeType;
    }

    public WxPayApiConfigUtil setFeeType(String feeType) {
        this.feeType = feeType;
        return this;
    }

    public String getTimeStart() {
        return this.timeStart;
    }

    public WxPayApiConfigUtil setTimeStart(String timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    public String getTimeExpire() {
        return this.timeExpire;
    }

    public WxPayApiConfigUtil setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
        return this;
    }

    public String getGoodsTag() {
        return this.goodsTag;
    }

    public WxPayApiConfigUtil setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
        return this;
    }

    public String getLimitPay() {
        return this.limitPay;
    }

    public WxPayApiConfigUtil setLimitPay(String limitPay) {
        this.limitPay = limitPay;
        return this;
    }

    public String getReceipt() {
        return this.receipt;
    }

    public WxPayApiConfigUtil setReceipt(String receipt) {
        this.receipt = receipt;
        return this;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public WxPayApiConfigUtil setBeginTime(String beginTime) {
        this.beginTime = beginTime;
        return this;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public WxPayApiConfigUtil setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public int getOffset() {
        return this.offset;
    }

    public WxPayApiConfigUtil setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public int getLimit() {
        return this.limit;
    }

    public WxPayApiConfigUtil setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public WxPayApiConfigUtil setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
        return this;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public WxPayApiConfigUtil setRefundFee(String refundFee) {
        this.refundFee = refundFee;
        return this;

    }

    public static enum SignType {
        MD5,
        HMAC_SHA256;

        private SignType() {
        }
    }

    public static enum PayModel {
        BUSINESSMODEL,
        SERVICEMODE;

        private PayModel() {
        }
    }

    /*public static String packageSign(Map<String, String> params, boolean urlEncoder) {
        TreeMap sortedParams = new TreeMap(params);
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        Iterator var5 = sortedParams.entrySet().iterator();

        while(var5.hasNext()) {
            Map.Entry param = (Map.Entry)var5.next();
            String value = (String)param.getValue();
            if(!StrKit.isBlank(value)) {
                if(first) {
                    first = false;
                } else {
                    sb.append("&");
                }

                sb.append((String)param.getKey()).append("=");
                if(urlEncoder) {
                    try {
                        value = urlEncode(value);
                    } catch (UnsupportedEncodingException var9) {
                        ;
                    }
                }

                sb.append(value);
            }
        }
        return sb.toString();
    }
    public static String urlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, Charsets.UTF_8.name()).replace("+", "%20");
    }*/
   /* public  static void main(String[] args){
        HashMap map = new HashMap();
        map.put("appid", "wx5f7abea70c318ab0");
        map.put("mch_id", "1548223921");
        map.put("nonce_str", "1565020061863");
        map.put("out_trade_no", "123456678");
        map.put("out_refund_no","4d90629004bc49b9861dde02d035a523");
        map.put("total_fee", "12");
        map.put("refund_fee", "12");
        map.put("notify_url", "http:139.196.111.53:18080/admin/api/pay/wxPayNotify");
        String stringA= packageSign(map,false);
        String stringSignTemp = stringA + "&key=" + "quLjLa0LhFnbqJfY9gfySJKX9oLEtGOs";
        System.out.println("stringSignTemp="+stringSignTemp);
        String sign= HashKit.md5(stringSignTemp).toUpperCase();
        System.out.println("sign="+sign);
    }*/

}
