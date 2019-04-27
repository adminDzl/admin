package com.wolves.service.system.usercarmonthcard;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;

import com.wolves.common.PayTypeEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dto.user.ExportDTO;
import com.wolves.entity.app.PayOrder;
import com.wolves.service.system.payorder.PayOrderService;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

/**
 * 月卡
 * @author gf
 */
@Service("usercarmonthcardService")
public class UserCarMonthCardService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="payorderService")
	private PayOrderService payorderService;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("UserCarMonthCardMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("UserCarMonthCardMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("UserCarMonthCardMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("UserCarMonthCardMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("UserCarMonthCardMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("UserCarMonthCardMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("UserCarMonthCardMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询导出pdf的字段
	 * @param user_car_month_card_id
	 * @return
	 */
	public ExportDTO findMonthCardById(String user_car_month_card_id){

		return (ExportDTO) dao.findForObject("UserCarMonthCardMapper.findMonthCardById", user_car_month_card_id);
	}

	/**
	 * 查询月卡记录
	 * @param userId
	 * @return
	 */
	public List<PayOrder> selectPayMonthCard(String userId){
		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("userId", userId);
		params.put("payType", PayTypeEnum.PARKING_RETE.getKey());
		params.put("payStatus", StatusEnum.SUCCESS.getKey());
		return payorderService.selectPayOrderByUserId(params);
	}
}

