package com.wolves.service.system.decorate;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;

import com.wolves.common.ApplyTypeEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dao.DaoSupport;
import com.wolves.dto.user.DecorateDataDTO;
import com.wolves.entity.app.User;
import com.wolves.entity.system.Decorate;
import com.wolves.entity.system.Page;
import com.wolves.service.system.user.UserService;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
import com.wolves.util.UuidUtil;
import org.springframework.stereotype.Service;

@Service("decorateService")
public class DecorateService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="userService")
	private UserService userService;
	
	/**
	* 新增
	*/
	public void save(PageData pd)  {
		dao.save("DecorateMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd)  {
		dao.delete("DecorateMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd)  {
		dao.update("DecorateMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page)  {
		return (List<PageData>)dao.findForList("DecorateMapper.datalistPage", page);
	}

	/**
	 * 一卡通申请
	 * @return
	 */
	public List<PageData> passList(Page page){

		return (List<PageData>)dao.findForList("DecorateMapper.passlistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)  {
		return (List<PageData>)dao.findForList("DecorateMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)  {
		return (PageData)dao.findForObject("DecorateMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)  {
		dao.delete("DecorateMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 保存申请
	 */
	public void saveApply(String token, DecorateDataDTO decorateDataDTO){
		User user = userService.getUser(token);
		Decorate decorate = new Decorate();
		decorate.setDecorateNo(UuidUtil.get32UUID());
		decorate.setDecorateId(UuidUtil.get32UUID());
		decorate.setUserId(user.getUserId());
		decorate.setType(decorateDataDTO.getType());
		decorate.setTitle(decorateDataDTO.getTitle());
		decorate.setContent(decorateDataDTO.getContent());
		if (StringUtils.isNotEmpty(decorateDataDTO.getFileUrl())){
			decorate.setFileUrl(decorateDataDTO.getFileUrl());
		}
		decorate.setStatus(Integer.valueOf(StatusEnum.INIT.getKey()));
		dao.save("DecorateMapper.saveApply", decorate);
		if (decorateDataDTO.getType().equals(ApplyTypeEnum.apply_ic_tl.getKey())){
			user.setSfid(decorateDataDTO.getIdCard());
			userService.updateUser(user);
		}
	}

	/**
	 * 查询申请列表
	 * @param userId
	 * @return
	 */
	public List<Decorate> selectMyApply(String userId){

		return (List<Decorate>)dao.findForList("DecorateMapper.selectMyApply", userId);
	}

	/**
	 * 查询申请明细
	 * @param decorateId
	 * @return
	 */
	public Decorate selectMyApplyDetail(String decorateId){

		return (Decorate) dao.findForObject("DecorateMapper.selectMyApplyDetail", decorateId);
	}

	/**
	 * 取消预约
	 * @param decorateId
	 */
	public void updateDecorate(String decorateId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("decorateId", decorateId);
		params.put("status", 3);

		dao.update("DecorateMapper.updateDecorate", params);
	}
}

