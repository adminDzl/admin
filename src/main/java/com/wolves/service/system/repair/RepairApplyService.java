package com.wolves.service.system.repair;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.wolves.common.RepairClientConstants;
import com.wolves.dto.repair.AttachmentDTO;
import com.wolves.dto.repair.ProcessLogDTO;
import com.wolves.dto.repair.WorkorderDTO;
import com.wolves.dto.user.CompanyDTO;
import com.wolves.dto.user.RepairParamsDTO;
import com.wolves.entity.app.User;
import com.wolves.entity.system.Repair;
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.payment.PaymentService;
import com.wolves.util.HttpClientUtil;
import com.wolves.util.Logger;
import com.wolves.util.UuidUtil;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("repairApplyService")
public class RepairApplyService {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "daoSupport")
	private DaoSupport dao;
    @Resource(name = "companyService")
    private CompanyService companyService;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("RepairApplyMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("RepairApplyMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("RepairApplyMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("RepairApplyMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("RepairApplyMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("RepairApplyMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("RepairApplyMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询报修列表
	 * @param params
	 * @return
	 */
	public List<Repair> selectRepairApplyByUserId(Map<String, Object> params){
		return (List<Repair>) dao.findForList("RepairApplyMapper.selectRepairApplyByUserId", params);
	}

	/**
	 *  查询报修详情
	 * @param repairApplyId
	 * @return
	 */
	public Repair selectRepairApplyById(String repairApplyId){
		return (Repair)dao.findForObject("RepairApplyMapper.selectRepairApplyById", repairApplyId);
	}

    /**
     * 修改状态
     * @param repairApplyId
     */
	public void updateStatusById(String repairApplyId){
        dao.update("RepairApplyMapper.updateStatusById", repairApplyId);
    }

	/**
	 * 保存信息
	 * @param userId
	 * @param repairParamsDTO
	 * @param jsonObject
	 */
	public Integer saveRepair(String userId, RepairParamsDTO repairParamsDTO, JSONObject jsonObject){
		Repair repair = new Repair();
		repair.setRepairApplyId(UuidUtil.get32UUID());
		repair.setUserId(userId);
        repair.setTitle(repairParamsDTO.getTitle());//标题
        repair.setLouyus(repairParamsDTO.getLouyus());//楼宇
        repair.setLoutis(repairParamsDTO.getLoutis());//楼体
        repair.setFloor(repairParamsDTO.getFloor());//楼层
        repair.setQuyus(repairParamsDTO.getQuyus());//区域
        repair.setFaultclassify(repairParamsDTO.getFaultclassify());//故障分类
        repair.setSyss(repairParamsDTO.getSyss());//系统分类
        repair.setDescribes(repairParamsDTO.getDescribes());//详细信息
        repair.setStatus(0);
        repair.setProcId(jsonObject.getString("procId"));
        repair.setWjbiid(jsonObject.getString("wjbiid"));
        repair.setTaskId(jsonObject.getString("taskId"));
		return (Integer) dao.save("RepairApplyMapper.saveRepair", repair);
	}

	//---------------------------------------------报修------------------------------------------------------------------
	//登录
	public Integer repairLogin(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", RepairClientConstants.USER_NAME);
		params.put("password", RepairClientConstants.PASS_WARD);
		params.put("appType", RepairClientConstants.APP_TYPE);

		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.LOGIN, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("报修登录失败, 消息如下："+jsonObject.toJSONString());
		}
		return res;
	}

	//创建工单
	public Integer createRepair(User user ,RepairParamsDTO repairParamsDTO){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);

		params.put("title", repairParamsDTO.getTitle());//标题
		params.put("apply", user.getName());//申请人姓名
		params.put("applyphone", user.getPhone());//申请人号码
		params.put("applyemail", user.getEmail());//申请人邮箱
        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
		params.put("company", companyDTO.getCompanyName());//公司
		params.put("louyus", repairParamsDTO.getLouyus());//楼宇
		params.put("loutis", repairParamsDTO.getLoutis());//楼体
		params.put("floor", repairParamsDTO.getFloor());//楼层
		params.put("quyus", repairParamsDTO.getQuyus());//区域
		params.put("faultclassify", repairParamsDTO.getFaultclassify());//故障分类
		params.put("syss", repairParamsDTO.getSyss());//系统分类
		params.put("describes", repairParamsDTO.getDescribes());//详细信息

        this.repairLogin();
		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.CREATE, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
            logger.warn("工单创建失败, 消息如下："+jsonObject.toJSONString());
		}
        JSONObject object = jsonObject.getJSONObject("obj");
		//保存
        this.saveRepair(user.getUserId(), repairParamsDTO, object);
		return res;
	}

	//工单查询列表
	public List<WorkorderDTO> listWorkorder(String startTime, String endTime){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);
		params.put("startTime", startTime);//开始时间
		params.put("endTime", endTime);//结束时间

        this.repairLogin();
		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.QUERY, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("工单查询失败, 消息如下："+jsonObject.toJSONString());
		}

		List<WorkorderDTO> workorderDTOs = JSONObject.parseArray(jsonObject.getString("obj"),  WorkorderDTO.class);

		return workorderDTOs;
	}

	//工单日志信息
	public List<ProcessLogDTO> listProcessLog(String repairApplyId){
        Repair repair = this.selectRepairApplyById(repairApplyId);
        if (repair == null){
            logger.warn("查询报修记录信息不存在, 消息如下-> repairApplyId:"+repairApplyId);
        }

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);
		params.put("wjbiid", repair.getWjbiid());//工单唯一标识

        this.repairLogin();
		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.LOG, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("查询工单日志信息失败, 消息如下："+jsonObject.toJSONString());
		}

		List<ProcessLogDTO> processLogDTOs = JSONObject.parseArray(jsonObject.getString("obj"),  ProcessLogDTO.class);

		return processLogDTOs;
	}

	//工单附件信息
	public List<AttachmentDTO> listAttachment(String repairApplyId){
        Repair repair = this.selectRepairApplyById(repairApplyId);
        if (repair == null){
            logger.warn("查询报修记录信息不存在, 消息如下-> repairApplyId:"+repairApplyId);
        }

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);
		params.put("wjbiid", repair.getWjbiid());//工单唯一标识

        this.repairLogin();
		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.ATTACH, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("查询工单附件信息失败, 消息如下："+jsonObject.toJSONString());
		}

		List<AttachmentDTO> attachmentDTOs = JSONObject.parseArray(jsonObject.getString("obj"),  AttachmentDTO.class);

		return attachmentDTOs;
	}

	//撤销工单
	public Integer stopJobs(String repairApplyId){
        Repair repair = this.selectRepairApplyById(repairApplyId);
        if (repair == null){
            logger.warn("查询报修记录信息不存在, 消息如下-> repairApplyId:"+repairApplyId);
        }

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);
		params.put("procId", repair.getProcId());//流程实例id
		params.put("wjbiid", repair.getWjbiid());//工单唯一标识
		params.put("taskId", repair.getTaskId());//用户环节id

        this.repairLogin();
		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.STOP, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("撤销工单失败, 消息如下："+jsonObject.toJSONString());
		}
        this.updateStatusById(repairApplyId);
		return res;
	}
}

