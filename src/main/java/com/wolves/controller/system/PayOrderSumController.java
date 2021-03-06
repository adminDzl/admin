package com.wolves.controller.system;

import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.Page;
import com.wolves.service.system.payment.PaymentService;
import com.wolves.service.system.payorder.PayOrderService;
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

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author gf
 */
@Controller
@RequestMapping(value="/payordersum")
public class PayOrderSumController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "payordersum/list.do";
	@Resource(name="paymentService")
	private PaymentService paymentService;
	@Resource(name="payorderService")
	private PayOrderService payorderService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表PayOrderSum");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);

		List<PageData> varList = paymentService.selectSummary(page, pd);

		mv.setViewName("system/payorder/payordersum_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());
		return mv;
	}

	/**
	 * 去未缴费公司页面
	 */
	@RequestMapping(value="/goWaitPay")
	public ModelAndView goWaitPay(){
		logBefore(logger, "去新增goWaitPay页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> varList = paymentService.queryNoPayCompany();
		mv.setViewName("system/payorder/waitPaycompany_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 一键催缴
	 */
	@RequestMapping(value="/worth")
	@ResponseBody
	public Object worth(){
		logBefore(logger, "一键催缴worth");
		PageData pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>(10);
		List<PageData> pdList = new ArrayList<PageData>();

		pd = paymentService.worthMsg();

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
		logBefore(logger, "导出payordersum到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("备注");
			titles.add("付费金额");
			titles.add("付费时间");
			titles.add("确认时间");
			titles.add("支付状态");
			titles.add("创建时间");
			titles.add("更新时间");
			dataMap.put("titles", titles);
			List<PageData> varOList = paymentService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).get("PAYMENT_TYPE").toString());
				vpd.put("var2", varOList.get(i).getString("AMOUNT"));
				vpd.put("var3", varOList.get(i).getString("PAYMENT_DATE"));
				vpd.put("var4", varOList.get(i).getString("CREATE_TIME"));
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