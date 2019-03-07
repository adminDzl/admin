package com.wolves.service.system.appuser;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("appUserService")
public class AppUserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 通过id获取数据
	*/
	public PageData findByUiId(PageData pd){
		return (PageData)dao.findForObject("AppuserMapper.findByUiId", pd);
	}
	/**
	* 通过loginname获取数据
	*/
	public PageData findByUId(PageData pd){
		return (PageData)dao.findForObject("AppuserMapper.findByUId", pd);
	}
	
	/**
	* 通过邮箱获取数据
	*/
	public PageData findByUE(PageData pd){
		return (PageData)dao.findForObject("AppuserMapper.findByUE", pd);
	}
	
	/**
	* 通过编号获取数据
	*/
	public PageData findByUN(PageData pd){
		return (PageData)dao.findForObject("AppuserMapper.findByUN", pd);
	}
	
	/**
	* 保存用户
	*/
	public void saveU(PageData pd){
		dao.save("AppuserMapper.saveU", pd);
	}
	/**
	* 修改用户
	*/
	public void editU(PageData pd){
		dao.update("AppuserMapper.editU", pd);
	}
	/**
	* 删除用户
	*/
	public void deleteU(PageData pd){
		dao.delete("AppuserMapper.deleteU", pd);
	}
	/**
	* 批量删除用户
	*/
	public void deleteAllU(String[] USER_IDS){
		dao.delete("AppuserMapper.deleteAllU", USER_IDS);
	}
	/**
	*用户列表(全部)
	*/
	public List<PageData> listAllUser(PageData pd){
		return (List<PageData>) dao.findForList("AppuserMapper.listAllUser", pd);
	}
	
	/**
	*用户列表(用户组)
	*/
	public List<PageData> listPdPageUser(Page page){
		return (List<PageData>) dao.findForList("AppuserMapper.userlistPage", page);
	}
	
	/**
	* 保存用户IP
	*/
	public void saveIP(PageData pd)throws Exception{
		dao.update("AppuserMapper.saveIP", pd);
	}
	
	/**
	* 登录判断
	*/
	public PageData getUserByNameAndPwd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.getUserInfo", pd);
	}
	/**
	* 跟新登录时间
	*/
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("AppuserMapper.updateLastLogin", pd);
	}
}