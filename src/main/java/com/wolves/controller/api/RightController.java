package com.wolves.controller.api;

import com.wolves.dto.CompanyOrStaffRightDTO;
import com.wolves.dto.ResourceDTO;
import com.wolves.dto.resource.AppResourceDTO;
import com.wolves.dto.right.*;
import com.wolves.dto.role.UpdateRoleDTO;
import com.wolves.dto.user.BaseCompanyDTO;
import com.wolves.dto.user.CheckUserDTO;
import com.wolves.dto.user.CompanyDTO;
import com.wolves.dto.user.ManagerUserDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.right.AppRoleService;
import com.wolves.service.right.AppUserRoleService;
import com.wolves.service.right.RightService;
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.appuser.AppUserService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.Logger;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
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
@Api(tags="RightController",description="app权限模块")
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
    @Autowired
    AppUserRoleService appUserRoleService;

    /**
     * 获取用户是否有企业编辑权限和人员权限
     */
    @ApiOperation(httpMethod="GET",value="获取用户是否有企业编辑权限和人员权限")
    @RequestMapping(value = "/hasCompanyAndStaffRight", method = RequestMethod.GET)
    public Result hasRight(@RequestHeader("Authorization") String token){
        Result result = new Result();
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
        //判断用户是否有企业编辑权限和人员权限
        List<CompanyOrStaffRightDTO> list = rightService.existCompanyAndStaffRight(user.getUserId());
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        result.setData(list);
        return result;
    }

    /**
     * 获取用户是否有数据报表权限
     */
    @ApiOperation(httpMethod="GET",value="获取用户是否有数据报表权限")
    @RequestMapping(value = "/hasDataRight", method = RequestMethod.GET)
    public Result hasDataRight(@RequestHeader("Authorization") String token){
        Result result = new Result();
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
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        result.setData(user.getHasDateRight());
        return result;
    }

    /**
     * 获取公司的权限资源
     */
    @ApiOperation(httpMethod="GET",value="获取公司的权限资源")
    @RequestMapping(value = "/getCompanyResource", method = RequestMethod.GET)
    public Result getCompanyResource(@RequestHeader("Authorization") String token){
        Result result = new Result();
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

        List<AppResourceDTO> list = appRoleService.getResources();
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        result.setData(list);
        return result;
    }

    /**
     * 添加app角色
     */
    @ApiOperation(httpMethod="POST", value="添加app角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public Result addRole(@RequestHeader("Authorization") String token,
                          @RequestBody AddRoleDTO addRole){
        Result result = new Result();
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
        //判断该员工是否有添加角色的权限
        //todo。。。
        //添加角色
        try{
            appRoleService.addRoleAndRight(addRole, companyDTO.getCompanyId());
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg("角色已存在");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 删除app角色
     */
    @ApiOperation(httpMethod="POST", value="删除app角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public Result addRole(@RequestHeader("Authorization") String token,
                          @RequestBody DeleteRoleDTO deleteRoleDTO){
        Result result = new Result();
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
        //判断该员工是否有删除角色的权限
        //todo。。。
        PageData roleDTO = appRoleService.getRoleById(deleteRoleDTO.getRoleId());
        if("admin".equals(roleDTO.getString("role_name")) || "未分组".equals(roleDTO.getString("role_name"))){
            result.setMsg("该组不能删除");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //添加角色
        try{
            appRoleService.deleteRoleAndResourceAndUser(deleteRoleDTO);
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg("删除失败");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 获取用户对应公司的所有角色及角色对应的所有用户
     */
    @ApiOperation(httpMethod="GET",value="获取用户对应公司的所有角色及角色对应的所有用户")
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
     * 获取角色的权限资源
     */
    @ApiOperation(httpMethod="GET",value="获取角色的权限资源")
    @RequestMapping(value = "/getResourceByRole", method = RequestMethod.GET)
    public Result getResourceByRole(@RequestHeader("Authorization") String token,
                              @RequestParam("roleId") Integer roleId){
        Result result = new Result();
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

        List<ResourceDTO> list = appRoleService.getResourcesByRoleId(roleId);
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        result.setData(list);
        return result;
    }

    /**
     * 修改app角色
     */
    @ApiOperation(httpMethod="POST", value="修改app角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public Result updateRole(@RequestHeader("Authorization") String token,
                             @RequestBody UpdateRoleDTO updateRoleDTO){
        Result result = new Result();
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
        PageData roleDTO = appRoleService.getRoleById(updateRoleDTO.getRoleId().toString());
        if("admin".equals(roleDTO.getString("ROLE_NAME")) || "未分组".equals(roleDTO.getString("ROLE_NAME"))){
            result.setMsg("该组不能删除");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        try{
            appRoleService.updateRoleResource(updateRoleDTO);
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
            result.setResult(ResultCode.FAIL);
            return result;
        }

        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        return result;
    }


    /**
     * 根据角色id
     */
    @ApiOperation(httpMethod="GET",value="获取当前app用户的所有权限")
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
        List<ResourceDTO> list = appUserRoleService.getResourcesById(user.getUserId());
        result.setData(list);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("success");
        return result;
    }

//    @ApiOperation(httpMethod="GET",value="给指定公司添加最高权限的角色")
//    @RequestMapping(value = "/addAdmin", method = RequestMethod.GET)
//    public Result addAdmin(@RequestParam("companyId") String id){
//        rightService.addCompanyAdminRole(id);
//        return null;
//    }

    /**
     * 给角色添加用户
     */
    @ApiOperation(httpMethod="POST", value="给角色添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/addUserToRole", method = RequestMethod.POST)
    public Result addRole(@RequestHeader("Authorization") String token,
                          @RequestBody UserRoleDTO userRole){
        Result result = new Result();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        if(StringUtils.isEmpty(userRole.getUserId())){
            result.setMsg("用户id不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        if(null == userRole.getRoleId()){
            result.setMsg("角色id不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
        if(null == companyDTO){
            result.setMsg("尚未加入任何公司");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        try{
            appRoleService.addUserToRole(userRole);
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg("角色已存在");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 删除角色下的用户
     */
    @ApiOperation(httpMethod="POST", value="删除角色下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/deleteUserInRole", method = RequestMethod.POST)
    public Result deleteUserInRole(@RequestHeader("Authorization") String token,
                          @RequestBody UserRoleDTO userRole){
        Result result = new Result();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        if(StringUtils.isEmpty(userRole.getUserId())){
            result.setMsg("用户id不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        if(null == userRole.getRoleId()){
            result.setMsg("角色id不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
        if(null == companyDTO){
            result.setMsg("尚未加入任何公司");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        appRoleService.deleteUserInRole(userRole);
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 查询审核注册人信息的列表
     * @param token
     * @return
     */
    @ApiOperation(httpMethod="POST", value="查询审核注册人信息的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/selectCheckUserList", method = RequestMethod.POST)
    public Result<List<CheckUserDTO>> selectCheckUserList(@RequestHeader("Authorization") String token){
        Result<List<CheckUserDTO>> result = new Result<List<CheckUserDTO>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //判断该用户是否是负责人
        List<ManagerUserDTO> managerUserDTOs = userService.selectManagerUserByCompanyId(user.getCompanyId());
        Boolean isCheck = false;
        if (managerUserDTOs != null && !managerUserDTOs.isEmpty()){
            for (ManagerUserDTO managerUserDTO : managerUserDTOs){
                if (managerUserDTO.getUserId().equals(user.getUserId())){
                    isCheck = true;
                    break;
                }
            }
        }
        if (isCheck){
            result.setData(userService.selectCheckUser(user.getCompanyId()));
        }
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        return result;
    }

    @ApiOperation(httpMethod="POST", value="审核通过注册人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/selectCheckSuccessUser", method = RequestMethod.POST)
    public Result selectCheckSuccessUser(@RequestHeader("Authorization") String token,
                                       @RequestBody List<CheckUserDTO> checkUserDTOs){
        Result result = new Result();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //判断该用户是否是负责人
        List<ManagerUserDTO> managerUserDTOs = userService.selectManagerUserByCompanyId(user.getCompanyId());
        Boolean isCheck = false;
        if (managerUserDTOs != null && !managerUserDTOs.isEmpty()){
            for (ManagerUserDTO managerUserDTO : managerUserDTOs){
                if (managerUserDTO.getUserId().equals(user.getUserId())){
                    isCheck = true;
                    break;
                }
            }
        }

        if (isCheck){
            //审核
            if (checkUserDTOs != null && !checkUserDTOs.isEmpty()){
                for (CheckUserDTO checkUserDTO : checkUserDTOs){
                    userService.checkUser(checkUserDTO, user.getCompanyId());
                }
            }
        }
        result.setMsg("success");
        result.setResult(ResultCode.SUCCESS);
        return result;
    }

    @ApiOperation(httpMethod="POST", value="test")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Result test(@RequestHeader("Authorization") String token){
        List<BaseCompanyDTO> list = companyService.selectAllCompany();
        for(BaseCompanyDTO baseCompanyDTO : list){
            AddRoleDTO addRoleDTO = new AddRoleDTO();
            addRoleDTO.setRoleName("未分组");
            addRoleDTO.setResourceId(new ArrayList<Integer>());
            appRoleService.addRoleAndRight(addRoleDTO, baseCompanyDTO.getCompanyId());
        }
      return null;
    }

}
