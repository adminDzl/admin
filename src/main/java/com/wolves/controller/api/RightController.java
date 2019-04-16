package com.wolves.controller.api;

import com.wolves.dto.right.CompanyRightDTO;
import com.wolves.dto.right.RoleDTO;
import com.wolves.dto.right.UserRightDTO;
import com.wolves.dto.user.CompanyDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.right.AppRoleService;
import com.wolves.service.right.RightService;
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.appuser.AppUserService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * app权限模块的控制层
 * @author Administrator
 * @date 2019/3/21
 */
@RestController
@RequestMapping(value = "/app/right")
@Api(tags="RightController",description="app权限模块的控制层")
public class RightController {

    protected Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    RightService rightService;
    @Autowired
    UserService userService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    AppRoleService appRoleService;
    @Autowired
    CompanyService companyService;

    /**
     * 获取用户对应公司的所有角色及角色对应的所有用户
     */
    @ApiOperation(httpMethod="GET",value="获取用户对应公司的所有角色",notes="获取用户对应公司的所有角色")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Result<CompanyRightDTO> getRolesByToken(@RequestHeader("Authorization") String token){
        Result result = new Result();
        //查找当前用户所在公司
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
        if(null == companyDTO){
            result.setMsg("尚未加入任何公司");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        List<RoleDTO> roleDTOList = appRoleService.getRolesByCompanyId(companyDTO.getCompanyId());
        if(CollectionUtils.isEmpty(roleDTOList)){
            roleDTOList = new ArrayList<RoleDTO>();
        }
        for(RoleDTO roleDTO : roleDTOList){
            List<UserRightDTO> userRightDTOList = appUserService.findByAppRoleId(roleDTO.getRoleId());
            roleDTO.setUserRightDTOList(userRightDTOList);
        }
        CompanyRightDTO companyRightDTO = new CompanyRightDTO();
        companyRightDTO.setCompanyId(companyDTO.getCompanyId());
        companyRightDTO.setLogUrl(companyDTO.getLogo());
        companyRightDTO.setRoleDTOList(roleDTOList);

        result.setResult(ResultCode.SUCCESS);
        result.setMsg("success");
        result.setData(companyRightDTO);
        return result;
    }
    /**
     * 根据角色id
     */
    @ApiOperation(httpMethod="GET",value="获取app用户的所有权限",notes="获取app用户的所有权限")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getRightsByToken(@RequestHeader("Authorization") String token){
        Result result = new Result();
        //查找当前用户所在公司
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
        if(null == companyDTO){
            result.setMsg("尚未加入任何公司");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //返回当前用户在所在公司的所有app权限
        return null;
    }

    /**
     * 修改app用户的权限
     */
    @ApiOperation(httpMethod="POST",value="修改app用户的权限",notes="修改app用户的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader("Authorization") String token){


        return null;
    }

    /**
     * 获取用户是否有企业编辑权限和人员权限
     */
    @ApiOperation(httpMethod="GET",value="获取用户是否有企业编辑权限和人员权限",notes="获取用户是否有企业编辑权限和人员权限")
    @RequestMapping(value = "/hasRight", method = RequestMethod.GET)
    public Result hasRight(@RequestHeader("Authorization") String token){
        Result result = new Result();
        //查找当前用户所在公司
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
        if(null == companyDTO){
            result.setMsg("尚未加入任何公司");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //根据企业id，userId获取所有权限
        //判断用户是否有企业编辑权限和人员权限
        return null;
    }
}
