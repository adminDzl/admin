package com.wolves.common;

/**
 * 报修/装修/施工
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

    /**
     * 装修申请
     */
    public static final String DECORATE = "/mobile/api/createZhuangxiu";
    
    /*
     * 申请一卡通
     */
    public static final String APPLY="/mobile/api/createCardapply";

    /*
     * 申请车库包月/续费
     */
    public static final String CARMONTH="/mobile/api/createGarage";
    
    /*
     * 申请施工许可
     */
    public static final String CONSTRUCTION="/mobile/api/createConstruction";
    
    public static final String USER_NAME = "APP";
    public static final String PASS_WARD = "a123456";

    /**
     * 固定值
     */
    public static final String APP_TYPE = "ITSM_APP";


}
