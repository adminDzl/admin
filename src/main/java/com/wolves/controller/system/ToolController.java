package com.wolves.controller.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wolves.controller.base.BaseController;
import com.wolves.util.AppUtil;
import com.wolves.util.PageData;

/**
 * @author gf
 */
@Controller
@RequestMapping(value="/tool")
public class ToolController extends BaseController {

	/**
	 * 去接口测试页面
	 */
	@RequestMapping(value="/interfaceTest")
	public ModelAndView editEmail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/tools/interfaceTest");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 *	接口内部请求
	 */
	@RequestMapping(value="/severTest")
	@ResponseBody
	public Object severTest(){
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = this.getPageData();
		String errInfo = "success",str = "",rTime="";
		try{
			//请求起始时间_毫秒
			long startTime = System.currentTimeMillis();
			URL url = new URL(pd.getString("serverUrl"));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//请求类型  POST or GET
			connection.setRequestMethod(pd.getString("requestMethod"));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			//请求结束时间_毫秒
			long endTime = System.currentTimeMillis();
			String temp = "";
			while((temp = in.readLine()) != null){ 
				str = str + temp;
			}
			rTime = String.valueOf(endTime - startTime); 
		}
		catch(Exception e){
			errInfo = "error";
		}
		//状态信息
		map.put("errInfo", errInfo);
		//返回结果
		map.put("result", str);
		//服务器请求时间 毫秒
		map.put("rTime", rTime);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 发送邮件页面
	 */
	@RequestMapping(value="/goSendEmail")
	public ModelAndView goSendEmail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/tools/email");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 多级别树页面
	 */
	@RequestMapping(value="/ztree")
	public ModelAndView ztree() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/tools/ztree");
		mv.addObject("pd", pd);
		return mv;
	}
}