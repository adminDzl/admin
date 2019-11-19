package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.wolves.common.LicensePlateEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dto.*;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.appuser.UserCarBindService;
import com.wolves.service.system.parking.ParkingService;
import com.wolves.service.system.user.UserService;
import com.wolves.service.system.usercarmonthcard.UserCarMonthCardService;
import com.wolves.service.system.yunweiapi.YunweiapiService;
import com.wolves.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * car的控制层
 * @author Administrator
 * @date 2019/3/21
 */
@RestController
@RequestMapping(value = "/app/user")
@Api(tags="CarController",description="car的控制层")
public class CarController {
    protected Logger logger = Logger.getLogger(this.getClass());

    @Resource(name="userService")
    private UserService userService;
    @Resource(name="usercarbindService")
    private UserCarBindService usercarbindService;
    @Resource(name="parkingService")
    private ParkingService parkingService;
    @Resource(name="usercarmonthcardService")
    private UserCarMonthCardService usercarmonthcardService;
    @Resource(name="yunweiapiService")
    private YunweiapiService yunweiapiService;
    /**
     * 查询车牌简称
     */
    @ApiOperation(httpMethod="POST",value="查询车牌简称",notes="查询车牌简称")
    @RequestMapping(value = "/getLicensePlate", method = RequestMethod.POST)
    public Result<Map<String, Object>> getLicensePlate(){
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> map = LicensePlateEnum.queryAll();

        result.setData(map);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 查询我的购买车牌月卡记录
     */
    @ApiOperation(httpMethod="POST",value="查询我的购买车牌月卡记录",notes="查询我的购买车牌月卡记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/myCar", method = RequestMethod.POST)
    public Result<List<UserCarsDTO>> myCar(@RequestHeader("Authorization") String token){
        Result<List<UserCarsDTO>> result = new Result<List<UserCarsDTO>>();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        result.setData(usercarmonthcardService.selectUserCarByUserId(user.getUserId()));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 购买我的车辆月卡
     */
    @ApiOperation(httpMethod="POST",value="绑定车牌",notes="绑定车牌")
    @RequestMapping(value = "/bingCar", method = RequestMethod.POST)
    public Result bingCar(@RequestHeader("Authorization") String token, @RequestBody UserCarDTO userCarDTO){
        Result result = new Result();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String plate = userCarDTO.getPlate();
        if (StringUtils.isEmpty(plate.trim())){
            result.setMsg("请填写你的车牌号");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //查询绑定车牌信息
        UserCarMonthCardDTO userCarMonthCardDTO = usercarmonthcardService.selectCarByCarNo(plate);
        if (userCarMonthCardDTO != null){
            result.setMsg("该车牌已经购买了月卡");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //存入数据
        usercarmonthcardService.createUserCar(user, userCarDTO);
        //运维接口入口
        yunweiapiService.createUserCar(user,userCarDTO);
        

        //返回结果
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("提交成功");
        return result;
    }

    /**
     * 续费，停车卡记录
     */
    @ApiOperation(httpMethod="POST",value="续费停车月卡",notes="续费停车月卡")
    @RequestMapping(value = "/RenewalCar", method = RequestMethod.POST)
    public Result RenewalCar(@RequestHeader("Authorization") String token, @RequestBody UserCarRenewalDTO userCarRenewalDTO){
        Result result = new Result();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
       String id = userCarRenewalDTO.getUserCarMonthCardId();
        if (StringUtils.isEmpty(id)){
            result.setMsg("请选择你续费的月卡");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //修改
        
      //运维接口入口[先传接口，需要读未修改前的tildate
        UserCarMonthCardDTO userCarMonthCardDTO = usercarmonthcardService.selectUserCarById(userCarRenewalDTO.getUserCarMonthCardId());
        if (userCarMonthCardDTO == null){
            result.setMsg("该月卡信息不存在");
            result.setResult(ResultCode.FAIL);
            return result;
        }//判断是否存在月卡
        yunweiapiService.renewCar(user, userCarRenewalDTO);
        
        Integer status = usercarmonthcardService.updateCarDateById(user, userCarRenewalDTO);
        if (status == 0){
            result.setMsg("该月卡信息不存在");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        
        

        //返回结果
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("提交成功");
        return result;
    }

    /**
     * 查看历史停车记录
     */
    @ApiOperation(httpMethod="POST",value="查看历史停车记录",notes="查看历史停车记录")
    @RequestMapping(value = "/myParkHistory", method = RequestMethod.POST)
    public Result<List<UserParkingDTO>> myParkHistory(@RequestHeader("Authorization") String token,
                                @RequestBody PageDataDTO pageDataDTO){
        Result<List<UserParkingDTO>> result = new Result<List<UserParkingDTO>>();
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
        }else {
            if (userCarBindDTO.getStatus().equals(StatusEnum.INIT.getKey()) ||
                    userCarBindDTO.getStatus().equals(StatusEnum.REJECT.getKey())){
                result.setMsg("待审核绑定车辆信息");
                result.setResult(ResultCode.FAIL);
                return result;
            }
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
