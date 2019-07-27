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

import com.wolves.dto.user.CompanyDTO;
import com.wolves.dto.user.UserExcelDTO;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
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
import com.wolves.service.system.CompanyService;

@Controller
@RequestMapping(value="/company")
public class CompanyController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "company/list.do";
	@Resource(name="companyService")
	private CompanyService companyService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Company");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("COMPANY_ID", super.get32UUID());
		pd.put("CREATE_TIME", new Date());
		companyService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Company");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		try{
			PageData pd = this.getPageData();
			companyService.delete(pd);
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
		logBefore(logger, "修改Company");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("UPDATE_TIME", new Date());
		companyService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Company");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> varList = companyService.list(page);
		mv.setViewName("system/company/company_list");
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
		logBefore(logger, "去新增Company页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			mv.setViewName("system/company/company_edit");
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
		logBefore(logger, "去修改Company页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			pd = companyService.findById(pd);
			mv.setViewName("system/company/company_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

	/**
	 * 去审核页面
	 */
	@RequestMapping(value="/goCheck")
	public ModelAndView goCheck(){
		logBefore(logger, "去修改Company页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			pd = companyService.findById(pd);
			mv.setViewName("system/company/company_check");
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
		logBefore(logger, "批量删除Company");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		PageData pd = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				companyService.deleteAll(ArrayDATA_IDS);
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

	@RequestMapping(value="/importCompanyExcel")
	@ResponseBody
	public Object importCompanyExcel(@RequestParam(value="uploadFile") MultipartFile file){
		logger.info("客户数据excel导入-->/importExcel");
		PageData pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>(10);
		List<PageData> pdList = new ArrayList<PageData>();
		ImportExcelUtil importExcelUtil = new ImportExcelUtil();
		List<Map<String, Object>> companyList = importExcelUtil.getExcelInfo(file);
		if (companyList != null && !companyList.isEmpty() && companyList.size() < 50000){
			List<CompanyDTO> companyDTOS = companyService.getCompanyData(companyList);
			pd = companyService.checkExcelData(companyList, pd);
			String status = pd.get("status").toString();
			if (status.equals("0")){
				companyService.createCompanyByExcel(companyDTOS);
				pd.put("msg", "ok");
			}
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
		logBefore(logger, "导出Company到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("企业名");
			titles.add("企业类型");
			titles.add("入驻时间");
			titles.add("合同期");
			titles.add("企业规模");
			titles.add("员工数量");
			titles.add("场地数量");
			titles.add("场地面积");
			titles.add("状态");
			titles.add("企业认证");
			dataMap.put("titles", titles);
			List<PageData> varOList = companyService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("COMPANY_NAME"));
				String type = varOList.get(i).get("TYPE").toString();
				String companyType = "";
				if ("1".equals(type)){
					companyType = "园区公司";
				}else {
					companyType = "入驻公司";
				}
				vpd.put("var2", companyType);
				Date commTime = (Date) varOList.get(i).get("COME_TIME");
				if(null != commTime){
					vpd.put("var3", DateUtil.convertDate2String("yyyy-MM-dd", commTime));
				} else {
					vpd.put("var3", "");
				}
				Date agreementTime = (Date) varOList.get(i).get("AGREEMENT_TIME");
				if(null != agreementTime){
					vpd.put("var4", DateUtil.convertDate2String("yyyy-MM-dd", agreementTime));
				} else {
					vpd.put("var4", "");
				}
				vpd.put("var5", varOList.get(i).getString("SCALE"));
				vpd.put("var6", varOList.get(i).get("NUM").toString());
				vpd.put("var7", 1+"");
				vpd.put("var8", 200+"");

				String status = varOList.get(i).get("STATUS").toString();
				String companyStatus = "";
				if ("0".equals(status)){
					companyStatus = "待入驻";
				}else if ("1".equals(status)){
					companyStatus = "已入驻";
				}else if ("2".equals(status)){
					companyStatus = "拒绝入驻";
				}else{
					companyStatus = "已退场";
				}
				vpd.put("var9", companyStatus);
				vpd.put("var10", varOList.get(i).getString("COMPANY_CERTIFY"));
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