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
import javax.servlet.http.HttpServletRequest;
import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.Page;
import com.wolves.service.system.buildbody.BuildBodyService;
import com.wolves.service.system.buildman.BuildManService;
import com.wolves.service.system.floorman.FloorManService;
import com.wolves.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/** 
 * 类名称：FloorManController
 * 创建时间：2019-02-23
 * @author gf
 */
@Controller
@RequestMapping(value="/floorman")
public class FloorManController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "floorman/list.do";
	@Resource(name="floormanService")
	private FloorManService floormanService;
	@Resource(name="buildmanService")
	private BuildManService buildmanService;
	@Resource(name="buildBodyService")
	private BuildBodyService buildBodyService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增FloorMan");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//主键
		pd.put("FLOORMAN_ID", this.get32UUID());
		floormanService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除FloorMan");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = new PageData();
		pd = this.getPageData();
		floormanService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改FloorMan");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		floormanService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表FloorMan");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = floormanService.list(page);
		mv.setViewName("system/floorman/floorman_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());
		return mv;
	}



	@RequestMapping(value="/selectByBodyId")
	@ResponseBody
	public List<PageData> selectByBodyId(HttpServletRequest request){
		Integer bodyId = Integer.parseInt(request.getParameter("bodyId"));
		return floormanService.getByBodyId(bodyId);
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增FloorMan页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/floorman/floorman_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("buildman",buildmanService.listAll(pd));
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改FloorMan页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = floormanService.findById(pd);
		mv.setViewName("system/floorman/floorman_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("buildman",buildmanService.listAll(pd));
		String buildId = pd.getString("BUILD_NO");
		mv.addObject("buildbodys",buildBodyService.getByBuildId(buildId));
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除FloorMan");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			floormanService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出FloorMan到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id");
		titles.add("楼栋号");
		titles.add(" 楼层");
		titles.add("层长姓名");
		titles.add("联系方式");
		titles.add("创建时间");
		dataMap.put("titles", titles);
		List<PageData> varOList = floormanService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("ID").toString());
			vpd.put("var2", varOList.get(i).getString("BUILD_NO"));
			vpd.put("var3", varOList.get(i).getString("FLOOR"));
			vpd.put("var4", varOList.get(i).getString("FLOOR_MASTER_NAME"));
			vpd.put("var5", varOList.get(i).getString("MASTER_TEL"));
			vpd.put("var6", varOList.get(i).getString("CREATE_TIME"));
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		return new ModelAndView(erv,dataMap);
	}
	
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}