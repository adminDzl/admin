package com.wolves.service.right;

import com.alibaba.fastjson.JSONObject;
import com.wolves.dto.right.RightDTO;
import com.wolves.framework.common.Constant;
import com.wolves.service.system.appuser.AppUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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
    public JSONObject existCompanyAndStaffRight(String userId, String companyId){
        List<RightDTO> rightDTOS = appUserService.selectRightByUserIdAndCompanyId(userId, companyId);
        JSONObject obj = new JSONObject();
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
        if(companyFlag){
            obj.put(Constant.editCompanyRight, true);
        } else {
            obj.put(Constant.editCompanyRight, false);
        }
        if(staffFlag){
            obj.put(Constant.verifyStafyRight, true);
        } else {
            obj.put(Constant.verifyStafyRight, true);
        }
        return obj;
    }


}
