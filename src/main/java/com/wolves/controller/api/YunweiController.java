package com.wolves.controller.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wolves.dto.YunweiConstruction;
import com.wolves.dto.YunweiConstructionItem;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.user.UserService;
import com.wolves.service.system.yunweiapi.YunweiapiService;
import com.wolves.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/app/user")
@Api(tags="YunweiController",description="运维平台对接API的控制层")
public class YunweiController {
	 @Resource(name="userService")
	    private UserService userService;
	 @Resource(name="yunweiapiService")
	    private YunweiapiService yunweiapiService;
	
	@ApiOperation(httpMethod="POST",value="施工许可",notes="施工许可")
    @RequestMapping(value = "/construction/createConstruction", method = RequestMethod.POST)
	public Result ConstructionApply(@RequestHeader("Authorization") String token,
    @RequestBody YunweiConstruction yunweiConstruction) {
		Result result = new Result();
		 User user = userService.getUser(token);
	        if (user == null){
	            result.setMsg("请登录");
	            result.setResult(ResultCode.FAIL);
	            return result;
	        }
	        String title = yunweiConstruction.getTitle();
	        if (StringUtils.isEmpty(title.trim())){
	            result.setResult(ResultCode.FAIL);
	            result.setMsg("请填写施工标题");
	            return result;
	        }
	        
	   	    String construction_units=yunweiConstruction.getConstruction_units();
	   	    if (StringUtils.isEmpty(construction_units.trim())){
	            result.setResult(ResultCode.FAIL);
	            result.setMsg("请填写施工单位");
	            return result;
	   	    }
	   	    
	      	 String construction_director=yunweiConstruction.getConstruction_director();
		   	    if (StringUtils.isEmpty(construction_director.trim())){
		            result.setResult(ResultCode.FAIL);
		            result.setMsg("请填写施工负责人");
		            return result;
		   	    }
		   	 String contact_way=yunweiConstruction.getContact_way();
		   	    if (StringUtils.isEmpty(contact_way.trim())){
		            result.setResult(ResultCode.FAIL);
		            result.setMsg("请填写施工联系方式");
		            return result;
		   	    }  	    
	   	 String plan_start_time=yunweiConstruction.getPlan_start_time();
	   	    if (StringUtils.isEmpty(plan_start_time.trim())){
	            result.setResult(ResultCode.FAIL);
	            result.setMsg("请填写工作时间-开始");
	            return result;
	   	    }  	    
	   	    
	   	 String plan_end_time=yunweiConstruction.getPlan_end_time();
	   	    if (StringUtils.isEmpty(plan_end_time.trim())){
	            result.setResult(ResultCode.FAIL);
	            result.setMsg("请填写工作时间-结束");
	            return result;
	   	    }  	    
	   	    
	   	 String construction_type=yunweiConstruction.getConstruction_type();
	   	    if (StringUtils.isEmpty(construction_type.trim())){
	            result.setResult(ResultCode.FAIL);
	            result.setMsg("请填写施工分类");
	            return result;
	   	    }  	    
	   	    
	   	 String system_id=yunweiConstruction.getSystem_id();
	   	    if (StringUtils.isEmpty(system_id.trim())){
	            result.setResult(ResultCode.FAIL);
	            result.setMsg("请填写具体类型");
	            return result;
	   	    }  	    
	   	    
	   	 String job_location=yunweiConstruction.getJob_location();
	   	    if (StringUtils.isEmpty(job_location.trim())){
	            result.setResult(ResultCode.FAIL);
	            result.setMsg("请填写工作区域");
	            return result;
	   	    }  	    
	   	    
	   	 String safeguard_procedures=yunweiConstruction.getSafeguard_procedures();
	   	    if (StringUtils.isEmpty(safeguard_procedures.trim())){
	            result.setResult(ResultCode.FAIL);
	            result.setMsg("请填写保护措施");
	            return result;
	   	    }  	  
	   	    
	   	    List<YunweiConstructionItem> yt=yunweiConstruction.getItemList();
	   	    if(yt.isEmpty()) {
	   	     result.setResult(ResultCode.FAIL);
	            result.setMsg("请填写项目");
	            return result;
	   	    }
//	   	    
//	   	 String =yunweiConstruction;
//	   	    if (StringUtils.isEmpty(trim())){
//	            result.setResult(ResultCode.FAIL);
//	            result.setMsg("");
//	            return result;
//	   	    }  	          
	        yunweiapiService.createConstruction(user, yunweiConstruction);
	        result.setResult(ResultCode.SUCCESS);
	        result.setMsg("保存成功");
	        return result;
		
		
		
	}

}
