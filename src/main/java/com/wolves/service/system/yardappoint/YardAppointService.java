package com.wolves.service.system.yardappoint;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;

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
		//主键
		pd.put("YARDAPPOINT_ID", UuidUtil.get32UUID());
		//场地ID
		pd.put("PLACE_ID", applyYardDTO.getYardId());
		//预定人ID
		pd.put("APPLY_USER_ID", userId);
		//预定金额
		pd.put("BOOK_FEE", yardDTO.getRentFee().multiply(BigDecimal.valueOf(hours)));
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

		result.setResult(ResultCode.SUCCESS);
		result.setMsg("预约成功");
		return result;
	}
}

