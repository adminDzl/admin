package com.wolves.service.system.usercarmonthcard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;

import com.wolves.common.PayTypeEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dto.UserCarMonthCardDTO;
import com.wolves.dto.user.ExportDTO;
import com.wolves.entity.app.PayOrder;
import com.wolves.service.system.payorder.PayOrderService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.StringUtils;
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
	@Resource(name="userService")
	private UserService userService;
	
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

	/**
	 * 获取数据
	 * @param lists
	 */
	public List<UserCarMonthCardDTO> getCompanyData(List<Map<String, Object>> lists){
		List<UserCarMonthCardDTO> userCarMonthCardDTOS = new ArrayList<UserCarMonthCardDTO>();
		if (lists != null && !lists.isEmpty()){
			for (Map<String, Object> map : lists){
				UserCarMonthCardDTO userCarMonthCardDTO = this.getData(map);
				userCarMonthCardDTOS.add(userCarMonthCardDTO);
			}
		}

		return userCarMonthCardDTOS;
	}

	private UserCarMonthCardDTO getData(Map<String, Object> map){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		UserCarMonthCardDTO userCarMonthCardDTO = new UserCarMonthCardDTO();
		Object userName = map.get("用户名");
		userCarMonthCardDTO.setUserName(userName.toString());
		com.wolves.entity.app.User user = new com.wolves.entity.app.User();
		user.setUsername(userName.toString());
		user = userService.getUserByPhone(user);
		if (user != null){
			userCarMonthCardDTO.setUserId(user.getUserId());
		}
		Object mothCard = map.get("月卡号");
		userCarMonthCardDTO.setCardNo(mothCard.toString());
		Object pricer = map.get("金额");
		userCarMonthCardDTO.setPrice(pricer.toString());
		try {
			Object comeTime = map.get("有效日期");
			userCarMonthCardDTO.setUseTilDate(sdf.parse(comeTime.toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return userCarMonthCardDTO;
	}

	public PageData checkExcelData(List<Map<String, Object>> list, PageData pd){

		if (list != null && !list.isEmpty()){
			for (Map<String, Object> map : list){
				Object name = map.get("用户名");
				if (StringUtils.isEmpty(name.toString().trim())){
					pd.put("msg", "用户名称不能为空");
					pd.put("status", "1");
					return pd;
				}
				com.wolves.entity.app.User user = new com.wolves.entity.app.User();
				user.setUsername(name.toString());
				user = userService.getUserByPhone(user);
				if (user == null){
					pd.put("msg", "该用户不存在");
					pd.put("status", "1");
					return pd;
				}
				Object phone = map.get("月卡号");
				if (StringUtils.isEmpty(phone.toString().trim())){
					pd.put("msg", "月卡号不能为空");
					pd.put("status", "1");
					return pd;
				}
				Object email = map.get("金额");
				if (StringUtils.isEmpty(email.toString().trim())){
					pd.put("msg", "金额不能为空");
					pd.put("status", "1");
					return pd;
				}
				Object company = map.get("有效日期");
				if (StringUtils.isEmpty(company.toString().trim())){
					pd.put("msg", "有效日期不能为空");
					pd.put("status", "1");
					return pd;
				}
			}
		}
		pd.put("status", "0");
		return pd;
	}

	public void createMonthCard(List<UserCarMonthCardDTO> userCarMonthCardDTOS){

	}
}

