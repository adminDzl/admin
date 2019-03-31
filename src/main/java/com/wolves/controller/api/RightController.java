package com.wolves.controller.api;

import com.wolves.framework.common.Result;
import com.wolves.service.right.RightService;
import com.wolves.util.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * app权限模块的控制层
 * @author Administrator
 * @date 2019/3/21
 */
@RestController
@RequestMapping(value = "/app/right")
@Api(tags="RightController",description="app权限模块的控制层")
public class RightController {
    protected Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    RightService rightService;

    /**
     * 获取app用户的所有权限
     */
    @ApiOperation(httpMethod="GET",value="获取app用户的所有权限",notes="获取app用户的所有权限")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getRightsByToken(@RequestHeader("Authorization") String token){

        return null;
    }

    /**
     * 修改app用户的权限
     */
    @ApiOperation(httpMethod="POST",value="修改app用户的权限",notes="修改app用户的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader("Authorization") String token){


        return null;
    }
}
