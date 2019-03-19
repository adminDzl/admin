package com.wolves.service.system;

import com.wolves.dao.DaoSupport;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.util.PageData;
import com.wolves.util.SmsUtil;
import com.wolves.util.UuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author xulu
 * @date 2019/2/28
 * @link https://github.com/xulu163
 */
@Service("smsService")
public class SmsService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 发送短信验证码，10分钟有效，十分钟最多发五条
     * @param tel 手机号
     * @param type 发送类别
     * @param code 验证码
     */
    public Result sendSms(String tel, Integer type, String code, String msg){
        Result result = new Result("发送失败");
        Integer smsStatus = 0;
        PageData pd = new PageData();
        pd.put("PHONE", tel);
        pd.put("MIN", 10);
        List<PageData> list = (List<PageData>)dao.findForList("SmsLogMapper.listLastMin", pd);
        if(!CollectionUtils.isEmpty(list) && list.size() > 5){
            result.setMsg("短信发送过多，请稍后重试");
            return result;
        }
        String msgReturn = "";
        try{
            msgReturn = SmsUtil.sendByHuaXin(tel, msg);
        } catch (Exception e){
            logger.error("发送短信失败", e);
        }
        if("Success".equals(msgReturn)){
            result.setMsg("发送成功");
            result.setResult(ResultCode.SUCCESS);
            smsStatus = 1;
        } else {
            result.setMsg("发送失败");
            result.setResult(ResultCode.FAIL);
        }
        PageData pdNew = new PageData();
        pdNew.put("SMS_TYPE", type);
        pdNew.put("PHONE", tel);
        pdNew.put("CODE", code);
        pdNew.put("CONTENT", msg);
        pdNew.put("SMS_STATUS", smsStatus);
        dao.save("SmsLogMapper.save", pdNew);
        return result;
    }

    /**
     * 根据手机号查询最新的短信发送记录
     * @param phone
     */
    public PageData selectOneByPhone(String phone){
        PageData pd = new PageData();
        pd.put("PHONE", phone);
        return (PageData)dao.findForObject("SmsLogMapper.selectOneByPhone", pd);
    }
}
