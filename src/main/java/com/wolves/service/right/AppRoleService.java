package com.wolves.service.right;

import com.wolves.dao.DaoSupport;
import com.wolves.dto.ResourceDTO;
import com.wolves.dto.resource.AppResourceDTO;
import com.wolves.dto.right.AddRoleDTO;
import com.wolves.dto.right.DeleteRoleDTO;
import com.wolves.dto.right.RoleDTO;
import com.wolves.dto.right.UserRoleDTO;
import com.wolves.dto.role.UpdateRoleDTO;
import com.wolves.entity.system.Role;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service("appRoleService")
public class AppRoleService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Autowired
    private AppUserRoleService appUserRoleService;

    /**
     * 根据所有资源
     * @return
     */
    public List<AppResourceDTO> getResources(){
        PageData pd = new PageData();
        return (List<AppResourceDTO>)dao.findForList("AppResourceMapper.getResources", pd);
    }

    /**
     * 根据企业id获取所有角色
     * @param companyId
     * @return
     */
    public List<RoleDTO> getRolesByCompanyId(String companyId){
        PageData pd = new PageData();
        pd.put("companyId", companyId);
        return (List<RoleDTO>)dao.findForList("AppRoleMapper.findByCompanyId", pd);
    }

    /**
     * 根据角色id获取所有资源权限
     * @return
     */
    public List<ResourceDTO> getResourcesByRoleId(Integer roleId){
        PageData pd = new PageData();
        pd.put("roleId", roleId);
        return (List<ResourceDTO>)dao.findForList("AppRoleMapper.getResourcesByRoleId", pd);
    }

    /**
     * 修改角色的权限
     * @param updateRoleDTO
     */
    @Transactional
    public void updateRoleResource(UpdateRoleDTO updateRoleDTO) throws RuntimeException{
        Integer roleId = updateRoleDTO.getRoleId();
        if(null == roleId){
            throw new RuntimeException("roleId不能为空");
        }
        String roleName = updateRoleDTO.getRoleName();
        if(StringUtils.isEmpty(roleName)){
            throw new RuntimeException("角色名称不能为空");
        }
        PageData rolePd = new PageData();
        rolePd.put("id", roleId);
        rolePd.put("roleName", roleName);
        dao.update("AppRoleMapper.updateById", rolePd);

        List<Integer> resourceIds = updateRoleDTO.getResourceList();
        //删除角色所有的权限
        dao.delete("AppRoleResourceMapper.deleteByRoleId", rolePd);

        PageData resourcePd;
        for(Integer item : resourceIds){
            resourcePd = new PageData();
            resourcePd.put("roleId", roleId);
            resourcePd.put("resourceId", item);
            //插入关联角色权限关联关系
            dao.save("AppRoleResourceMapper.save", resourcePd);
        }
    }

    /**
     * 添加角色和资源权限
     * @param addRoleDTO
     * @throws RuntimeException
     */
    @Transactional
    public void addRoleAndRight(AddRoleDTO addRoleDTO, String companyId) throws RuntimeException {
        String roleName = addRoleDTO.getRoleName();
        if(StringUtils.isEmpty(roleName)){
            throw new RuntimeException("角色名称不能为空");
        }
        PageData rolePd = new PageData();
        rolePd.put("roleName", roleName);
        rolePd.put("companyId", companyId);
        dao.save("AppRoleMapper.save", rolePd);

        List<Integer> resourceList = addRoleDTO.getResourceId();
        PageData resourcePd = new PageData();
        resourcePd.put("roleId", rolePd.get("id"));
        for(Integer item : resourceList){
            //添加角色-资源权限关联关系
            resourcePd.put("resourceId",item);
            resourcePd.put("status", 1);
            dao.save("AppRoleResourceMapper.save", resourcePd);
        }

    }

    /**
     * 删除角色和资源权限
     * @param deleteRoleDTO
     * @throws RuntimeException
     */
    @Transactional
    public void deleteRoleAndResourceAndUser(DeleteRoleDTO deleteRoleDTO) throws RuntimeException {
        PageData rolePd = new PageData();
        rolePd.put("id", deleteRoleDTO.getRoleId());
        dao.save("AppRoleMapper.deleteRole", rolePd);

        PageData roleResourcePd = new PageData();
        roleResourcePd.put("id", deleteRoleDTO.getRoleId());
        dao.save("AppRoleResourceMapper.deleteByRoleId", roleResourcePd);

        PageData roleUserPd = new PageData();
        roleUserPd.put("roleId", deleteRoleDTO.getRoleId());
        dao.delete("AppUserRoleMapper.deleteRole", roleUserPd);
    }

    /**
     * 给角色添加用户
     * @param userRole
     */
    public void addUserToRole(UserRoleDTO userRole){
        appUserRoleService.addRoleToUser(userRole.getUserId(), userRole.getRoleId());
    }

    /**
     * 删除角色中的用户
     * @param userRole
     */
    public void deleteUserInRole(UserRoleDTO userRole){
        appUserRoleService.deleteUserInRole(userRole.getUserId(), userRole.getRoleId());
    }

    /**
     *
     * @param roleName roleName
     * @param companyId companyId
     */
    public PageData getRoleByNameAndCompanyId(String roleName, String companyId){
        PageData pd = new PageData();
        pd.put("roleName", roleName);
        pd.put("companyId", companyId);
        return (PageData)dao.findForObject("AppRoleMapper.getRoleByNameAndCompanyId", pd);
    }

    /**
     *
     * @param roleId roleId
     */
    public PageData getRoleById(String roleId){
        PageData pd = new PageData();
        pd.put("id", roleId);
        return (PageData) dao.findForObject("AppRoleMapper.getRoleById", pd);
    }
}