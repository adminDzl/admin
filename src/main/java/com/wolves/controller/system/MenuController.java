package com.wolves.controller.system;

import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.Menu;
import com.wolves.service.system.menu.MenuService;
import com.wolves.util.PageData;

@Controller
@RequestMapping(value="/menu")
public class MenuController extends BaseController {

	@Resource(name="menuService")
	private MenuService menuService;
	
	/**
	 * 显示菜单列表
	 */
	@RequestMapping
	public ModelAndView list()throws Exception{
		ModelAndView mv = this.getModelAndView();
		List<Menu> menuList = menuService.listAllParentMenu();
		mv.addObject("menuList", menuList);
		mv.setViewName("system/menu/menu_list");
		return mv;
	}
	
	/**
	 * 请求新增菜单页面
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		List<Menu> menuList = menuService.listAllParentMenu();
		mv.addObject("menuList", menuList);
		mv.setViewName("system/menu/menu_add");
		return mv;
	}
	
	/**
	 * 保存菜单信息
	 */
	@RequestMapping(value="/add")
	public ModelAndView add(Menu menu)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		menu.setMENU_ID(String.valueOf(Integer.parseInt(menuService.findMaxId(pd).get("MID").toString())+1));
		String PARENT_ID = menu.getPARENT_ID();
		if(!"0".equals(PARENT_ID)){
			pd.put("MENU_ID",PARENT_ID);
			pd = menuService.getMenuById(pd);
			menu.setMENU_TYPE(pd.getString("MENU_TYPE"));
		}
		menuService.saveMenu(menu);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
		
	}
	
	/**
	 * 请求编辑菜单页面
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit(String MENU_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("MENU_ID",MENU_ID);
		pd = menuService.getMenuById(pd);
		List<Menu> menuList = menuService.listAllParentMenu();
		mv.addObject("menuList", menuList);
		mv.addObject("pd", pd);
		mv.setViewName("system/menu/menu_edit");
		return mv;
	}
	
	/**
	 * 请求编辑菜单图标页面
	 */
	@RequestMapping(value="/toEditicon")
	public ModelAndView toEditicon(String MENU_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("MENU_ID",MENU_ID);
		mv.addObject("pd", pd);
		mv.setViewName("system/menu/menu_icon");
		return mv;
	}
	
	/**
	 * 保存菜单图标 (顶部菜单)
	 */
	@RequestMapping(value="/editicon")
	public ModelAndView editicon()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		menuService.editicon(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 保存编辑
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String PARENT_ID = pd.getString("PARENT_ID");
		if(null == PARENT_ID || "".equals(PARENT_ID)){
			PARENT_ID = "0";
			pd.put("PARENT_ID", PARENT_ID);
		}
		if("0".equals(PARENT_ID)){
			menuService.editType(pd);
		}
		menuService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 获取当前菜单的所有子菜单
	 */
	@RequestMapping(value="/sub")
	public void getSub(@RequestParam String MENU_ID,HttpServletResponse response)throws Exception {
		List<Menu> subMenu = menuService.listSubMenuByParentId(MENU_ID);
		JSONArray arr = JSONArray.fromObject(subMenu);
		PrintWriter out;
		response.setCharacterEncoding("utf-8");
		out = response.getWriter();
		String json = arr.toString();
		out.write(json);
		out.flush();
		out.close();
	}
	
	/**
	 * 删除菜单
	 */
	@RequestMapping(value="/del")
	public void delete(@RequestParam String MENU_ID,PrintWriter out)throws Exception{
		menuService.deleteMenuById(MENU_ID);
		out.write("success");
		out.flush();
		out.close();
	}
}
