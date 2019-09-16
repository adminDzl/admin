package com.wolves.util.wxUtil;

import com.jpay.ext.kit.StrKit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zej on 2019/8/5.
 */
public class WxPayApiConfigKitUtil {
    private static final ThreadLocal<String> TL = new ThreadLocal();
    private static final Map<String, WxPayApiConfigUtil> CFG_MAP = new ConcurrentHashMap();
    private static final String DEFAULT_CFG_KEY = "_default_ijpay_key_";

    public WxPayApiConfigKitUtil() {
    }

    public static WxPayApiConfigUtil putApiConfig(WxPayApiConfigUtil WxPayApiConfigUtil) {
        System.out.print("WxPayApiConfigUtil="+WxPayApiConfigUtil+"CFG_MAP.size()"+CFG_MAP.size());
        if(CFG_MAP.size() == 0) {
            CFG_MAP.put("_default_ijpay_key_", WxPayApiConfigUtil);
        }

        return (com.wolves.util.wxUtil.WxPayApiConfigUtil)CFG_MAP.put(WxPayApiConfigUtil.getAppId(), WxPayApiConfigUtil);
    }

    public static WxPayApiConfigUtil setThreadLocalWxPayApiConfigUtil(WxPayApiConfigUtil WxPayApiConfigUtil) {
        return putApiConfig(WxPayApiConfigUtil);
    }

    public static WxPayApiConfigUtil removeApiConfig(WxPayApiConfigUtil WxPayApiConfigUtil) {
        return removeApiConfig(WxPayApiConfigUtil.getAppId());
    }

    public static WxPayApiConfigUtil removeApiConfig(String appId) {
        return (WxPayApiConfigUtil)CFG_MAP.remove(appId);
    }

    public static void setThreadLocalAppId(String appId) {
        if(StrKit.isBlank(appId)) {
            appId = ((WxPayApiConfigUtil)CFG_MAP.get("_default_ijpay_key_")).getAppId();
        }

        TL.set(appId);
    }

    public static void removeThreadLocalAppId() {
        TL.remove();
    }

    public static String getAppId() {
        String appId = (String)TL.get();
        if(StrKit.isBlank(appId)) {
            System.out.println(CFG_MAP.get("_default_ijpay_key_")+"++++++++++");
            appId = ((WxPayApiConfigUtil)CFG_MAP.get("_default_ijpay_key_")).getAppId();
        }

        return appId;
    }

    public static WxPayApiConfigUtil getWxPayApiConfigUtil() {
        String appId = getAppId();
        return getApiConfig(appId);
    }

    public static WxPayApiConfigUtil getApiConfig(String appId) {
        WxPayApiConfigUtil cfg = (WxPayApiConfigUtil)CFG_MAP.get(appId);
        if(cfg == null) {
            throw new IllegalStateException("需事先调用 WxPayApiConfigUtilKit.putApiConfig(WxPayApiConfigUtil) 将 appId对应的 WxPayApiConfigUtil 对象存入，才可以使用 WxPayApiConfigUtilKit.getWxPayApiConfigUtil() 的系列方法");
        } else {
            return cfg;
        }
    }
}
