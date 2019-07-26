package com.wolves.service.right;

import com.alibaba.fastjson.JSONObject;
import com.wolves.dto.CompanyOrStaffRightDTO;
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

    @Resource(name="appUserService")
    AppUserService appUserService;


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


}
