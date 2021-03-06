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
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.email.EmailService;

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
import com.wolves.dto.YunweiEmail;
import com.wolves.entity.system.MailModel;
import com.wolves.entity.system.Page;
import com.wolves.entity.system.User;
import com.wolves.util.AppUtil;
import com.wolves.util.ObjectExcelView;
import com.wolves.util.Const;
import com.wolves.util.PageData;
import com.wolves.util.SmsUtil;
import com.wolves.util.Tools;
import com.wolves.util.Jurisdiction;
import com.wolves.service.system.payment.PaymentService;
import com.wolves.service.system.yunweiapi.YunweiapiService;

/**
 * @author gf
 */
@Controller
@RequestMapping(value="/payment")
public class PaymentController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "payment/list.do";
	@Resource(name="paymentService")
	private PaymentService paymentService;	
	
	@Resource(name="emailService")
	private EmailService emailService;

	@Resource(name="companyService")
	private CompanyService companyService;
	
	@Resource(name="yunweiapiService")
	private YunweiapiService yunweiapiService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Payment");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("PAYMENT_ID", this.get32UUID());
        pd.put("STATUS", StatusEnum.INIT.getKey());
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
		paymentService.save(pd);
		//发送邮件或者短信
		String company_id=pd.getString("COMPANY_ID");
		List<YunweiEmail> emails=yunweiapiService.getPhoneByCompanyId(company_id);
		System.out.println("email:"+emails.toString());
		if(pd.getString("TP_MSG").equals("1")) {//短信+邮件
			for(YunweiEmail email:emails) {
				//逐个发邮件级短信
				MailModel mail=new MailModel();
				String tomail=emails.get(emails.size()-1).getEmail();
				mail.setToEmails(tomail);
				//
				mail.setSubject("缴费提醒");
				mail.setContent("您公司有未缴纳的费用需要结清，请点击APP【缴纳费用】进行支付");
				
				if(!tomail.isEmpty()) {
					emailService.sendEmail(mail);
					//短信内容
					String str="您公司有未缴纳的费用需要结清，请点击APP[缴纳费用]进行支付【o-park智慧园区】";
					SmsUtil.sendByJiXinTong(emails.get(emails.size()-1).getPhone(), str);
				}
			}
		}else if(pd.getString("TP_MSG").equals("2")) {//短信
			for(YunweiEmail email:emails) {
			if(emails.get(emails.size()-1).getPhone()==null) {
			String str="您公司有未缴纳的费用需要结清，请点击APP[缴纳费用]进行支付【o-park智慧园区】";
			SmsUtil.sendByJiXinTong(emails.get(emails.size()-1).getPhone(), str);
			}		
			}
		}else if(pd.getString("TP_MSG").equals("3")) {//邮件		
			for(YunweiEmail email:emails) {
			MailModel mail=new MailModel();
			String tomail=emails.get(emails.size()-1).getEmail();
			mail.setToEmails(tomail);
			mail.setSubject("缴费提醒");
			mail.setContent("您公司有未缴纳的费用需要结清，请点击APP【缴纳费用】进行支付");
			if(!tomail.isEmpty()) {
				emailService.sendEmail(mail);
			}
		}
		}		
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Payment");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = this.getPageData();
		paymentService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Payment");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		paymentService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Payment");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = paymentService.list(page);
		mv.setViewName("system/payment/payment_list");
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
		logBefore(logger, "去新增Payment页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/payment/payment_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("company", companyService.selectAllCompany());
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Payment页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = paymentService.findById(pd);
		mv.setViewName("system/payment/payment_edit");
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
		logBefore(logger, "批量删除Payment");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		PageData pd = null;
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			paymentService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Payment到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("缴费单位");
			titles.add("备注");
			titles.add("缴费金额");
			titles.add("费用月度");
			titles.add("状态");
			titles.add("创建时间");
			dataMap.put("titles", titles);
			List<PageData> varOList = paymentService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("COMPANY_NAME"));
				vpd.put("var2", varOList.get(i).get("PAYMENT_TYPE").toString());
				vpd.put("var3", varOList.get(i).getString("AMOUNT"));
				vpd.put("var4", varOList.get(i).getString("PAYMENT_DATE"));
				String status = varOList.get(i).get("STATUS").toString();
				String state = "待支付";
				if (status.equals("1")){
					state = "支付完成";
 				}else if (status.equals("2")){
					state = "取消支付";
				}
				vpd.put("var5", state);
				vpd.put("var6", varOList.get(i).getString("CREATE_TIME"));
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