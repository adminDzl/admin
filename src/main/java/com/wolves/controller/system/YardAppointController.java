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

import com.wolves.common.YardTypeEnum;
import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.Page;
import com.wolves.service.system.yardappoint.YardAppointService;
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
 * 类名称：YardAppointController
 * 创建时间：2019-02-24
 * @author gf
 */
@Controller
@RequestMapping(value="/yardappoint")
public class YardAppointController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "yardappoint/list.do";
	@Resource(name="yardappointService")
	private YardAppointService yardappointService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增YardAppoint");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//主键
		pd.put("YARDAPPOINT_ID", this.get32UUID());
		//场地ID
		pd.put("PLACE_ID", "");
		//预定人ID
		pd.put("APPLY_USER_ID", "");
		//预定金额
		pd.put("BOOK_FEE", "");
		//预定状态
		pd.put("STATUS", 0);
		//创建时间
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));
		//更新时间
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
		yardappointService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除YardAppoint");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = this.getPageData();
		yardappointService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改YardAppoint");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		yardappointService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表YardAppoint");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = yardappointService.list(page);
		mv.setViewName("system/yardappoint/yardappoint_list");
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
		logBefore(logger, "去新增YardAppoint页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/yardappoint/yardappoint_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改YardAppoint页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = yardappointService.findById(pd);
		mv.setViewName("system/yardappoint/yardappoint_edit");
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
		logBefore(logger, "批量删除YardAppoint");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			yardappointService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 导出付费的用户信息包括【申请单位名称、单位联系人、联系电话、预约场地、使用费用、会议/培训名称（若有）、会议/培训时间、使用时间段（格式：上午/下午 xx时-上午/下午xx时）】
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出YardAppoint到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("申请单位名称");
			titles.add("单位联系人");
			titles.add("联系电话");
			titles.add("预约场地");
			titles.add("使用费用");
			titles.add("会议/培训名称");
			titles.add("会议/培训时间");
			dataMap.put("titles", titles);
			List<PageData> varOList = yardappointService.listYardAppoint(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				if (StringUtils.isNotEmpty(varOList.get(i).getString("company_name"))){
					vpd.put("var1", varOList.get(i).getString("company_name"));
				}else {
					vpd.put("var1", "-");
				}
				if (StringUtils.isNotEmpty(varOList.get(i).getString("name"))){
					vpd.put("var2", varOList.get(i).getString("name"));
				}else {
					vpd.put("var2", "-");
				}
				if (StringUtils.isNotEmpty(varOList.get(i).getString("phone"))){
					vpd.put("var3", varOList.get(i).getString("phone"));
				}else {
					vpd.put("var3", "-");
				}
				String position = varOList.get(i).getString("position");
				String type = varOList.get(i).get("place_type").toString();
				if (StringUtils.isNotEmpty(type)){
					vpd.put("var4", YardTypeEnum.queryValueByKey(type)+"-"+position);
				}
				vpd.put("var5", varOList.get(i).getString("book_fee"));

				vpd.put("var6", "-");
				String startTime = varOList.get(i).getString("begin_time");
				String endTime = varOList.get(i).getString("end_time");
				if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
					vpd.put("var7", sdf.format(formatter.parse(startTime))+" - "+sdf.format(formatter.parse(endTime)));
				}else {
					vpd.put("var7", "-");
				}

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