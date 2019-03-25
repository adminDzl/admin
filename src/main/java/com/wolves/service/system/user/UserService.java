package com.wolves.service.system.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.common.CompanyTypeEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dto.user.CompanyDTO;
import com.wolves.dto.user.RegisterDTO;
import com.wolves.dto.user.UserExcelDTO;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.CompanyService;
import com.wolves.util.MD5;
import com.wolves.util.StringUtils;
import com.wolves.util.UuidUtil;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.entity.system.User;
import com.wolves.util.PageData;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="companyService")
	private CompanyService companyService;

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

	public com.wolves.entity.app.User getUser(String token){

		com.wolves.entity.app.User user = new com.wolves.entity.app.User();
		if (StringUtils.isNotEmpty(token)){
			user.setToken(token);
			user = this.getUserByToken(user);
		}
		return user;
	}

	public Integer saveUserData(RegisterDTO registerDTO){
		//保存数据
		com.wolves.entity.app.User userInfo = new com.wolves.entity.app.User();
		userInfo.setUserId(UuidUtil.get32UUID());
		userInfo.setUsername(registerDTO.getName());
		String encrypt = MD5.md5(registerDTO.getPassword());
		userInfo.setPassword(encrypt);
		userInfo.setPhone(registerDTO.getTelephone());
		userInfo.setName(registerDTO.getName());
		userInfo.setSex(registerDTO.getSex());
		userInfo.setIdCardFrontUrl(registerDTO.getIdCardFrontUrl());
		userInfo.setIdCardBackUrl(registerDTO.getIdCardBackUrl());
		userInfo.setCompanyId(registerDTO.getCompanyId());
		userInfo.setIp("");
		userInfo.setEmail(registerDTO.getEmail());
		//身份证已经绑定
		return this.saveUser(userInfo);
	}

	public List<UserExcelDTO> getUserData(List<Map<String, Object>> list){
		List<UserExcelDTO> userExcelDTOS = new ArrayList<UserExcelDTO>();
		if (list != null && !list.isEmpty()){
			for (Map<String, Object> map : list){

				UserExcelDTO userExcelDTO = this.getData(map);
				userExcelDTOS.add(userExcelDTO);
			}
		}
		return userExcelDTOS;
	}

	private UserExcelDTO getData(Map<String, Object> map){
		UserExcelDTO userExcelDTO = new UserExcelDTO();
		userExcelDTO.setNum(map.get("编号").toString());
		Object name = map.get("姓名");
		userExcelDTO.setName(name.toString());
		Object phone = map.get("手机");
		userExcelDTO.setPhone(phone.toString());
		Object email = map.get("邮箱");
		userExcelDTO.setEmail(email.toString());
		userExcelDTO.setNote(map.get("备注").toString());
		Object company = map.get("企业");
		userExcelDTO.setCompany(company.toString());
		return userExcelDTO;
	}

	public Result checkExcelData(List<Map<String, Object>> list){
		Result result = new Result();
		if (list != null && !list.isEmpty()){
			for (Map<String, Object> map : list){
				Object name = map.get("姓名");
				if (StringUtils.isEmpty(name.toString().trim())){
					result.setMsg("姓名不能为空");
					result.setResult(ResultCode.FAIL);
					return result;
				}
				Object phone = map.get("手机");
				if (StringUtils.isEmpty(phone.toString().trim())){
					result.setMsg("手机不能为空");
					result.setResult(ResultCode.FAIL);
					return result;
				}
				Object email = map.get("邮箱");
				if (StringUtils.isEmpty(email.toString().trim())){
					result.setMsg("邮箱不能为空");
					result.setResult(ResultCode.FAIL);
					return result;
				}
				Object company = map.get("企业");
				if (StringUtils.isEmpty(company.toString().trim())){
					result.setMsg("企业不能为空");
					result.setResult(ResultCode.FAIL);
					return result;
				}
			}
		}
		result.setResult(ResultCode.SUCCESS);
		return result;
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public void saveExcelUser(List<UserExcelDTO> userExcelDTOS){

		if (userExcelDTOS != null && !userExcelDTOS.isEmpty()){
			for (UserExcelDTO userExcelDTO : userExcelDTOS){
				com.wolves.entity.app.User user = new com.wolves.entity.app.User();
				user.setPhone(userExcelDTO.getPhone());
				user = this.getUserByPhone(user);
				if (user != null && user.getUserId() != null){
					String r = user.getPhone()+",该号码已重复，请核实";
					throw new RuntimeException(r);
				}else {
					//判断企业是否存在

					//创建企业
					CompanyDTO companyDTO = new CompanyDTO();
					companyDTO.setType(Integer.valueOf(CompanyTypeEnum.out.getKey()));
					companyDTO.setStatus(Integer.valueOf(StatusEnum.INIT.getKey()));
					companyDTO.setCompanyName(userExcelDTO.getCompany());
					String companyId = UuidUtil.get32UUID();
					companyDTO.setCompanyId(companyId);
					companyService.saveCompany(companyDTO);
					//保存员工
					com.wolves.entity.app.User userInfo = new com.wolves.entity.app.User();
					userInfo.setUserId(UuidUtil.get32UUID());
					userInfo.setUsername(userExcelDTO.getName());
					String encrypt = MD5.md5("1");
					userInfo.setPassword(encrypt);
					userInfo.setPhone(userExcelDTO.getPhone());
					userInfo.setName(userExcelDTO.getName());
					userInfo.setEmail(userExcelDTO.getEmail());
					userInfo.setCompanyId(companyId);
					//身份证已经绑定
					this.saveUser(userInfo);
				}
			}
		}
	}

	public Integer updateUser(com.wolves.entity.app.User user){

		return (Integer) dao.update("UserMapper.updateUser", user);
	}
}
