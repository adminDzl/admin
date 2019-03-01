package com.wolves.controller.system.yard;

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
import com.wolves.entity.Page;
import com.wolves.service.information.pictures.PicturesService;
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
 * 创建人：FH 
 * 创建时间：2019-02-24
 */
@Controller
@RequestMapping(value="/yard")
public class YardController extends BaseController {
	
	String menuUrl = "yard/list.do"; //菜单地址(权限用)
	@Resource(name="yardService")
	private YardService yardService;

	@Resource(name="picturesService")
	private PicturesService picturesService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Yard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("YARD_ID", this.get32UUID());	//主键
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));	//更新时间
		yardService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 新增
	 */
	@RequestMapping(value="/imageUpload")
	@ResponseBody
	public Object imageUpload(@RequestParam(required=false) MultipartFile[] files) throws Exception{
		logBefore(logger, "新增Pictures");
		Map<String,String> map = new HashMap<String,String>();
		String fileAddress = DateUtil.getDays(), fileName = "";
		List<PageData> pageDatas = new ArrayList<PageData>();
		if (files != null && files.length > 0){
			for (MultipartFile file : files){
				if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
					PageData pd = new PageData();
					if (null != file && !file.isEmpty()) {
						String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + fileAddress;		//文件上传路径
						fileName = FileUpload.fileUp(file, filePath, this.get32UUID());				//执行上传
					}else{
						System.out.println("上传失败");
					}
					pd.put("PICTURES_ID", this.get32UUID());			//主键
					pd.put("TITLE", "图片");							//标题
					pd.put("NAME", fileName);							//文件名
					pd.put("PATH", fileAddress + "/" + fileName);				//路径
					pd.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
					pd.put("MASTER_ID", "1");							//附属与
					pd.put("BZ", "场地管理处上传");						//备注
					//加水印
					picturesService.save(pd);
					pageDatas.add(pd);
				}
			}
		}
		map.put("result", "ok");
		map.put("callback", pageDatas.toString());
		return map;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Yard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
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
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Yard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
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
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = yardService.list(page);	//列出Yard列表
			mv.setViewName("system/yard/yard_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
		try {
			mv.setViewName("system/yard/yard_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
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
		try {
			pd = yardService.findById(pd);	//根据ID读取
			mv.setViewName("system/yard/yard_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Yard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
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
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/*
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
			titles.add("场地类型");	//1
			titles.add("所处位置");	//2
			titles.add("图片url");	//3
			titles.add("设备描述");	//4
			titles.add("价格");	//5
			titles.add("创建时间");	//6
			titles.add("更新时间");	//7
			dataMap.put("titles", titles);
			List<PageData> varOList = yardService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).get("PLACE_TYPE").toString());	//1
				vpd.put("var2", varOList.get(i).getString("POSITION"));	//2
				vpd.put("var3", varOList.get(i).getString("IMAGE_URL"));	//3
				vpd.put("var4", varOList.get(i).getString("EQUIPMENT"));	//4
				vpd.put("var5", varOList.get(i).getString("RENT_FEE"));	//5
				vpd.put("var6", varOList.get(i).getString("CREATE_TIME"));	//6
				vpd.put("var7", varOList.get(i).getString("UPDATE_TIME"));	//7
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
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}