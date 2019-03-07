package com.wolves.controller.base;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.wolves.entity.system.Page;
import com.wolves.util.Logger;
import com.wolves.util.PageData;
import com.wolves.util.UuidUtil;

public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public String get32UUID(){
		return UuidUtil.get32UUID();
	}

	public Page getPage(){
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
}
