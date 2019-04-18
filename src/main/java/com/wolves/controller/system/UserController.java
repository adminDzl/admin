package com.wolves.controller.system;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.dto.user.UserExcelDTO;
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.Page;
import com.wolves.entity.system.Role;
import com.wolves.service.system.appuser.AppUserService;
import com.wolves.service.system.role.RoleService;

@Controller
@RequestMapping(value="/appuser")
public class UserController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "appuser/listUsers.do";
	@Resource(name="appUserService")
	private AppUserService appUserService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="companyService")
	CompanyService companyService;
	@Resource(name="userService")
	private UserService userService;

	/**
	 * 保存用户
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("USER_ID", this.get32UUID());
		pd.put("PASSWORD", MD5.md5(pd.getString("PASSWORD")));
		if(null == appUserService.findByUId(pd)){
			if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){appUserService.saveU(pd);}
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", MD5.md5(pd.getString("PASSWORD")));
		}
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			appUserService.editU(pd);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 判断用户名是否存在
	 */
	@RequestMapping(value="/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = this.getPageData();
		if(appUserService.findByUId(pd) != null){
			errInfo = "error";
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 判断邮箱是否存在
	 */
	@RequestMapping(value="/hasE")
	@ResponseBody
	public Object hasE(){
		Map<String,String> map = new HashMap<String,String>(8);
		String errInfo = "success";
		PageData pd = this.getPageData();
		if(appUserService.findByUE(pd) != null){
			errInfo = "error";
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 判断编码是否存在
	 */
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>(8);
		String errInfo = "success";
		PageData pd = this.getPageData();
		if(appUserService.findByUN(pd) != null){
			errInfo = "error";
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 去修改用户页面
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<Role> roleList = roleService.listAllappERRoles();
		pd = appUserService.findByUiId(pd);
		mv.setViewName("system/appuser/appuser_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**
	 * 去新增用户页面
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<Role> roleList = roleService.listAllappERRoles();
		mv.setViewName("system/appuser/appuser_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**
	 * 显示用户列表
	 */
	@RequestMapping(value="/listUsers")
	public ModelAndView listUsers(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String USERNAME = pd.getString("USERNAME");
		if(null != USERNAME && !"".equals(USERNAME)){
			USERNAME = USERNAME.trim();
			pd.put("USERNAME", USERNAME);
		}
		page.setPd(pd);
		List<PageData> userList = appUserService.listPdPageUser(page);

		mv.setViewName("system/appuser/appuser_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());
		return mv;
	}

	/**
	 * 删除用户
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out){
		PageData pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){appUserService.deleteU(pd);}
		out.write("success");
		out.close();
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() {
		Map<String,Object> map = new HashMap<String,Object>(8);
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>(8);
		String USER_IDS = pd.getString("USER_IDS");
		if(null != USER_IDS && !"".equals(USER_IDS)){
			String ArrayUSER_IDS[] = USER_IDS.split(",");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				appUserService.deleteAllU(ArrayUSER_IDS);
			}
			pd.put("msg", "ok");
		}else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导入客户数据
	 */
	@RequestMapping(value="/importExcel")
	@ResponseBody
	public Object importExcel(@RequestParam(value="uploadFile") MultipartFile file){
		logger.info("客户数据excel导入-->/importExcel");
		Map<String,Object> map = new HashMap<String,Object>(8);
		List<PageData> pdList = new ArrayList<PageData>(8);
		PageData pd = this.getPageData();
		ImportExcelUtil importExcelUtil = new ImportExcelUtil();
		List<Map<String, Object>> userList = importExcelUtil.getExcelInfo(file);
		if (userList != null && !userList.isEmpty() && userList.size() < 50000){
			List<UserExcelDTO> userExcelDTOS = userService.getUserData(userList);
			//判断
			pd = userService.checkExcelData(userList, pd);
			if (pd.isEmpty()){
				//保存数据
				pd = userService.saveExcelUser(userExcelDTOS, pd);
				if (pd.isEmpty()){
					pd.put("msg", "ok");
				}
			}
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 导出会员信息到excel
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){	
				//检索条件===
				String USERNAME = pd.getString("USERNAME");
				if(null != USERNAME && !"".equals(USERNAME)){
					USERNAME = USERNAME.trim();
					pd.put("USERNAME", USERNAME);
				}
				String lastLoginStart = pd.getString("lastLoginStart");
				String lastLoginEnd = pd.getString("lastLoginEnd");
				if(lastLoginStart != null && !"".equals(lastLoginStart)){
					lastLoginStart = lastLoginStart+" 00:00:00";
					pd.put("lastLoginStart", lastLoginStart);
				}
				if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
					lastLoginEnd = lastLoginEnd+" 00:00:00";
					pd.put("lastLoginEnd", lastLoginEnd);
				}
				
				Map<String,Object> dataMap = new HashMap<String,Object>(8);
				List<String> titles = new ArrayList<String>(16);
				
				titles.add("用户名");
				titles.add("编号");
				titles.add("姓名");
				titles.add("手机号");
				titles.add("身份证号");
				titles.add("等级");
				titles.add("邮箱");
				titles.add("最近登录");
				titles.add("到期时间");
				titles.add("上次登录IP");
				
				dataMap.put("titles", titles);
				
				List<PageData> userList = appUserService.listAllUser(pd);
				List<PageData> varList = new ArrayList<PageData>(8);
				for(int i=0;i<userList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", userList.get(i).getString("USERNAME"));
					vpd.put("var2", userList.get(i).getString("NUMBER"));
					vpd.put("var3", userList.get(i).getString("NAME"));
					vpd.put("var4", userList.get(i).getString("PHONE"));
					vpd.put("var5", userList.get(i).getString("SFID"));
					vpd.put("var6", userList.get(i).getString("ROLE_NAME"));
					vpd.put("var7", userList.get(i).getString("EMAIL"));
					vpd.put("var8", userList.get(i).getString("LAST_LOGIN"));
					vpd.put("var9", userList.get(i).getString("END_TIME"));
					vpd.put("var10", userList.get(i).getString("IP"));
					varList.add(vpd);
				}
				
				dataMap.put("varList", varList);
				
				ObjectExcelView erv = new ObjectExcelView();
				mv = new ModelAndView(erv,dataMap);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}

	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
}
