package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.wolves.dto.BuildDTO;
import com.wolves.dto.FloorDTO;
import com.wolves.dto.RoomDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.buildman.BuildManService;
import com.wolves.service.system.floorman.FloorManService;
import com.wolves.service.system.room.RoomService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 楼层
 * Created by Administrator on 2019/4/11.
 */
@RestController
@RequestMapping(value = "/app/user")
@Api(tags="BuildController",description="楼栋的控制层")
public class BuildController {

    @Resource(name="userService")
    private UserService userService;

    @Resource(name="buildmanService")
    private BuildManService buildManService;

    @Resource(name="floormanService")
    private FloorManService floorManService;

    @Resource(name="roomService")
    private RoomService roomService;

    @ApiOperation(httpMethod="POST",value="全部楼栋，楼层，房间信息",notes="全部楼栋，楼层，房间信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/getAllBuild", method = RequestMethod.POST)
    public Result<List<BuildDTO>> getAllBuild(@RequestHeader("Authorization") String token){
        Result<List<BuildDTO>> result = new Result<List<BuildDTO>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        List<BuildDTO> buildDTOs = buildManService.listAll();
        result.setData(buildDTOs);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    @ApiOperation(httpMethod="POST",value="全部楼栋",notes="全部楼栋")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/getBuild", method = RequestMethod.POST)
    public Result<List<BuildDTO>> getBuild(@RequestHeader("Authorization") String token){
        Result<List<BuildDTO>> result = new Result<List<BuildDTO>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        List<BuildDTO> buildDTOs = buildManService.listBuild();
        result.setData(buildDTOs);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    @ApiOperation(httpMethod="POST",value="根据楼层号查询楼层",notes="根据楼层号查询楼层")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"name\":\"栋号name\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/getFloor", method = RequestMethod.POST)
    public Result<List<FloorDTO>> getFloor(@RequestHeader("Authorization") String token,
                                           @RequestBody JSONObject jsonObject){
        Result<List<FloorDTO>> result = new Result<List<FloorDTO>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String buildNo = jsonObject.getString("name");
        if (StringUtils.isEmpty(buildNo.trim())){
            result.setMsg("楼栋name为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        List<FloorDTO> floorDTOs = floorManService.getAllFloor(buildNo.trim());
        result.setData(floorDTOs);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    @ApiOperation(httpMethod="POST",value="查询房间根据楼层",notes="查询房间根据楼层")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"id\":\"楼层id\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/getRoom", method = RequestMethod.POST)
    public Result<List<RoomDTO>> getRoom(@RequestHeader("Authorization") String token,
                                           @RequestBody JSONObject jsonObject){
        Result<List<RoomDTO>> result = new Result<List<RoomDTO>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String id = jsonObject.getString("id");
        if (StringUtils.isEmpty(id.trim())){
            result.setMsg("楼层id为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        List<RoomDTO> roomDTOs = roomService.listRoom(id.trim());
        result.setData(roomDTOs);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }
}
