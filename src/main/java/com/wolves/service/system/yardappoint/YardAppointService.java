package com.wolves.service.system.yardappoint;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

import com.wolves.common.Constants;
import com.wolves.common.StatusEnum;
import com.wolves.dao.DaoSupport;
import com.wolves.dto.ApplyYardDTO;
import com.wolves.dto.AppointmentDTO;
import com.wolves.dto.YardDTO;
import com.wolves.entity.system.Page;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.yard.YardService;
import com.wolves.util.DateUtil;
import com.wolves.util.PageData;
import com.wolves.util.UuidUtil;
import org.springframework.stereotype.Service;

@Service("yardappointService")
public class YardAppointService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="yardService")
	private YardService yardService;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("YardAppointMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("YardAppointMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("YardAppointMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("YardAppointMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("YardAppointMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("YardAppointMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("YardAppointMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 根据用户id查询
	 * @param params
	 * @return
	 */
	public List<AppointmentDTO> selectAppointment(Map<String,Object> params){

		return (List<AppointmentDTO>) dao.findForList("YardAppointMapper.selectAppointment", params);
	}

	/**
	 * 查询excel导出的数据
	 * @param pd
	 * @return
	 */
	public List<PageData> listYardAppoint(PageData pd){

		return (List<PageData>)dao.findForList("YardAppointMapper.listYardAppoint", pd);
	}

	/**
	 * 提交预约
	 * @param applyYardDTO
	 * @param userId
	 * @return
	 */
	public Result saveAppoint(ApplyYardDTO applyYardDTO, String userId){
		Result result = new Result();
		//判断场地
		YardDTO yardDTO = yardService.getYardById(applyYardDTO.getYardId());
		if (yardDTO == null){
			result.setResult(ResultCode.FAIL);
			result.setMsg("抱歉，该场地已不存在");
			return result;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("placeId", applyYardDTO.getYardId());
		params.put("beginTime", applyYardDTO.getBeginTime());
		params.put("endTime", applyYardDTO.getEndTime());
		List<AppointmentDTO> appointmentDTOs = this.selectAppointment(params);
		if (appointmentDTOs != null && !appointmentDTOs.isEmpty()){
			result.setResult(ResultCode.FAIL);
			result.setMsg("抱歉，改时段已被预定");
			return result;
		}
		//计算时长
		long hours = DateUtil.printDifference(applyYardDTO.getBeginTime(), applyYardDTO.getEndTime());
		PageData pd = new PageData();
		String id = UuidUtil.get32UUID();
		//主键
		pd.put("YARDAPPOINT_ID", id);
		//场地ID
		pd.put("PLACE_ID", applyYardDTO.getYardId());
		//预定人ID
		pd.put("APPLY_USER_ID", userId);
		//预定金额
		pd.put("BOOK_FEE", yardDTO.getRentFee().multiply(BigDecimal.valueOf(2)).multiply(BigDecimal.valueOf(hours)));
		//预定状态
		pd.put("STATUS", StatusEnum.INIT.getKey());
		//预定日期
		pd.put("PLACE_DATE", applyYardDTO.getPlaceDate());
		//开始时间
		pd.put("BEGIN_TIME", applyYardDTO.getBeginTime());
		//结束时间
		pd.put("END_TIME", applyYardDTO.getEndTime());
		//时长
		pd.put("BOOK_DURATION", hours);
		//备注
		pd.put("NOTE", applyYardDTO.getNote());
		this.save(pd);

		//发短信给企业负责人

		//发短信给园区场地负责人

		result.setResult(ResultCode.SUCCESS);
		result.setMsg("预约成功");
		result.setData(id);
		return result;
	}

	/**
	 * 查询场地已经预定好的时间段
	 * @param yardId
	 * @param time
	 * @return
	 */
	public Set<String> selectYardAppointTime(String yardId, String time){
		Map<String,Object> params = new HashMap<String, Object>(2);
		params.put("yardId", yardId);
		params.put("time", time);
		List<AppointmentDTO> appointmentDTOS = (List<AppointmentDTO>) dao.findForList("YardAppointMapper.selectYardAppointTime", params);
		Set<String> set = new TreeSet<String>();
		if (appointmentDTOS != null && !appointmentDTOS.isEmpty()){
			for (AppointmentDTO appointmentDTO : appointmentDTOS){
				SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
				String start = formatter.format(appointmentDTO.getBeginTime());
				String end = formatter.format(appointmentDTO.getEndTime());
				List<String> list = DateUtil.getIntervalTimeList(start, end, Constants.TIME_MINUTE);
				set.addAll(list);
			}
		}
		return set;
	}

	/**
	 * 查询预约是否存在
	 * @param yardappointId
	 * @return
	 */
	public AppointmentDTO selectYardAppointById(String yardappointId){

		return (AppointmentDTO)dao.findForObject("YardAppointMapper.selectYardAppointById", yardappointId);
	}

	/**
	 * 修改预约
	 * @param yardappointId
	 */
	public void updateYardAppoint(String yardappointId, String status){
		PageData pd = new PageData();
		pd.put("yardappointId", yardappointId);
		pd.put("status", status);
		dao.update("YardAppointMapper.updateYardAppoint", pd);

		pd.put("yardappointId", yardappointId);
		pd.put("status", status);
		dao.update("PayOrderMapper.updatePayOrder", pd);
	}
}

