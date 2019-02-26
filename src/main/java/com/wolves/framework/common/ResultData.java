package com.wolves.framework.common;

/**
 * @author xulu
 */
public class ResultData extends Result{

    public ResultData() {
        result = ResultCode.SUCCESS;
        msg = "";
    }

    public ResultData(int result, String msg, Object data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }
}
