package com.wolves.controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wolves.controller.base.BaseController;
import com.wolves.service.system.appuser.AppUserService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.AppUtil;
import com.wolves.util.Const;
import com.wolves.util.PageData;
import com.wolves.util.SmsUtil;
import com.wolves.util.Tools;

/**
 * @author
 */
@Controller
@RequestMapping(value="/head")
public class HeadController extends BaseController {
	
	@Resource(name="userService")
	private UserService userService;	
	@Resource(name="appUserService")
	private AppUserService appUserService;
	
	/**
	 * 获取头部信息
	 */
	@RequestMapping(value="/getUname")
	@ResponseBody
	public Object getList() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			PageData pds = (PageData)session.getAttribute(Const.SESSION_userpds);
			
			if(null == pds){
				String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();
				pd.put("USERNAME", USERNAME);
				pds = userService.findByUId(pd);
				session.setAttribute(Const.SESSION_userpds, pds);
			}
			
			pdList.add(pds);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 保存皮肤
	 */
	@RequestMapping(value="/setSKIN")
	public void setSKIN(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();
		pd.put("USERNAME", USERNAME);
		userService.setSKIN(pd);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERROL);
		out.write("success");
		out.close();
	}
	

	
	/**
	 * 去系统设置页面
	 */
	@RequestMapping(value="/goSystem")
	public ModelAndView goEditEmail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("YSYNAME", Tools.readTxtFile(Const.SYSNAME));
		pd.put("COUNTPAGE", Tools.readTxtFile(Const.PAGE));
		String strEMAIL = Tools.readTxtFile(Const.EMAIL);
		String strSMS1 = Tools.readTxtFile(Const.SMS1);
		String strSMS2 = Tools.readTxtFile(Const.SMS2);
		String strFWATERM = Tools.readTxtFile(Const.FWATERM);
		String strIWATERM = Tools.readTxtFile(Const.IWATERM);
		pd.put("Token", Tools.readTxtFile(Const.WEIXIN));
		
		if(null != strEMAIL && !"".equals(strEMAIL)){
			String strEM[] = strEMAIL.split(",fh,");
			if(strEM.length == 4){
				pd.put("SMTP", strEM[0]);
				pd.put("PORT", strEM[1]);
				pd.put("EMAIL", strEM[2]);
				pd.put("PAW", strEM[3]);
			}
		}
		
		if(null != strSMS1 && !"".equals(strSMS1)){
			String strS1[] = strSMS1.split(",fh,");
			if(strS1.length == 2){
				pd.put("SMSU1", strS1[0]);
				pd.put("SMSPAW1", strS1[1]);
			}
		}
		
		if(null != strSMS2 && !"".equals(strSMS2)){
			String strS2[] = strSMS2.split(",fh,");
			if(strS2.length == 2){
				pd.put("SMSU2", strS2[0]);
				pd.put("SMSPAW2", strS2[1]);
			}
		}
		
		if(null != strFWATERM && !"".equals(strFWATERM)){
			String strFW[] = strFWATERM.split(",fh,");
			if(strFW.length == 5){
				pd.put("isCheck1", strFW[0]);
				pd.put("fcontent", strFW[1]);
				pd.put("fontSize", strFW[2]);
				pd.put("fontX", strFW[3]);
				pd.put("fontY", strFW[4]);
			}
		}
		
		if(null != strIWATERM && !"".equals(strIWATERM)){
			String strIW[] = strIWATERM.split(",fh,");
			if(strIW.length == 4){
				pd.put("isCheck2", strIW[0]);
				pd.put("imgUrl", strIW[1]);
				pd.put("imgX", strIW[2]);
				pd.put("imgY", strIW[3]);
			}
		}
		
		mv.setViewName("system/head/sys_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 保存系统设置1
	 */
	@RequestMapping(value="/saveSys")
	public ModelAndView saveSys() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Tools.writeFile(Const.SYSNAME,pd.getString("YSYNAME"));
		Tools.writeFile(Const.PAGE,pd.getString("COUNTPAGE"));
		Tools.writeFile(Const.EMAIL,pd.getString("SMTP")+",fh,"+pd.getString("PORT")+",fh,"+pd.getString("EMAIL")+",fh,"+pd.getString("PAW"));
		Tools.writeFile(Const.SMS1,pd.getString("SMSU1")+",fh,"+pd.getString("SMSPAW1"));
		Tools.writeFile(Const.SMS2,pd.getString("SMSU2")+",fh,"+pd.getString("SMSPAW2"));
		mv.addObject("msg","OK");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 保存系统设置2
	 */
	@RequestMapping(value="/saveSys2")
	public ModelAndView saveSys2() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Tools.writeFile(Const.FWATERM,pd.getString("isCheck1")+",fh,"+pd.getString("fcontent")+",fh,"+pd.getString("fontSize")+",fh,"+pd.getString("fontX")+",fh,"+pd.getString("fontY"));
		Tools.writeFile(Const.IWATERM,pd.getString("isCheck2")+",fh,"+pd.getString("imgUrl")+",fh,"+pd.getString("imgX")+",fh,"+pd.getString("imgY"));
		mv.addObject("msg","OK");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 保存系统设置3
	 */
	@RequestMapping(value="/saveSys3")
	public ModelAndView saveSys3() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Tools.writeFile(Const.WEIXIN,pd.getString("Token"));
		mv.addObject("msg","OK");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去代码生成器页面
	 */
	@RequestMapping(value="/goProductCode")
	public ModelAndView goProductCode() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/head/productCode");
		return mv;
	}
	
}
