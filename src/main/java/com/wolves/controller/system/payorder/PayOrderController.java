package com.wolves.controller.system.payorder;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
import com.wolves.entity.Page;
import com.wolves.util.AppUtil;
import com.wolves.util.ObjectExcelView;
import com.wolves.util.Const;
import com.wolves.util.PageData;
import com.wolves.util.Tools;
import com.wolves.util.Jurisdiction;
import com.wolves.service.system.payorder.PayOrderService;

@Controller
@RequestMapping(value="/payorder")
public class PayOrderController extends BaseController {

	//菜单地址(权限用)
	String menuUrl = "payorder/list.do";
	@Resource(name="payorderService")
	private PayOrderService payorderService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增PayOrder");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("PAYORDER_ID", this.get32UUID());
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
		payorderService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除PayOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = this.getPageData();
		payorderService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改PayOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		payorderService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表PayOrder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = payorderService.list(page);
		mv.setViewName("system/payorder/payorder_list");
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
		logBefore(logger, "去新增PayOrder页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/payorder/payorder_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改PayOrder页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = payorderService.findById(pd);
		mv.setViewName("system/payorder/payorder_edit");
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
		logBefore(logger, "批量删除PayOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		PageData pd = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				payorderService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出PayOrder到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("类型（1-水电物业，2-停车费，3-场地预定费，4-一卡通费用）");
			titles.add("付费金额");
			titles.add("付费时间");
			titles.add("确认时间");
			titles.add("支付状态");
			titles.add("创建时间");
			titles.add("更新时间");
			dataMap.put("titles", titles);
			List<PageData> varOList = payorderService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).get("PAY_TYPE").toString());
				vpd.put("var2", varOList.get(i).getString("PAY_AMOUNT"));
				vpd.put("var3", varOList.get(i).getString("PAY_TIME"));
				vpd.put("var4", varOList.get(i).getString("RETURN_TIME"));
				vpd.put("var5", varOList.get(i).get("PAY_STATUS").toString());
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