package com.wolves.controller.base;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wolves.service.system.appuser.AppUserService;
import com.wolves.util.AppUtil;
import com.wolves.util.PageData;
import com.wolves.util.Tools;

/**
  * 会员-接口类 
  *    
  * 相关参数协议：
  * 00	请求失败
  * 01	请求成功
  * 02	返回空值
  * 03	请求协议参数不完整    
  * 04  用户名或密码错误
  * 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/appuser")
public class IntAppuserController extends BaseController {
    
	@Resource(name="appUserService")
	private AppUserService appUserService;
	
	/**
	 * 根据用户名获取会员信息
	 */
	@RequestMapping(value="/getAppuserByUm")
	@ResponseBody
	public Object getAppuserByUsernmae(){
		logBefore(logger, "根据用户名获取会员信息");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = this.getPageData();
		String result = "00";
		
		try{
			if(Tools.checkKey("USERNAME", pd.getString("FKEY"))){
				if(AppUtil.checkParam("getAppuserByUsernmae", pd)){
					pd = appUserService.findByUId(pd);
					
					map.put("pd", pd);
					result = (null == pd) ?  "02" :  "01";
					
				}else {
					result = "03";
				}
			}else{
				result = "05";
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
}
	
 