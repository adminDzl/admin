package com.wolves.service.system.appuser;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.dto.UserCarBindDTO;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("usercarbindService")
public class UserCarBindService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("UserCarBindMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("UserCarBindMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("UserCarBindMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("UserCarBindMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("UserCarBindMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("UserCarBindMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("UserCarBindMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 *  通过客户id查询
	 * @param userId
	 * @return
	 */
	public UserCarBindDTO selectCarBindByUserId(String userId){

		return (UserCarBindDTO)dao.findForObject("UserCarBindMapper.selectCarBindByUserId", userId);
	}

	/**
	 * 查询
	 * @param params
	 * @return
	 */
	public List<UserCarBindDTO> selectMyCar(Map<String, Object> params){

		return (List<UserCarBindDTO>) dao.findForList("UserCarBindMapper.selectMyCar", params);
	}
}

