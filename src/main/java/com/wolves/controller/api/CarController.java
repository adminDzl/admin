package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.wolves.common.LicensePlateEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dto.PageDataDTO;
import com.wolves.dto.UserCarBindDTO;
import com.wolves.dto.UserParkingDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.appuser.UserCarBindService;
import com.wolves.service.system.parking.ParkingService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
import com.wolves.util.UuidUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/21.
 */
@RestController
@RequestMapping(value = "/app/user")
public class CarController {

    @Resource(name="userService")
    private UserService userService;
    @Resource(name="usercarbindService")
    private UserCarBindService usercarbindService;
    @Resource(name="parkingService")
    private ParkingService parkingService;

    /**
     * 查询车牌简称
     */
    @RequestMapping(value = "/getLicensePlate", method = RequestMethod.GET)
    public Result getLicensePlate(){
        Result result = new Result();
        Map<String, Object> map = LicensePlateEnum.queryAll();

        result.setData(map);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 我的车辆信息
     */
    @RequestMapping(value = "/myCar", method = RequestMethod.POST)
    public Result myCar(@RequestHeader("Authorization") String token){
        Result result = new Result();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        result.setData(usercarbindService.selectCarBindByUserId(user.getUserId()));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 绑定车牌
     */
    @RequestMapping(value = "/bingCar", method = RequestMethod.POST)
    public Result bingCar(@RequestHeader("Authorization") String token, @RequestBody JSONObject jsonObject){
        Result result = new Result();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String plate = jsonObject.getString("plate");
        if (StringUtils.isEmpty(plate.trim())){
            result.setMsg("请填写你的车牌号");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        //存入数据
        PageData pd = new PageData();
        pd.put("USER_CAR_BIND_ID", UuidUtil.get32UUID());
        pd.put("STATUS", StatusEnum.INIT.getKey());
        pd.put("USER_ID",user.getUserId());
        pd.put("CAR_NO",plate);
        pd.put("REMARK","");
        usercarbindService.save(pd);

        //返回结果
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("提交成功");
        return result;
    }

    /**
     * 查看历史停车记录
     */
    @RequestMapping(value = "/myParkHistory", method = RequestMethod.POST)
    public Result myParkHistory(@RequestHeader("Authorization") String token,
                                @RequestBody PageDataDTO pageDataDTO){
        Result result = new Result();
        Integer page = pageDataDTO.getPage();
        if (page < 0){
            result.setMsg("页数必须大于0");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        Integer size = pageDataDTO.getSize();
        if (size == 0 || size < 0){
            result.setMsg("条数必须大于0");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //判断是否已经绑定车牌
        UserCarBindDTO userCarBindDTO = usercarbindService.selectCarBindByUserId(user.getUserId());
        if (userCarBindDTO == null){
            result.setMsg("请绑定车辆信息");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", user.getUserId());
        params.put("start", (page - 1) * size);
        params.put("size", size);
        List<UserParkingDTO> userParkingDTOs = parkingService.selectParking(params);

        result.setData(userParkingDTOs);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }


}
