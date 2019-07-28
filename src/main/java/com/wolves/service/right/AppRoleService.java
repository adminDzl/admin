package com.wolves.service.right;

import com.wolves.dao.DaoSupport;
import com.wolves.dto.ResourceDTO;
import com.wolves.dto.resource.AppResourceDTO;
import com.wolves.dto.right.AddRoleDTO;
import com.wolves.dto.right.RoleDTO;
import com.wolves.dto.role.UpdateRoleDTO;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("appRoleService")
public class AppRoleService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

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
        return (List<RoleDTO>)dao.findForList("AppRoleMapper.getRolesByCompanyId", pd);
    }

    /**
     * 根据企业id和角色id获取所有资源权限
     * @param companyId
     * @return
     */
    public List<ResourceDTO> getRolesByCompanyIdAndRoleId(String companyId, Integer roleId){
        PageData pd = new PageData();
        pd.put("companyId", companyId);
        pd.put("roleId", roleId);
        return (List<ResourceDTO>)dao.findForList("AppRoleMapper.getRolesByCompanyIdAndRoleId", pd);
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
        rolePd.put("roleId", roleId);
        rolePd.put("roleName", roleName);
        dao.update("", rolePd);

        List<ResourceDTO> resourceDTOList = updateRoleDTO.getResourceList();
        //删除角色所有的权限
        dao.delete("", rolePd);
        for(ResourceDTO resourceDTO : resourceDTOList){
            if(1 == resourceDTO.getHasResource()){
                //插入关联角色权限关联关系
                dao.save("", "");
            }
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

        List<ResourceDTO> resourceDTOList = addRoleDTO.getResourceDTOList();
        PageData resourcePd = new PageData();
        resourcePd.put("roleId", rolePd.get("id"));
        for(ResourceDTO resourceDTO : resourceDTOList){
            if(1 == resourceDTO.getHasResource()){
                //添加角色-资源权限关联关系
                resourcePd.put("resourceId", resourceDTO.getResourceId());
                resourcePd.put("status", 1);
                dao.save("AppRoleResourceMapper.save", resourcePd);
            }
        }

    }

}
