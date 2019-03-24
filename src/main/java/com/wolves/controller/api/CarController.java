package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.wolves.common.LicensePlateEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dto.PageDataDTO;
import com.wolves.dto.UserCarBindDTO;
import com.wolves.dto.UserParkingDTO;
import com.wolves.dto.user.UserExcelDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.appuser.UserCarBindService;
import com.wolves.service.system.parking.ParkingService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 查询车牌简称
     */
    @ApiOperation(httpMethod="POST",value="查询车牌简称",notes="查询车牌简称")
    @RequestMapping(value = "/getLicensePlate", method = RequestMethod.GET)
    public Result<Map<String, Object>> getLicensePlate(){
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> map = LicensePlateEnum.queryAll();

        result.setData(map);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 我的车辆信息
     */
    @ApiOperation(httpMethod="POST",value="我的车辆信息",notes="我的车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/myCar", method = RequestMethod.POST)
    public Result<UserCarBindDTO> myCar(@RequestHeader("Authorization") String token){
        Result<UserCarBindDTO> result = new Result<UserCarBindDTO>();
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
    @ApiOperation(httpMethod="POST",value="绑定车牌",notes="绑定车牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"plate\":\"车牌号\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
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
        //查询绑定车牌信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", user.getUserId());
        params.put("status", Integer.valueOf(StatusEnum.INIT.getKey()));
        List<UserCarBindDTO> userCarBindDTOs = usercarbindService.selectMyCar(params);
        if (userCarBindDTOs != null && !userCarBindDTOs.isEmpty()){
            result.setMsg("已经提交审核，请耐心等待");
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

//    @ApiOperation(httpMethod="POST",value="导入",notes="导入")
//    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
//    public void importExcel(@RequestParam(value="uploadFile") MultipartFile file){
//        logger.info("客户数据excel导入-->/importExcel");
//        ImportExcelUtil importExcelUtil = new ImportExcelUtil();
//        List<Map<String, Object>> userList = importExcelUtil.getExcelInfo(file);
//        if (userList != null && !userList.isEmpty() && userList.size() < 50000){
//            List<UserExcelDTO> userExcelDTOS = userService.getUserData(userList);
//            //保存数据
//            userService.saveExcelUser(userExcelDTOS);
//        }
//    }
}
