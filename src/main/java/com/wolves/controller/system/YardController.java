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
import com.wolves.dto.YardDTO;
import com.wolves.entity.system.Page;
import com.wolves.service.system.yard.YardService;
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

/** 
 * 类名称：YardController
 * 创建时间：2019-02-24
 * @author gf
 */
@Controller
@RequestMapping(value="/yard")
public class YardController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "yard/list.do";
	@Resource(name="yardService")
	private YardService yardService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Yard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("YARD_ID", UuidUtil.get32UUID());
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
		yardService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Yard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			yardService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 删除图片
	 * @param out
	 */
	@RequestMapping(value="/delImage")
	public void delImage(PrintWriter out){
		logBefore(logger, "删除场地图片");
		try{
			PageData pd = this.getPageData();
			String PATH = pd.getString("IMAGE_URL");
			String YARD_ID = pd.getString("YARD_ID");
			yardService.delImage(YARD_ID, PATH);
			out.write("success");
			out.close();
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Yard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String PATH = pd.getString("IMAGE_URL");
		String YARD_ID = pd.getString("YARD_ID");
		YardDTO yardDTO = yardService.getYardById(YARD_ID);
		if (StringUtils.isNotEmpty(PATH)){
			if (yardDTO.getImageUrl().equals(PATH)){
				pd.put("IMAGE_URL", PATH);
			}else {
				StringBuffer buf = new StringBuffer();
				buf.append(yardDTO.getImageUrl()).append(",");
				buf.append(PATH);
				pd.put("IMAGE_URL", java.net.URLDecoder.decode(String.valueOf(buf)));
			}
		}
		yardService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Yard");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		//列出Yard列表
		List<PageData>	varList = yardService.list(page);
		mv.setViewName("system/yard/yard_list");
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
		logBefore(logger, "去新增Yard页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/yard/yard_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Yard页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = yardService.findById(pd);
		mv.setViewName("system/yard/yard_edit");
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
		logBefore(logger, "批量删除Yard");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			yardService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Yard到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("场地类型");
			titles.add("所处位置");
			titles.add("图片url");
			titles.add("设备描述");
			titles.add("价格");
			titles.add("创建时间");
			titles.add("更新时间");
			dataMap.put("titles", titles);
			List<PageData> varOList = yardService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).get("PLACE_TYPE").toString());
				vpd.put("var2", varOList.get(i).getString("POSITION"));
				vpd.put("var3", varOList.get(i).getString("IMAGE_URL"));
				vpd.put("var4", varOList.get(i).getString("EQUIPMENT"));
				vpd.put("var5", varOList.get(i).getString("RENT_FEE"));
				vpd.put("var6", varOList.get(i).getString("CREATE_TIME"));
				vpd.put("var7", varOList.get(i).getString("UPDATE_TIME"));
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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