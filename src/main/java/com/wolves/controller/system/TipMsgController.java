package com.wolves.controller.system;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.email.EmailService;
import com.wolves.service.system.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.MailModel;
import com.wolves.entity.system.Page;
import com.wolves.util.AppUtil;
import com.wolves.util.ObjectExcelView;
import com.wolves.util.Const;
import com.wolves.util.PageData;
import com.wolves.util.SmsUtil;
import com.wolves.util.Tools;
import com.wolves.util.Jurisdiction;
import com.wolves.service.system.tipmsg.TipMsgService;

/**
 * @author gf
 */
@Controller
@RequestMapping(value="/tipmsg")
public class TipMsgController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "tipmsg/list.do";
	@Resource(name="tipmsgService")
	private TipMsgService tipmsgService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="companyService")
	private CompanyService companyService;
	@Resource(name="emailService")
	private EmailService emailService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增TipMsg");
		//校验权限
		System.out.println("--开始新增站内信");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
		String companyId = pd.getString("TO_USER");
		if("ALL".equals(companyId)){
			companyId = null;
		}
		List<com.wolves.entity.app.User> users = null;
		try{
			users = userService.getUserByCompanyId(companyId);
		} catch (Exception e){
			e.printStackTrace();
		}
		if(!CollectionUtils.isEmpty(users)){
			for(com.wolves.entity.app.User user : users){
				pd.put("TIPMSG_ID", this.get32UUID());
				pd.put("TO_USER", user.getUserId());
				tipmsgService.save(pd);
				String tp_msg=pd.getString("TP_MSG");
				if(tp_msg.equals("1")) {//短信+邮件
					MailModel mail=new MailModel();
					String tomail=user.getEmail();
					mail.setToEmails(tomail);
					mail.setContent(pd.getString("ALERT_CONTENT"));
					mail.setSubject(pd.getString("ALERT_TITLE"));
					System.out.println(mail.toString());
					if(!tomail.isEmpty()) {
						emailService.sendEmail(mail);
					}
					//短信
					String str=pd.getString("ALERT_TITLE")+":"+pd.getString("ALERT_CONTENT")+"【o-park智慧园区】";
					SmsUtil.sendByJiXinTong(user.getPhone(), str);
					
				}else if(tp_msg.equals("2")) {//短信
					String str=pd.getString("ALERT_TITLE")+":"+pd.getString("ALERT_CONTENT")+"【o-park智慧园区】";
					SmsUtil.sendByJiXinTong(user.getPhone(), str);
				}else if(tp_msg.equals("3")) {//邮件		
					MailModel mail=new MailModel();
					String tomail=user.getEmail();
					mail.setToEmails(tomail);
					mail.setContent(pd.getString("ALERT_CONTENT"));
					mail.setSubject(pd.getString("ALERT_TITLE"));
					System.out.println(mail.toString());
					if(!tomail.isEmpty()) {
						emailService.sendEmail(mail);
					}
				}
				System.out.println("pd.ALERT_CONTENT:"+pd.getString("ALERT_CONTENT"));
				System.out.println("pd.TP_MSG:"+pd.getString("TP_MSG"));
				System.out.println("--完成APP站内信推送");
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
		logBefore(logger, "删除TipMsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = this.getPageData();
		tipmsgService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改TipMsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		tipmsgService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表TipMsg");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = tipmsgService.list(page);
		mv.setViewName("system/tipmsg/tipmsg_list");
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
		logBefore(logger, "去新增TipMsg页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/tipmsg/tipmsg_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("userList", userService.selectAllUser());
		mv.addObject("companys", companyService.selectAllCompany());
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改TipMsg页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = tipmsgService.findById(pd);
		mv.setViewName("system/tipmsg/tipmsg_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("userList", userService.selectAllUser());
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除TipMsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		PageData pd = null;
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			tipmsgService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出TipMsg到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("1-公告消息，2-进度提醒");
			titles.add("通知人群");
			titles.add("提醒标题");
			titles.add("提醒内容");
			titles.add("创建时间");
			titles.add("更新时间");
			dataMap.put("titles", titles);
			List<PageData> varOList = tipmsgService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).get("MSG_TYPE").toString());
				vpd.put("var2", varOList.get(i).getString("TO_USER"));
				vpd.put("var3", varOList.get(i).getString("ALERT_TITLE"));
				vpd.put("var4", varOList.get(i).getString("ALERT_CONTENT"));
				vpd.put("var5", varOList.get(i).getString("CREATE_TIME"));
				vpd.put("var6", varOList.get(i).getString("UPDATE_TIME"));
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

	@RequestMapping(value="/getMes")
	public ModelAndView getMes(){
		logBefore(logger, "去head页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

		List<PageData> list = tipmsgService.getMsgByUser(pd);

		mv.setViewName("system/admin/head");
		mv.addObject("varList", list);
		mv.addObject("pd", pd);
		return mv;
	}
}