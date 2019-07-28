package com.wolves.service.right;

import com.itextpdf.text.ExceptionConverter;
import com.wolves.dto.CompanyOrStaffRightDTO;
import com.wolves.dto.ResourceDTO;
import com.wolves.dto.resource.AppResourceDTO;
import com.wolves.dto.right.AddRoleDTO;
import com.wolves.dto.right.RightDTO;
import com.wolves.framework.common.Constant;
import com.wolves.service.system.appuser.AppUserService;
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

    /**
     * 判断用户是否能上传企业logo和人员权限
     * @param userId
     * @param companyId
     * @return
     */
    public List<CompanyOrStaffRightDTO> existCompanyAndStaffRight(String userId, String companyId){
        List<RightDTO> rightDTOS = appUserService.selectRightByUserIdAndCompanyId(userId, companyId);
        boolean companyFlag = false;
        boolean staffFlag = false;
        for(RightDTO rightDTO : rightDTOS){
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

        AddRoleDTO addRoleDTO = new AddRoleDTO();
        addRoleDTO.setRoleName("admin");
        List<ResourceDTO> resourceDTOList = new ArrayList<ResourceDTO>();
        ResourceDTO resourceDTO;
        for(AppResourceDTO item : list){
            resourceDTO = new ResourceDTO();
            resourceDTO.setResourceId(item.getId());
            resourceDTO.setResourceName(item.getResourceName());
            resourceDTO.setHasResource(1);
            resourceDTOList.add(resourceDTO);
        }
        addRoleDTO.setResourceDTOList(resourceDTOList);
        //插入admin角色
        appRoleService.addRoleAndRight(addRoleDTO, companyId);
    }


}
