package com.wolves.service.system.repair;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wolves.common.RepairClientConstants;
import com.wolves.common.StatusEnum;
import com.wolves.dto.repair.AttachmentDTO;
import com.wolves.dto.repair.ProcessLogDTO;
import com.wolves.dto.repair.WorkorderDTO;
import com.wolves.dto.user.RepairApplyDTO;
import com.wolves.dto.user.RepairApplyDetailDTO;
import com.wolves.entity.system.Repair;
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
	public List<RepairApplyDTO> selectRepairApplyByUserId(Map<String, Object> params){
		return (List<RepairApplyDTO>) dao.findForList("RepairApplyMapper.selectRepairApplyByUserId", params);
	}

	/**
	 *  查询报修详情
	 * @param repairApplyId
	 * @return
	 */
	public RepairApplyDetailDTO selectRepairApplyById(String repairApplyId){
		return (RepairApplyDetailDTO)dao.findForObject("RepairApplyMapper.selectRepairApplyById", repairApplyId);
	}

	/**
	 * 保存信息
	 * @param userId
	 * @param content
	 * @param imageUrls
	 */
	public Integer saveRepair(String userId, String content, String imageUrls){
		Repair repair = new Repair();
		repair.setApplyContent(content);
		repair.setImageUrls(imageUrls);
		repair.setApplyStatus(Integer.valueOf(StatusEnum.INIT.getKey()));
		repair.setRepairApplyId(UuidUtil.get32UUID());
		repair.setUserId(userId);
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
	public Integer createRepair(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);

		params.put("title", "");//标题
		params.put("apply", "");//申请人姓名
		params.put("applyphone", "");//申请人号码
		params.put("applyemail", "");//申请人邮箱
		params.put("company", "");//公司
		params.put("louyus", "");//楼宇
		params.put("loutis", "");//楼体
		params.put("floor", "");//楼层
		params.put("quyus", "");//区域
		params.put("faultclassify", "");//故障分类
		params.put("syss", "");//系统分类
		params.put("describes", "");//详细信息

		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.CREATE, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("创建工单失败, 消息如下："+jsonObject.toJSONString());
		}
		return res;
	}

	//工单查询列表
	public List<WorkorderDTO> listWorkorder(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);

		params.put("startTime", "");//开始时间
		params.put("endTime", "");//结束时间

		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.QUERY, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("工单查询失败, 消息如下："+jsonObject.toJSONString());
		}

		List<WorkorderDTO> workorderDTOs = JSONObject.parseArray(jsonObject.getString("obj"),  WorkorderDTO.class);

		return workorderDTOs;
	}

	//工单日志信息
	public List<ProcessLogDTO> listProcessLog(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);
		params.put("wjbiid", "");//工单唯一标识

		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.LOG, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("查询工单日志信息失败, 消息如下："+jsonObject.toJSONString());
		}

		List<ProcessLogDTO> processLogDTOs = JSONObject.parseArray(jsonObject.getString("obj"),  ProcessLogDTO.class);

		return processLogDTOs;
	}

	//工单附件信息
	public List<AttachmentDTO> listAttachment(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);
		params.put("wjbiid", "");//工单唯一标识

		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.ATTACH, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("查询工单附件信息失败, 消息如下："+jsonObject.toJSONString());
		}

		List<AttachmentDTO> attachmentDTOs = JSONObject.parseArray(jsonObject.getString("obj"),  AttachmentDTO.class);

		return attachmentDTOs;
	}

	//撤销工单
	public Integer stopJobs(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", RepairClientConstants.APP_TYPE);
		params.put("procId", "");//流程实例id
		params.put("wjbiid", "");//工单唯一标识
		params.put("taskId", "");//用户环节id

		JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.STOP, params);
		Integer res = jsonObject.getInteger("res");
		if (res != 1){
			logger.warn("撤销工单失败, 消息如下："+jsonObject.toJSONString());
		}
		return res;
	}
}

