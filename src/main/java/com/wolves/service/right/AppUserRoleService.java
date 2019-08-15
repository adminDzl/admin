package com.wolves.service.right;

import com.wolves.dao.DaoSupport;
import com.wolves.dto.ResourceDTO;
import com.wolves.dto.resource.AppResourceDTO;
import com.wolves.dto.right.AddRoleDTO;
import com.wolves.dto.right.RoleBasicDTO;
import com.wolves.dto.right.RoleDTO;
import com.wolves.dto.role.UpdateRoleDTO;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("appUserRoleService")
public class AppUserRoleService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Resource(name = "appRoleService")
    private AppRoleService appRoleService;

    /**
     * 获取某人的角色
     */
    public RoleBasicDTO getRoleById(String userId){
        PageData pd = new PageData();
        pd.put("userId", userId);
        return (RoleBasicDTO) dao.findForObject("AppUserRoleMapper.selectByUserId", pd);
    }

    /**
     * 获取某人的资源
     */
    public List<ResourceDTO> getResourcesById(String userId){
        List<ResourceDTO> list = new ArrayList<ResourceDTO>();
        PageData pd = new PageData();
        pd.put("userId", userId);
        RoleBasicDTO roleBasicDTO = (RoleBasicDTO) dao.findForObject("AppUserRoleMapper.selectByUserId", pd);
        Integer roleId = roleBasicDTO.getRoleId();
        if(null != roleId){
            list = appRoleService.getResourcesByRoleId(roleBasicDTO.getRoleId());
        }
        return list;
    }

    /**
     * 给某人设置角色
     * 角色不能有多个
     */
    @Transactional
    public void addRoleToUser(String userId, Integer roleId){
        PageData pd = new PageData();
        pd.put("userId", userId);
        //先删除该用户已有的角色
        dao.delete("AppUserRoleMapper.deleteByUserId", pd);
        //再新增角色
        pd.put("roleId", roleId);
        dao.save("AppUserRoleMapper.save", pd);
    }

    /**
     * 删除某人的角色
     * 角色不能有多个
     */
    @Transactional
    public void deleteUserInRole(String userId, Integer roleId){
        PageData pd = new PageData();
        pd.put("userId", userId);
        pd.put("roleId", roleId);
        dao.delete("AppUserRoleMapper.deleteUserInRole", pd);
    }

}
