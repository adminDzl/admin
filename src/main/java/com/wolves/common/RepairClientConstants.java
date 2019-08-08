package com.wolves.common;

/**
 * 报修
 * Created by Administrator on 2019/8/8.
 */
public class RepairClientConstants {

    /**
     * 地址
     */
    public static final String ADDRESS = "http://servicehot.wxo-park.com:8080";

    /**
     * 登录
     */
    public static final String LOGIN = "/mobile/api/login";

    /**
     * 工单创建
     */
    public static final String CREATE = "/mobile/api/createComplaint";

    /**
     * 工单查询列表
     */
    public static final String QUERY = "/mobile/api/listWorkorder";

    /**
     * 工单日志信息
     */
    public static final String LOG = "/mobile/api/listProcessLog";

    /**
     * 工单附件信息
     */
    public static final String ATTACH = "/mobile/api/listAttachment";

    /**
     * 撤销工单
     */
    public static final String STOP = "/mobile/api/stopJobs";

    public static final String USER_NAME = "APP";
    public static final String PASS_WARD = "a123456";

    /**
     * 固定值
     */
    public static final String APP_TYPE = "ITSM_APP";
}
