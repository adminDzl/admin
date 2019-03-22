package com.wolves.framework.common;

/**
 * @author xulu
 */
public class Result<T> {

    /**
     * 操作结果 0：成功、  1：业务错误(msg写失败描述)、 -1：系统异常
     */
    public int result;
    public String msg;
    protected T data;

    public Result(){

    }

    public Result(String msg) {
        this.result = ResultCode.FAIL;
        this.msg = msg;
    }

    public Result(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
