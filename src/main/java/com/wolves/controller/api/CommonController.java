package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.wolves.common.OSSClientConstants;
import com.wolves.dto.PictureDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.PicturesService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 *
 * @author gf
 * @date 2019/3/26
 */
@RestController
@RequestMapping(value = "/app/user")
@Api(tags="CommonController",description="公共的控制层")
public class CommonController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name="picturesService")
    private PicturesService picturesService;
    @Resource(name="userService")
    private UserService userService;

    @ApiOperation(value = "上传文件", notes = "上传文件API,上传返回的是图片链接，可以传多个")
    @RequestMapping(value = "/saveFile", method = RequestMethod.POST)
    public Result<List<String>> saveFile(@RequestParam(required=false) MultipartFile[] files, HttpServletRequest request)throws Exception{
        logger.info("start file !");
        Result<List<String>> result = new Result<List<String>>();
        List<String> urls = new ArrayList<String>();
        if (files != null && files.length > 0){
            for (MultipartFile file : files){
                String url = OssUtil.uploadObject2OSS(file, OSSClientConstants.BACKET_NAME, OSSClientConstants.FOLDER);
                urls.add(url);
            }
        }
        result.setData(urls);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("上传成功");
        return result;
    }

    @ApiOperation(value = "获取轮播列表", notes = "获取轮播列表")
    @RequestMapping(value = "/selectPricture", method = RequestMethod.POST)
    public Result<List<PictureDTO>> selectPricture(){
        Result<List<PictureDTO>> result = new Result<List<PictureDTO>>();
        List<PictureDTO> pictureDTOs = picturesService.selectPicture();

        result.setData(pictureDTOs);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

//    @ApiOperation(value = "文件下载", notes = "文件下载")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
//            @ApiImplicitParam(name = "jsonObject",value = "{\"fileName\":\"fileName\"}",required = true,paramType = "body",dataType = "JSONObject")
//    })
//    @RequestMapping(value = "/download", method = RequestMethod.POST)
//    public Result download(@RequestHeader("Authorization") String token,
//                         @RequestBody JSONObject jsonObject,
//                           HttpServletRequest request,
//                           HttpServletResponse response){
//        Result result = new Result();
//        String filePath = jsonObject.getString("fileName");
//        if (StringUtils.isEmpty(filePath)){
//            result.setResult(ResultCode.FAIL);
//            result.setMsg("文件链接不能为空");
//            return result;
//        }
//        User user = userService.getUser(token);
//        if (user == null){
//            result.setMsg("请登录");
//            result.setResult(ResultCode.FAIL);
//            return result;
//        }
//        OssUtil.downFile(filePath, request, response);
//        result.setResult(ResultCode.SUCCESS);
//        result.setMsg("下载成功");
//        return result;
//    }
}
