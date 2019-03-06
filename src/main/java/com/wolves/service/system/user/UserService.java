package com.wolves.service.system.user;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.dto.user.RegisterDTO;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.Page;
import com.wolves.entity.system.User;
import com.wolves.util.PageData;

@Service("userService")
public class UserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	* 通过id获取数据
	*/
	public PageData findByUiId(PageData pd) {
		return (PageData)dao.findForObject("UserXMapper.findByUiId", pd);
	}
	/**
	* 通过loginname获取数据
	*/
	public PageData findByUId(PageData pd) {
		return (PageData)dao.findForObject("UserXMapper.findByUId", pd);
	}
	
	/**
	* 通过邮箱获取数据
	*/
	public PageData findByUE(PageData pd) {
		return (PageData)dao.findForObject("UserXMapper.findByUE", pd);
	}
	
	/**
	* 通过编号获取数据
	*/
	public PageData findByUN(PageData pd) {
		return (PageData)dao.findForObject("UserXMapper.findByUN", pd);
	}
	
	/**
	* 保存用户
	*/
	public void saveU(PageData pd) {
		dao.save("UserXMapper.saveU", pd);
	}
	/**
	* 修改用户
	*/
	public void editU(PageData pd) {
		dao.update("UserXMapper.editU", pd);
	}
	/**
	* 换皮肤
	*/
	public void setSKIN(PageData pd) {
		dao.update("UserXMapper.setSKIN", pd);
	}
	/**
	* 删除用户
	*/
	public void deleteU(PageData pd) {
		dao.delete("UserXMapper.deleteU", pd);
	}
	/**
	* 批量删除用户
	*/
	public void deleteAllU(String[] USER_IDS) {
		dao.delete("UserXMapper.deleteAllU", USER_IDS);
	}
	/**
	*用户列表(用户组)
	*/
	public List<PageData> listPdPageUser(Page page) {
		return (List<PageData>) dao.findForList("UserXMapper.userlistPage", page);
	}
	
	/**
	*用户列表(全部)
	*/
	public List<PageData> listAllUser(PageData pd) {
		return (List<PageData>) dao.findForList("UserXMapper.listAllUser", pd);
	}
	
	/**
	*用户列表(供应商用户)
	*/
	public List<PageData> listGPdPageUser(Page page) {
		return (List<PageData>) dao.findForList("UserXMapper.userGlistPage", page);
	}
	/**
	* 保存用户IP
	*/
	public void saveIP(PageData pd) {
		dao.update("UserXMapper.saveIP", pd);
	}
	
	/**
	* 登录判断
	*/
	public PageData getUserByNameAndPwd(PageData pd) {
		return (PageData)dao.findForObject("UserXMapper.getUserInfo", pd);
	}
	/**
	* 跟新登录时间
	*/
	public void updateLastLogin(PageData pd) {
		dao.update("UserXMapper.updateLastLogin", pd);
	}
	
	/**
	*通过id获取数据
	*/
	public User getUserAndRoleById(String USER_ID)   {
		return (User) dao.findForObject("UserMapper.getUserAndRoleById", USER_ID);
	}

	/**
	 * app登陆
	 */
	public com.wolves.entity.app.User selectUserByModel(com.wolves.entity.app.User user){
		return (com.wolves.entity.app.User)dao.findForObject("UserMapper.getUser", user);
	}

	/**
	 * 更新登录时间，及token
	 */
	public Integer updateTokenById(com.wolves.entity.app.User user) {

		return (Integer) dao.update("UserMapper.updateTokenById", user);
	}

	/**
	 * 保存APP客户
	 */
	public Integer saveUser(com.wolves.entity.app.User user){

		return (Integer) dao.save("UserMapper.saveUser", user);
	}

	/**
	 * 根据电话号码查询用户
	 */
	public com.wolves.entity.app.User getUserByPhone(com.wolves.entity.app.User user){

		return (com.wolves.entity.app.User) dao.findForObject("UserMapper.getUserByPhone", user);
	}

	/**
	 * 根据toe可能查询用户
	 */
	public com.wolves.entity.app.User getUserByToken(com.wolves.entity.app.User user){

		return (com.wolves.entity.app.User) dao.findForObject("UserMapper.getUserByToken", user);
	}
}
