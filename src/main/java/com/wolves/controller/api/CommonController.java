package com.wolves.controller.api;

import com.wolves.common.OSSClientConstants;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
}
