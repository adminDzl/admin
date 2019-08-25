package com.wolves.service.system.decorate;

import com.alibaba.fastjson.JSONObject;
import com.wolves.common.RepairClientConstants;
import com.wolves.dao.DaoSupport;
import com.wolves.dto.decorate.DecorateParamDTO;
import com.wolves.dto.decorate.DecorationApplyDTO;
import com.wolves.dto.repair.ProcessLogDTO;
import com.wolves.dto.user.CompanyDTO;
import com.wolves.entity.app.User;
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.HttpClientUtil;
import com.wolves.util.Logger;
import com.wolves.util.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 装修
 * Created by Administrator on 2019/8/25.
 */
@Service("decorateApplyService")
public class DecorateApplyService {

    protected Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "companyService")
    private CompanyService companyService;

    @Resource(name="userService")
    private UserService userService;

    /**
     * 保存装修申请
     * @param userId
     * @param decorateParamDTO
     * @param jsonObject
     * @return
     */
    public Integer saveDecorateApply(String userId, DecorateParamDTO decorateParamDTO, JSONObject jsonObject){
        DecorationApplyDTO decorationApplyDTO = new DecorationApplyDTO();
        decorationApplyDTO.setDecorationApplyId(UuidUtil.get32UUID());
        decorationApplyDTO.setUserId(userId);
        decorationApplyDTO.setTitle(decorateParamDTO.getTitle());
        decorationApplyDTO.setFadingren(decorateParamDTO.getFadingren());
        decorationApplyDTO.setFadingPhone(decorateParamDTO.getFadingPhone());
        decorationApplyDTO.setZuling(decorateParamDTO.getZuling());
        decorationApplyDTO.setXianchangren(decorateParamDTO.getXianchangren());
        decorationApplyDTO.setXianchangPhone(decorateParamDTO.getXianchangPhone());
        decorationApplyDTO.setZhuangxiuName(decorateParamDTO.getZhuangxiuName());
        decorationApplyDTO.setZhizhaoNum(decorateParamDTO.getZhizhaoNum());
        decorationApplyDTO.setZhuangxiuren(decorateParamDTO.getZhuangxiuren());
        decorationApplyDTO.setZhuangxiuPhone(decorateParamDTO.getZhuangxiuPhone());
        decorationApplyDTO.setZhuangxiuTime(decorateParamDTO.getZhuangxiuTime());
        decorationApplyDTO.setZhuangxirenshu(decorateParamDTO.getZhuangxirenshu());
        decorationApplyDTO.setZhuangxiufangwei(decorateParamDTO.getZhuangxiufangwei());
        decorationApplyDTO.setStatus(0);
        decorationApplyDTO.setProcId(jsonObject.getString("procId"));
        decorationApplyDTO.setWjbiid(jsonObject.getString("wjbiid"));
        decorationApplyDTO.setTaskId(jsonObject.getString("taskId"));
        decorationApplyDTO.setCreateTime(new Date());
        decorationApplyDTO.setUpdateTime(new Date());
        return (Integer) dao.save("DecorateApplyMapper.saveDecorationApply", decorationApplyDTO);
    }

    /**
     * 查询列表
     * @param userId
     * @return
     */
    public List<DecorationApplyDTO> selectDecorationApplyByUserId(String userId){

        return (List<DecorationApplyDTO>)dao.findForList("DecorateApplyMapper.selectDecorationApplyByUserId", userId);
    }

    /**
     * 查询
     * @param decorationApplyId
     * @return
     */
    public DecorationApplyDTO selectDecorationApplyById(String decorationApplyId){

        return (DecorationApplyDTO)dao.findForObject("DecorateApplyMapper.selectDecorationApplyById", decorationApplyId);
    }

    /**
     * 更新状态
     * @param decorationApplyId
     */
    public void updateStatusById(String decorationApplyId){

        dao.update("DecorateApplyMapper.updateStatusById", decorationApplyId);
    }

    //----------------------------------------------------装修请求-----------------------------------------------------------
    public Integer decorateLogin(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appType", RepairClientConstants.APP_TYPE);
        params.put("username", RepairClientConstants.USER_NAME);
        params.put("password", RepairClientConstants.PASS_WARD);

        JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.LOGIN, params);
        Integer res = jsonObject.getInteger("res");
        if (res != 1){
            logger.warn("装修登录失败, 消息如下："+jsonObject.toJSONString());
        }
        return res;
    }

    /**
     * 提交装修申请单子
     * @param user
     * @param decorateParamDTO
     */
    public Integer createDecorate(User user, DecorateParamDTO decorateParamDTO){
        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appType", RepairClientConstants.APP_TYPE);
        jsonObject.put("title", decorateParamDTO.getTitle());
        jsonObject.put("fadingren", decorateParamDTO.getFadingren());
        jsonObject.put("fading_phone", decorateParamDTO.getFadingPhone());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        jsonObject.put("zuling", formatter.format(decorateParamDTO.getZuling()));

        jsonObject.put("company", companyDTO.getCompanyName());
        jsonObject.put("xianchangren", decorateParamDTO.getXianchangren());
        jsonObject.put("xianchang_phone", decorateParamDTO.getXianchangPhone());
        jsonObject.put("zhuangxiu_name", decorateParamDTO.getZhuangxiuName());
        jsonObject.put("zhizhao_num", decorateParamDTO.getZhizhaoNum());

        jsonObject.put("zhuangxiuren", decorateParamDTO.getZhuangxiuren());
        jsonObject.put("zhuangxiu_phone", decorateParamDTO.getZhuangxiuPhone());
        jsonObject.put("zhuangxiu_time", formatter.format(decorateParamDTO.getZhuangxiuTime()));
        jsonObject.put("zhuangxirenshu", decorateParamDTO.getZhuangxirenshu());
        jsonObject.put("zhuangxiufangwei", decorateParamDTO.getZhuangxiufangwei());

        this.decorateLogin();
        JSONObject result = null;
        try {
            String resu = HttpClientUtil.send(RepairClientConstants.DECORATE+RepairClientConstants.CREATE, jsonObject, "UTF-8");
            result = JSONObject.parseObject(resu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer res = result.getInteger("res");
        if (res != 1){
            logger.warn("工单创建失败, 消息如下："+jsonObject.toJSONString());
        }
        //保存
        this.saveDecorateApply(user.getUserId(), decorateParamDTO, result);
        return res;
    }

    /**
     * 查询信息
     * @param decorationApplyId
     * @return
     */
    public List<ProcessLogDTO> listProcessLog(String decorationApplyId){
        DecorationApplyDTO decorationApplyDTO = this.selectDecorationApplyById(decorationApplyId);
        if (decorationApplyDTO == null){
            logger.warn("查询记录信息不存在, 消息如下-> decorationApplyId:"+decorationApplyId);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appType", RepairClientConstants.APP_TYPE);
        params.put("wjbiid", decorationApplyDTO.getWjbiid());//工单唯一标识

        this.decorateLogin();
        JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.LOG, params);
        Integer res = jsonObject.getInteger("res");
        if (res != 1){
            logger.warn("查询工单日志信息失败, 消息如下："+jsonObject.toJSONString());
        }

        List<ProcessLogDTO> processLogDTOs = JSONObject.parseArray(jsonObject.getString("obj"),  ProcessLogDTO.class);

        return processLogDTOs;
    }

    /**
     * 撤销工单
     * @param decorationApplyId
     * @return
     */
    public Integer stopJobs(String decorationApplyId){
        DecorationApplyDTO decorationApplyDTO = this.selectDecorationApplyById(decorationApplyId);
		if (decorationApplyDTO == null){
			logger.warn("查询记录信息不存在, 消息如下-> decorationApplyId:"+decorationApplyId);
		}

        Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);
		params.put("procId", decorationApplyDTO.getProcId());//流程实例id
		params.put("wjbiid", decorationApplyDTO.getWjbiid());//工单唯一标识
		params.put("taskId", decorationApplyDTO.getTaskId());//用户环节id

        this.decorateLogin();
        JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.STOP, params);
        Integer res = jsonObject.getInteger("res");
        if (res != 1){
            logger.warn("撤销工单失败, 消息如下："+jsonObject.toJSONString());
        }
		this.updateStatusById(decorationApplyId);
        return res;
    }
}
