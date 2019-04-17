package com.wolves.service.right;

import com.wolves.dao.DaoSupport;
import com.wolves.dto.right.RoleDTO;
import com.wolves.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("appRoleService")
public class AppRoleService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

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
}
