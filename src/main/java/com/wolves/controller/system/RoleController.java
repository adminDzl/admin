package com.wolves.controller.system;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wolves.controller.base.BaseController;
import com.wolves.entity.Page;
import com.wolves.entity.system.Menu;
import com.wolves.entity.system.Role;
import com.wolves.service.system.menu.MenuService;
import com.wolves.service.system.role.RoleService;
import com.wolves.util.AppUtil;
import com.wolves.util.Const;
import com.wolves.util.Jurisdiction;
import com.wolves.util.PageData;
import com.wolves.util.RightsHelper;
import com.wolves.util.Tools;

@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	String menuUrl = "role.do";
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="roleService")
	private RoleService roleService;
	
	/**
	 * 权限(增删改查)
	 */
	@RequestMapping(value="/qx")
	public ModelAndView qx()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String msg = pd.getString("msg");
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.updateQx(msg,pd);}
		mv.setViewName("save_result");
		mv.addObject("msg","success");
		return mv;
	}
	
	/**
	 * K权限
	 */
	@RequestMapping(value="/kfqx")
	public ModelAndView kfqx()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String msg = pd.getString("msg");
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.updateKFQx(msg,pd);}
		mv.setViewName("save_result");
		mv.addObject("msg","success");
		return mv;
	}
	
	/**
	 * c权限
	 */
	@RequestMapping(value="/gysqxc")
	public ModelAndView gysqxc()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String msg = pd.getString("msg");
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.gysqxc(msg,pd);}
		mv.setViewName("save_result");
		mv.addObject("msg","success");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping
	public ModelAndView list(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String roleId = pd.getString("ROLE_ID");
		if(roleId == null || "".equals(roleId)){
			pd.put("ROLE_ID", "1");
		}
		List<Role> roleList = roleService.listAllRoles();
		List<Role> roleList_z = roleService.listAllRolesByPId(pd);
		List<PageData> kefuqxlist = roleService.listAllkefu(pd);
		List<PageData> gysqxlist = roleService.listAllGysQX(pd);
		pd = roleService.findObjectById(pd);
		mv.addObject("pd", pd);
		mv.addObject("kefuqxlist", kefuqxlist);
		mv.addObject("gysqxlist", gysqxlist);
		mv.addObject("roleList", roleList);
		mv.addObject("roleList_z", roleList_z);
		mv.setViewName("system/role/role_list");
		mv.addObject(Const.SESSION_QX,this.getHC());
		return mv;
	}
	
	/**
	 * 新增页面
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/role/role_add");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 保存新增信息
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
			
		String parent_id = pd.getString("PARENT_ID");
		pd.put("ROLE_ID", parent_id);
		if("0".equals(parent_id)){
			pd.put("RIGHTS", "");
		}else{
			String rights = roleService.findObjectById(pd).getString("RIGHTS");
			pd.put("RIGHTS", (null == rights)?"":rights);
		}
		pd.put("QX_ID", "");
		String UUID = this.get32UUID();
		pd.put("GL_ID", UUID);
		//发信权限
		pd.put("FX_QX", 0);
		//服务权限
		pd.put("FW_QX", 0);
		//操作权限
		pd.put("QX1", 0);
		//产品权限
		pd.put("QX2", 0);
		//预留权限
		pd.put("QX3", 0);
		//预留权限
		pd.put("QX4", 0);
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			roleService.saveKeFu(pd);
		}
		pd.put("U_ID", UUID);
		//每日发信数量
		pd.put("C1", 0);
		pd.put("C2", 0);
		pd.put("C3", 0);
		pd.put("C4", 0);
		pd.put("Q1", 0);
		pd.put("Q2", 0);
		pd.put("Q3", 0);
		pd.put("Q4", 0);
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			roleService.saveGYSQX(pd);
		}
		pd.put("QX_ID", UUID);
		pd.put("ROLE_ID", UUID);
		pd.put("ADD_QX", "0");
		pd.put("DEL_QX", "0");
		pd.put("EDIT_QX", "0");
		pd.put("CHA_QX", "0");
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			roleService.add(pd);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 请求编辑
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String ROLE_ID )throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("ROLE_ID", ROLE_ID);
		pd = roleService.findObjectById(pd);
		mv.setViewName("system/role/role_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			roleService.edit(pd);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 请求角色菜单授权页面
	 */
	@RequestMapping(value="/auth")
	public String auth(@RequestParam String ROLE_ID,Model model)throws Exception{
		List<Menu> menuList = menuService.listAllMenu();
		Role role = roleService.getRoleById(ROLE_ID);
		String roleRights = role.getRIGHTS();
		if(Tools.notEmpty(roleRights)){
			for(Menu menu : menuList){
				menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
				if(menu.isHasMenu()){
					List<Menu> subMenuList = menu.getSubMenu();
					for(Menu sub : subMenuList){
						sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
					}
				}
			}
		}
		JSONArray arr = JSONArray.fromObject(menuList);
		String json = arr.toString();
		json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("roleId", ROLE_ID);
		return "authorization";
	}
	
	/**
	 * 请求角色按钮授权页面
	 */
	@RequestMapping(value="/button")
	public ModelAndView button(@RequestParam String ROLE_ID,@RequestParam String msg,Model model)throws Exception{
		ModelAndView mv = this.getModelAndView();
		List<Menu> menuList = menuService.listAllMenu();
		Role role = roleService.getRoleById(ROLE_ID);
		String roleRights = "";
		if("add_qx".equals(msg)){
			roleRights = role.getADD_QX();
		}else if("del_qx".equals(msg)){
			roleRights = role.getDEL_QX();
		}else if("edit_qx".equals(msg)){
			roleRights = role.getEDIT_QX();
		}else if("cha_qx".equals(msg)){
			roleRights = role.getCHA_QX();
		}
		if(Tools.notEmpty(roleRights)){
			for(Menu menu : menuList){
				menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
				if(menu.isHasMenu()){
					List<Menu> subMenuList = menu.getSubMenu();
					for(Menu sub : subMenuList){
						sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
					}
				}
			}
		}
		JSONArray arr = JSONArray.fromObject(menuList);
		String json = arr.toString();
		json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
		mv.addObject("zTreeNodes", json);
		mv.addObject("roleId", ROLE_ID);
		mv.addObject("msg", msg);
		mv.setViewName("system/role/role_button");
		return mv;
	}
	
	/**
	 * 保存角色菜单权限
	 */
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam String ROLE_ID,@RequestParam String menuIds,PrintWriter out)throws Exception{
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				Role role = roleService.getRoleById(ROLE_ID);
				role.setRIGHTS(rights.toString());
				roleService.updateRoleRights(role);
				pd.put("rights",rights.toString());
			}else{
				Role role = new Role();
				role.setRIGHTS("");
				role.setROLE_ID(ROLE_ID);
				roleService.updateRoleRights(role);
				pd.put("rights","");
			}

				pd.put("roleId", ROLE_ID);
				roleService.setAllRights(pd);
		}
		out.write("success");
		out.close();
	}
	
	/**
	 * 保存角色按钮权限
	 */
	@RequestMapping(value="/roleButton/save")
	public void orleButton(@RequestParam String ROLE_ID,@RequestParam String menuIds,@RequestParam String msg,PrintWriter out)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				pd.put("value",rights.toString());
			}else{
				pd.put("value","");
			}
			pd.put("ROLE_ID", ROLE_ID);
			roleService.updateQx(msg,pd);
		}
		out.write("success");
		out.close();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object deleteRole(@RequestParam String ROLE_ID)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String errInfo = "";
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			pd.put("ROLE_ID", ROLE_ID);
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);
			if(roleList_z.size() > 0){
				errInfo = "false";
			}else{
				List<PageData> userlist = roleService.listAllUByRid(pd);
				List<PageData> appuserlist = roleService.listAllAppUByRid(pd);
				if(userlist.size() > 0 || appuserlist.size() > 0){
					errInfo = "false2";
				}else{roleService.deleteRoleById(ROLE_ID);roleService.deleteKeFuById(ROLE_ID);roleService.deleteGById(ROLE_ID);errInfo = "success";
				}
			}
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}

}
