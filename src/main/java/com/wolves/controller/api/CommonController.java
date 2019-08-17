package com.wolves.controller.api;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wolves.common.LicensePlateEnum;
import com.wolves.common.OSSClientConstants;
import com.wolves.dto.DownFileDTO;
import com.wolves.dto.PictureDTO;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.PicturesService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @ApiOperation(value = "文件下载", notes = "文件下载API")
    @RequestMapping(value = "/downFile", method = RequestMethod.POST)
    public Result downFile(HttpServletRequest request,HttpServletResponse response, @RequestBody DownFileDTO downFileDTO){
        Result result = new Result();
        String url = downFileDTO.getUrl();
        if (StringUtils.isEmpty(url)){
            result.setMsg("请添加下载链接");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        OssUtil.downloadFile(request, response, url);
        return result;
    }

    @ApiOperation(value = "获取报修楼栋选项", notes = "获取报修楼栋选项")
    @RequestMapping(value = "/queryBulid", method = RequestMethod.POST)
    public Result<JSONArray> queryBulid(){
        Result<JSONArray> result = new Result<JSONArray>();
        String path = getClass().getClassLoader().getResource("bulid.json").toString();
        path = path.replace("\\", "/");
        if (path.contains(":")) {
            path = path.replace("file:/","");
        }
        JSONArray jsonArray = null;
        try {
            String input = FileUtils.readFileToString(new File(path), "UTF-8");
            JSONArray array = JSONArray.parseArray(input);

            if (array != null){
                jsonArray = array;
            }

        }catch (Exception e){
            e.printStackTrace();
            jsonArray = null;
        }

        result.setData(jsonArray);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

}
