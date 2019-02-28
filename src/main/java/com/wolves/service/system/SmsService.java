package com.wolves.service.system;

import com.wolves.dao.DaoSupport;
import com.wolves.framework.common.Result;
import com.wolves.util.PageData;
import com.wolves.util.SmsUtil;
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
     *
     * @param tel 手机号
     * @param type 发送类别
     * @param code 验证码
     */
    public Result sendSms(String tel, Integer type, String code, String msg){
        Result result = new Result("发送失败");
        Integer smsStstus = 0;
        //短信发送过多提醒
        PageData pd = new PageData();
        pd.put("PHONE", tel);
        pd.put("MIN", 3);
        List<PageData> list = (List<PageData>)dao.findForList("SmsLogMapper.listLastMin", pd);
        if(!CollectionUtils.isEmpty(list) && list.size() > 3){
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
            result.setResult(0);
            smsStstus = 1;
        }
        PageData pdNew = new PageData();
        pdNew.put("SMS_TYPE", type);
        pdNew.put("PHONE", tel);
        pdNew.put("CODE", code);
        pdNew.put("CONTENT", msg);
        pdNew.put("CREAT_TIME", new Date());
        pdNew.put("SMS_STATUS", smsStstus);
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
