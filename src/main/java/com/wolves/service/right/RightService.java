package com.wolves.service.right;

import com.wolves.dto.CompanyOrStaffRightDTO;
import com.wolves.dto.ResourceDTO;
import com.wolves.dto.resource.AppResourceDTO;
import com.wolves.dto.right.AddRoleDTO;
import com.wolves.framework.common.Constant;
import com.wolves.service.system.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xulu
 * app权限模块service
 */
@Service
public class RightService {

    @Resource(name = "appUserService")
    AppUserService appUserService;
    @Resource(name = "appRoleService")
    AppRoleService appRoleService;
    @Autowired
    AppUserRoleService appUserRoleService;

    /**
     * 判断用户是否能上传企业logo和人员权限
     * @param userId
     * @return
     */
    public List<CompanyOrStaffRightDTO> existCompanyAndStaffRight(String userId){
        List<ResourceDTO> rightDTOS = appUserRoleService.getResourcesById(userId);
        boolean companyFlag = false;
        boolean staffFlag = false;
        for(ResourceDTO rightDTO : rightDTOS){
              if(Constant.editCompanyRight.equals(rightDTO.getResourceName())){
                  companyFlag = true;
              }
              if(Constant.verifyStafyRight.equals(rightDTO.getResourceName())){
                  staffFlag = true;
              }
        }
        CompanyOrStaffRightDTO companyDTO = new CompanyOrStaffRightDTO();
        companyDTO.setResourceName(Constant.editCompanyRight);
        if(companyFlag){
            companyDTO.setHasResource(1);
        } else {
            companyDTO.setHasResource(0);
        }
        CompanyOrStaffRightDTO staffDTO = new CompanyOrStaffRightDTO();
        staffDTO.setResourceName(Constant.verifyStafyRight);
        if(staffFlag){
            staffDTO.setHasResource(1);
        } else {
            staffDTO.setHasResource(0);
        }
        List<CompanyOrStaffRightDTO> list = new ArrayList<CompanyOrStaffRightDTO>();
        list.add(companyDTO);
        list.add(staffDTO);
        return list;
    }

    /**
     * 给指定的公司添加最高权限的角色
     * @param companyId
     * @throws RuntimeException 说明该公司已存在admin角色
     */
    public void addCompanyAdminRole(String companyId) throws RuntimeException {
        //获取公司的所有权限
        List<AppResourceDTO> list = appRoleService.getResources();

        AddRoleDTO adminRoleDTO = new AddRoleDTO();
        adminRoleDTO.setRoleName("admin");
        List<Integer> resourceDTOList = new ArrayList();
        for(AppResourceDTO item : list){
            resourceDTOList.add(item.getId());
        }
        adminRoleDTO.setResourceId(resourceDTOList);
        //插入admin角色
        appRoleService.addRoleAndRight(adminRoleDTO, companyId);

        //插入未分组角色
        AddRoleDTO nullRoleDTO = new AddRoleDTO();
        nullRoleDTO.setRoleName("未分组");
        nullRoleDTO.setResourceId(new ArrayList<Integer>());
        appRoleService.addRoleAndRight(nullRoleDTO, companyId);
    }


}
