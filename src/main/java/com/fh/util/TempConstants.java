package com.fh.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xulu on 2016/4/7.
 */
public class TempConstants {
    private static Map tempMap = new HashMap();
    public static Map getTempMap(){
        if(null == tempMap || tempMap.size() == 0){
            tempMap.put("chargeTemp", PropUtils.getConfigMessage("CHARGE_TEMP_ID"));
            tempMap.put("investTemp", PropUtils.getConfigMessage("INVEST_TEMP_ID"));
            tempMap.put("creditTemp", PropUtils.getConfigMessage("CREDIT_TEMP_ID"));
            tempMap.put("levelTemp", PropUtils.getConfigMessage("LEVEL_TEMP_ID"));
            tempMap.put("repayTemp", PropUtils.getConfigMessage("REPAY_TEMP_ID"));
            tempMap.put("depositsApplyTemp", PropUtils.getConfigMessage("DEPOSITS_APPLY_TEMP_ID"));
            tempMap.put("depositsResultTemp", PropUtils.getConfigMessage("DEPOSITS_RESULT_TEMP_ID"));
            tempMap.put("friendTemp", PropUtils.getConfigMessage("FRIEND_TEMP_ID"));
            tempMap.put("returnTemp", PropUtils.getConfigMessage("RETURN_TEMP_ID"));
        }
        return tempMap;
    }
}
