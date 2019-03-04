package com.wolves.controller.system.user;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
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
import com.wolves.entity.Page;
import com.wolves.entity.system.Role;
import com.wolves.service.system.menu.MenuService;
import com.wolves.service.system.role.RoleService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.AppUtil;
import com.wolves.util.Const;
import com.wolves.util.FileDownload;
import com.wolves.util.FileUpload;
import com.wolves.util.GetPinyin;
import com.wolves.util.Jurisdiction;
import com.wolves.util.ObjectExcelRead;
import com.wolves.util.PageData;
import com.wolves.util.ObjectExcelView;
import com.wolves.util.PathUtil;
import com.wolves.util.Tools;

@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "user/listUsers.do";
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="menuService")
	private MenuService menuService;
	
	
	/**
	 * 保存用户
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU(PrintWriter out) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("USER_ID", this.get32UUID());
		pd.put("RIGHTS", "");
		pd.put("LAST_LOGIN", "");
		pd.put("IP", "");
		pd.put("STATUS", "0");
		pd.put("SKIN", "default");
		
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		
		if(null == userService.findByUId(pd)){
			//判断新增权限
			if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){userService.saveU(pd);}
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
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
		if(userService.findByUId(pd) != null){
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
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = this.getPageData();
		if(userService.findByUE(pd) != null){
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
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = this.getPageData();
		if(userService.findByUN(pd) != null){
			errInfo = "error";
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){userService.editU(pd);}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改用户页面
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String fx = pd.getString("fx");
		if("head".equals(fx)){
			mv.addObject("fx", "head");
		}else{
			mv.addObject("fx", "user");
		}
		List<Role> roleList = roleService.listAllERRoles();
		pd = userService.findByUiId(pd);
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**
	 * 去新增用户页面
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<Role> roleList;
		roleList = roleService.listAllERRoles();
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**
	 * 显示用户列表(用户组)
	 */
	@RequestMapping(value="/listUsers")
	public ModelAndView listUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
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
		page.setPd(pd);
		List<PageData>	userList = userService.listPdPageUser(page);
		List<Role> roleList = roleService.listAllERRoles();
		
		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());
		return mv;
	}

	
	/**
	 * 显示用户列表(tab方式)
	 */
	@RequestMapping(value="/listtabUsers")
	public ModelAndView listtabUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData>	userList = userService.listAllUser(pd);
		mv.setViewName("system/user/user_tb_list");
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
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			userService.deleteU(pd);
		}
		out.write("success");
		out.close();

	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String USER_IDS = pd.getString("USER_IDS");
		if(null != USER_IDS && !"".equals(USER_IDS)){
			String ArrayUSER_IDS[] = USER_IDS.split(",");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				userService.deleteAllU(ArrayUSER_IDS);
			}
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导出用户信息到EXCEL
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
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
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户名");
			titles.add("编号");
			titles.add("姓名");
			titles.add("职位");
			titles.add("手机");
			titles.add("邮箱");
			titles.add("最近登录");
			titles.add("上次登录IP");
			dataMap.put("titles", titles);
			List<PageData> userList = userService.listAllUser(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<userList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", userList.get(i).getString("USERNAME"));
				vpd.put("var2", userList.get(i).getString("NUMBER"));
				vpd.put("var3", userList.get(i).getString("NAME"));
				vpd.put("var4", userList.get(i).getString("ROLE_NAME"));
				vpd.put("var5", userList.get(i).getString("PHONE"));
				vpd.put("var6", userList.get(i).getString("EMAIL"));
				vpd.put("var7", userList.get(i).getString("LAST_LOGIN"));
				vpd.put("var8", userList.get(i).getString("IP"));
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		}
		return mv;
	}
	
	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}
	
	/**
	 * 下载模版
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Users.xls", "Users.xls");
	}
	
	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);
			pd.put("RIGHTS", "");
			pd.put("LAST_LOGIN", "");
			pd.put("IP", "");
			pd.put("STATUS", "0");
			pd.put("SKIN", "default");
			List<Role> roleList = roleService.listAllERRoles();
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());
			//文件上传路径
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;
			//执行上传
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");

			//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);

			/*存入数据库操作======================================*/
			//权限
			pd.put("RIGHTS", "");
			//最后登录时间
			pd.put("LAST_LOGIN", "");
			//IP
			pd.put("IP", "");
			//状态
			pd.put("STATUS", "0");
			//默认皮肤
			pd.put("SKIN", "default");

			//列出所有二级角色
			List<Role> roleList = roleService.listAllERRoles();
			//设置角色ID为随便第一个
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			for(int i=0;i<listPd.size();i++){
				//ID
				pd.put("USER_ID", this.get32UUID());
				//姓名
				pd.put("NAME", listPd.get(i).getString("var1"));
				//根据姓名汉字生成全拼
				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"));
				pd.put("USERNAME", USERNAME);	
				if(userService.findByUId(pd) != null){
					//判断用户名是否重复
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"))+Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
				//备注
				pd.put("BZ", listPd.get(i).getString("var4"));
				//邮箱格式不对就跳过
				if(Tools.checkEmail(listPd.get(i).getString("var3"))){
					pd.put("EMAIL", listPd.get(i).getString("var3"));
					//邮箱已存在就跳过
					if(userService.findByUE(pd) != null){
						continue;
					}
				}else{
					continue;
				}
				//编号已存在就跳过
				pd.put("NUMBER", listPd.get(i).getString("var0"));
				//手机号
				pd.put("PHONE", listPd.get(i).getString("var2"));
				//默认密码123
				pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, "123").toString());
				if(userService.findByUN(pd) != null){
					continue;
				}
				userService.saveU(pd);
			}
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
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
