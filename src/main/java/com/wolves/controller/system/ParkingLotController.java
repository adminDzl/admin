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

import com.wolves.common.StatusEnum;
import com.wolves.entity.system.Page;
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
import com.wolves.controller.base.BaseController;
import com.wolves.util.AppUtil;
import com.wolves.util.ObjectExcelView;
import com.wolves.util.Const;
import com.wolves.util.PageData;
import com.wolves.util.Jurisdiction;
import com.wolves.service.system.parkinglot.ParkingLotService;

@Controller
@RequestMapping(value="/parkinglot")
public class ParkingLotController extends BaseController {

	//菜单地址(权限用)
	String menuUrl = "parkinglot/list.do";
	@Resource(name="parkinglotService")
	private ParkingLotService parkinglotService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增ParkingLot");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("PARKING_LOT_ID", this.get32UUID());
		pd.put("STATUS", StatusEnum.INIT.getKey());
		parkinglotService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除ParkingLot");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = this.getPageData();
		parkinglotService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改ParkingLot");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		parkinglotService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 剩余车位列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表ParkingLot");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = parkinglotService.list(page);
		mv.setViewName("system/parkinglot/parkinglot_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());
		return mv;
	}

	/**
	 * 车位归属列表
	 */
	@RequestMapping(value="/list_own")
	public ModelAndView list_own(Page page){
		logBefore(logger, "车位归属列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = parkinglotService.listOwn(page);
		mv.setViewName("system/parkinglot/parkinglot_own_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增ParkingLot页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/parkinglot/parkinglot_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改ParkingLot页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = parkinglotService.findById(pd);
		mv.setViewName("system/parkinglot/parkinglot_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit_own")
	public ModelAndView goEditOwn(){
		logBefore(logger, "去修改ParkingLotOwn页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = parkinglotService.findById(pd);
		mv.setViewName("system/parkinglot/parkinglot_edit_own");
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
		logBefore(logger, "批量删除ParkingLot");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		PageData pd = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				parkinglotService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出ParkingLot到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("ID");
			titles.add("车位编号");
			titles.add("车位所在位置");
			titles.add("用户id");
			titles.add("状态");
			titles.add("创建时间");
			titles.add("更新时间");
			dataMap.put("titles", titles);
			List<PageData> varOList = parkinglotService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("ID"));
				vpd.put("var2", varOList.get(i).getString("LOT_NO"));
				vpd.put("var3", varOList.get(i).getString("LOCATION"));
				vpd.put("var4", varOList.get(i).getString("USER_ID"));
				vpd.put("var5", varOList.get(i).get("STATUS").toString());
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