package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.wolves.dto.decorate.DecorateParamDTO;
import com.wolves.dto.decorate.DecorationApplyDTO;
import com.wolves.dto.repair.ProcessLogDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.decorate.DecorateApplyService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 装修相关接口
 * @author xulu
 * @date 2019/2/26
 * @link https://github.com/xulu163
 */
@RestController
@RequestMapping(value = "/app/user")
@Api(tags="DecorationController",description="decoration的控制层")
public class DecorationController {

    @Resource(name="userService")
    private UserService userService;

    @Resource(name="decorateApplyService")
    private DecorateApplyService decorateApplyService;

    /**
     * 创建装修
     */
    @ApiOperation(httpMethod="POST",value="创建装修",notes="创建装修")
    @RequestMapping(value = "/decorate/createDecorateApply", method = RequestMethod.POST)
    public Result createRepair(@RequestHeader("Authorization") String token,
                               @RequestBody DecorateParamDTO decorateParamDTO){
        Result result = new Result();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String title = decorateParamDTO.getTitle();
        if (StringUtils.isEmpty(title.trim())){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写装修修标题");
            return result;
        }

        String fadingren = decorateParamDTO.getFadingren();
        if (StringUtils.isEmpty(fadingren)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写法定代表人");
            return result;
        }

        String fadingPhone = decorateParamDTO.getFadingPhone();
        if (StringUtils.isEmpty(fadingPhone)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写法定联系电话");
            return result;
        }

        Date zuling = decorateParamDTO.getZuling();
        if (zuling == null){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请选择租赁时间");
            return result;
        }

        String xianchangren = decorateParamDTO.getXianchangren();
        if (StringUtils.isEmpty(xianchangren)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写现场负责人");
            return result;
        }

        String xianchangPhone = decorateParamDTO.getXianchangPhone();
        if (StringUtils.isEmpty(xianchangPhone)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写现场联系电话");
            return result;
        }

        String zhuangxiuName = decorateParamDTO.getZhuangxiuName();
        if (StringUtils.isEmpty(zhuangxiuName)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写装修单位");
            return result;
        }

        String zhizhaoNum = decorateParamDTO.getZhizhaoNum();
        if (StringUtils.isEmpty(zhizhaoNum)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写执照号码");
            return result;
        }

        String zhuangxiuren = decorateParamDTO.getZhuangxiuren();
        if (StringUtils.isEmpty(zhuangxiuren)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写装修负责人");
            return result;
        }
        String zhuangxiuPhone = decorateParamDTO.getZhuangxiuPhone();
        if (StringUtils.isEmpty(zhuangxiuPhone)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写装修联系电话");
            return result;
        }

        Date zhuangxiuTime = decorateParamDTO.getZhuangxiuTime();
        if (zhuangxiuTime == null){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请选择装修时间");
            return result;
        }

        String zhuangxirenshu = decorateParamDTO.getZhuangxirenshu();
        if (StringUtils.isEmpty(zhuangxirenshu)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写装修人数");
            return result;
        }

        String zhuangxiufangwei = decorateParamDTO.getZhuangxiufangwei();
        if (StringUtils.isEmpty(zhuangxiufangwei)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写装修范围");
            return result;
        }

        decorateApplyService.createDecorate(user, decorateParamDTO);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("保存成功");
        return result;
    }

    /**
     * 我的装修申请
     */
    @ApiOperation(httpMethod="POST",value="我的装修申请",notes="我的装修申请")
    @RequestMapping(value = "/decorate/myDecorate", method = RequestMethod.POST)
    public Result<List<DecorationApplyDTO>> myDecorate(@RequestHeader("Authorization") String token){
        Result<List<DecorationApplyDTO>> result = new Result<List<DecorationApplyDTO>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        result.setData(decorateApplyService.selectDecorationApplyByUserId(user.getUserId()));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 我的装修申请明细
     * @param token
     * @param jsonObject
     * @return
     */
    @ApiOperation(httpMethod="POST",value="我的装修申请明细",notes="我的装修申请明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"decorationApplyId\":\"ID\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/decorate/decorateDetail", method = RequestMethod.POST)
    public Result<DecorationApplyDTO> decorateDetail(@RequestHeader("Authorization") String token,
                                       @RequestBody JSONObject jsonObject){
        Result<DecorationApplyDTO> result = new Result<DecorationApplyDTO>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        String decorationApplyId = jsonObject.getString("decorationApplyId");
        if (StringUtils.isEmpty(decorationApplyId)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("decorationApplyId不能为空");
            return result;
        }

        result.setData(decorateApplyService.selectDecorationApplyById(decorationApplyId));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }


    @ApiOperation(httpMethod="POST",value="装修日志信息",notes="装修日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"decorationApplyId\":\"ID\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/decorate/decorateLog", method = RequestMethod.POST)
    public Result<List<ProcessLogDTO>> repairLog(@RequestHeader("Authorization") String token,
                                                 @RequestBody JSONObject jsonObject){
        Result<List<ProcessLogDTO>> result = new Result<List<ProcessLogDTO>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        String decorationApplyId = jsonObject.getString("decorationApplyId");
        if (StringUtils.isEmpty(decorationApplyId)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("decorationApplyId不能为空");
            return result;
        }
        result.setData(decorateApplyService.listProcessLog(decorationApplyId));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    @ApiOperation(httpMethod="POST",value="撤销报修",notes="撤销报修")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"decorationApplyId\":\"ID\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/decorate/backOutRepair", method = RequestMethod.POST)
    public Result backOutRepair(@RequestHeader("Authorization") String token,
                                @RequestBody JSONObject jsonObject){
        Result result = new Result();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        String decorationApplyId = jsonObject.getString("decorationApplyId");
        if (StringUtils.isEmpty(decorationApplyId)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("decorationApplyId不能为空");
            return result;
        }

        decorateApplyService.stopJobs(decorationApplyId);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("撤销成功");
        return result;
    }
}
