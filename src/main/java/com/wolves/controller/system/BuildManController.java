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

import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.Page;
import com.wolves.service.system.buildman.BuildManService;
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
 * 类名称：BuildManController
 * 创建时间：2019-02-23
 * @author gf
 */
@Controller
@RequestMapping(value="/buildman")
public class BuildManController extends BaseController {
	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "buildman/list.do";
	@Resource(name="buildmanService")
	private BuildManService buildmanService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增BuildMan");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//主键
		pd.put("BUILDMAN_ID", this.get32UUID());
		buildmanService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除BuildMan");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = this.getPageData();
		buildmanService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改BuildMan");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		buildmanService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表BuildMan");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		//列出BuildMan列表
		List<PageData> varList = buildmanService.list(page);
		mv.setViewName("system/buildman/buildman_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		//按钮权限
		mv.addObject(Const.SESSION_QX,this.getHC());
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增BuildMan页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/buildman/buildman_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改BuildMan页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//根据ID读取
		pd = buildmanService.findById(pd);
		mv.setViewName("system/buildman/buildman_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除BuildMan");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			buildmanService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出BuildMan到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		PageData pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("id");
		titles.add("楼栋");
		titles.add("楼长姓名");
		titles.add("联系方式");
		titles.add("创建时间");
		dataMap.put("titles", titles);
		List<PageData> varOList = buildmanService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("ID").toString());
			vpd.put("var2", varOList.get(i).getString("BUILD_NAME"));
			vpd.put("var3", varOList.get(i).getString("BUILD_MASTER_NAME"));
			vpd.put("var4", varOList.get(i).getString("MASTER_TEL"));
			vpd.put("var5", varOList.get(i).getString("CREATE_TIME"));
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		ModelAndView mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	public Map<String, String> getHC(){
		//shiro管理的session
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