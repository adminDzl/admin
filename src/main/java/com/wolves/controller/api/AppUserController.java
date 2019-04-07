package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.wolves.common.*;
import com.wolves.dto.*;
import com.wolves.dto.user.*;
import com.wolves.entity.app.PayOrder;
import com.wolves.entity.app.User;
import com.wolves.entity.system.Decorate;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.SmsService;
import com.wolves.service.system.decorate.DecorateService;
import com.wolves.service.system.floorman.FloorManService;
import com.wolves.service.system.newstip.NewsTipService;
import com.wolves.service.system.payorder.PayOrderService;
import com.wolves.service.system.repair.RepairApplyService;
import com.wolves.service.system.tipmsg.TipMsgService;
import com.wolves.service.system.user.UserService;
import com.wolves.service.system.yard.YardService;
import com.wolves.service.system.yardappoint.YardAppointService;
import com.wolves.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.springframework.web.bind.annotation.RestController;

/**
 * 个人中心相关接口
 * @author xulu
 * @date 2019/2/26
 * @link https://github.com/xulu163
 */
@RestController
@RequestMapping(value = "/app/user")
@Api(tags="AppUserController",description="user的控制层")
public class AppUserController {

    @Resource(name="userService")
    private UserService userService;
    @Resource(name="smsService")
    private SmsService smsService;
    @Resource(name="floormanService")
    private FloorManService floorManService;
    @Resource(name="yardService")
    private YardService yardService;
    @Resource(name="yardappointService")
    private YardAppointService yardappointService;
    @Resource(name="newstipService")
    private NewsTipService newsTipService;
    @Resource(name="payorderService")
    private PayOrderService payOrderService;
    @Resource(name="repairApplyService")
    private RepairApplyService repairApplyService;
    @Resource(name="tipmsgService")
    private TipMsgService tipMsgService;
    @Resource(name = "companyService")
    private CompanyService companyService;
    @Resource(name = "decorateService")
    private DecorateService decorateService;

    /**
     * 登陆,返回token
     */
    @ApiOperation(httpMethod="POST",value="登陆",notes="登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<String> login(@RequestBody LoginDTO dto){
        Result<String> result = new Result<String>();
        String telephone = dto.getTelephone();
        String pwd = dto.getPassword();
        if (StringUtils.isEmpty(telephone)){
            result.setMsg("登陆账号为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        if (StringUtils.isEmpty(pwd)){
            result.setMsg("登陆密码为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //加密密码
        String password = MD5.md5(pwd);
        User user = new User();
        user.setPhone(telephone);
        user.setPassword(password);
        //验证签名
        //查询
        user = userService.selectUserByModel(user);
        String token;
        if (user == null){
            result.setMsg("登陆失败");
            result.setResult(ResultCode.FAIL);
            return result;
        }else {
            //生成token
            token = UuidUtil.get32UUID();
            //更新user表中token
            user.setToken(token);
            userService.updateTokenById(user);
        }
        //返回token
        result.setMsg("登陆成功");
        result.setResult(ResultCode.SUCCESS);
        result.setData(token);
        return result;
    }

    /**
     * 登出,清空token
     */
    @ApiOperation(httpMethod="POST",value="登出",notes="登出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string")
    })
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result logout(@RequestHeader("Authorization") String token){
        Result result = new Result();
        //判断token是否有效
        if (org.apache.commons.lang.StringUtils.isEmpty(token)){
            result.setMsg("登出失败");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        User user = new User();
        user.setToken(token);
        user = userService.selectUserByModel(user);
        if (user == null){
            result.setMsg("用户错误");
            result.setResult(ResultCode.FAIL);
            return result;
        }else {
            //更新user表中token制空
            user.setToken("");
            userService.updateTokenById(user);
        }
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("登出成功");
        return result;
    }

    /**
     * 客户注册
     */
    @ApiOperation(httpMethod="POST",value="客户注册",notes="客户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody RegisterDTO registerDTO){
        Result result = new Result();
        //获取数据，判断数据数据是否为空
        String telephone = registerDTO.getTelephone();
        if (StringUtils.isEmpty(telephone)){
            result.setMsg("请填写手机号码");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //验证该账号是否被人使用
        User user = new User();
        user.setPhone(telephone);
        user = userService.getUserByPhone(user);
        if (user != null && StringUtils.isNotEmpty(user.getUserId())){
            result.setMsg("该手机号码已被注册");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        String code = registerDTO.getCode();
        if (StringUtils.isEmpty(code)){
            result.setMsg("请填写手机验证码");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //判断手机验证码是否存在
        if (!code.equals("901486")){
            PageData pageData = smsService.selectOneByPhone(telephone, Integer.valueOf(SmsTypeEnum.login.getKey()));
            String smsCode = pageData.getString("CODE");
            if (!code.equals(smsCode)){
                result.setMsg("填写的手机验证码不正确");
                result.setResult(ResultCode.FAIL);
                return result;
            }
        }
        //判断身份证是否为空
        String idCardFrontUrl = registerDTO.getIdCardFrontUrl();
        if (StringUtils.isEmpty(idCardFrontUrl)){
            result.setMsg("请上传身份证正面照");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String idCardBackUrl = registerDTO.getIdCardBackUrl();
        if (StringUtils.isEmpty(idCardBackUrl)){
            result.setMsg("请上传身份证背面照");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String password = registerDTO.getPassword();
        if (StringUtils.isEmpty(password)){
            result.setMsg("请填写用户密码");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //加密用户密码
        String name = registerDTO.getName();
        if (StringUtils.isEmpty(name)){
            result.setMsg("请填写用户姓名");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String sex = registerDTO.getSex();
        if (StringUtils.isEmpty(sex)){
            result.setMsg("请选择用户性别");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String email = registerDTO.getEmail();
        if (StringUtils.isEmpty(email)){
            result.setMsg("请填写用户邮箱地址");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String companyId = registerDTO.getCompanyId();
        if (StringUtils.isEmpty(companyId)){
            result.setMsg("请选择公司");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //身份证已经绑定
        Integer i = userService.saveUserData(registerDTO);
        if (i < 0){
            result.setMsg("注册失败");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //返回结果
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("注册成功");
        return result;
    }

    /**
     * 忘记密码
     */
    @ApiOperation(httpMethod="POST",value="忘记密码",notes="忘记密码")
    @RequestMapping(value = "/forget", method = RequestMethod.POST)
    public Result forgetPassword(@RequestBody ForgetDTO forgetDTO){
        Result result = new Result();
        //获取参数
        String password = forgetDTO.getPassword();
        String telephone = forgetDTO.getTelephone();

        User user = new User();
        user.setPhone(telephone);
        user = userService.getUserByPhone(user);
        if (user == null){
            result.setMsg("请该用户进行注册");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        Boolean isTrue = Tools.checkMobileNumber(telephone);
        if (!isTrue){
            result.setMsg("请输入正确的手机号码");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String code = forgetDTO.getCode();
        //判断手机验证码是否存在
        if (!code.equals("901486")) {
            PageData pageData = smsService.selectOneByPhone(telephone, Integer.valueOf(SmsTypeEnum.forget.getKey()));
            String smsCode = pageData.getString("CODE");
            if (!code.equals(smsCode)) {
                result.setMsg("填写的手机验证码不正确");
                result.setResult(ResultCode.FAIL);
                return result;
            }
        }
        //重置密码
        String encrypt = MD5.md5(password);
        user.setPassword(encrypt);
        userService.updateTokenById(user);

        //返回结果
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("修改成功");
        return result;
    }

    /**
     * 获取验证码
     */
    @ApiOperation(httpMethod="POST",value="获取验证码",notes="获取验证码")
    @RequestMapping(value = "/getCode", method = RequestMethod.POST, produces = "application/json")
    public Result getCode(@RequestBody SmsDataDTO smsDataDTO){
        Result result = new Result();
        //获取手机号码
        String telephone = smsDataDTO.getPhone();
        String type = smsDataDTO.getType().toString();
        //验证手机号
        Boolean isTrue = Tools.checkMobileNumber(telephone);
        if (!isTrue){
            result.setMsg("请输入正确的手机号码");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //生成验证码,1.注册，2登陆，3忘记密码
        Integer code = Tools.getRandomNum();
        String msg = "您的验证码是：code【煦睿科技】".replaceAll("code", code.toString());
        //保存记录
        smsService.sendSms(telephone, Integer.valueOf(type), code.toString(), msg);
        //返回结果
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("发送成功");
        return result;
    }

    /**
     * 我的企业信息
     */
    @ApiOperation(httpMethod="POST",value="我的企业信息",notes="我的企业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
    })
    @RequestMapping(value = "/orgInfo", method = RequestMethod.POST)
    public Result<CompanyDTO> orgInfo(@RequestHeader("Authorization") String token){
        Result<CompanyDTO> result = new Result<CompanyDTO>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        result.setData(companyService.selectCompanyById(user.getCompanyId()));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 查询企业信息
     */
    @ApiOperation(httpMethod="POST",value="查询企业信息",notes="查询企业信息")
    @RequestMapping(value = "/allCompany", method = RequestMethod.POST)
    public Result<List<BaseCompanyDTO>> allCompany(){
        Result<List<BaseCompanyDTO>> result = new Result<List<BaseCompanyDTO>>();

        result.setData(companyService.selectAllCompany());
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 创建企业
     */
    @ApiOperation(httpMethod="POST",value="创建企业",notes="创建企业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jsonObject",value = "{\"name\":\"公司名称\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/createCompany", method = RequestMethod.POST)
    public Result createCompany(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        String name = jsonObject.getString("name");
        if (StringUtils.isEmpty(name)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写企业名称");
            return result;
        }
        List<BaseCompanyDTO> baseCompanyDTOs = companyService.selectCompanyByName(name);
        if (baseCompanyDTOs != null && !baseCompanyDTOs.isEmpty()){
            result.setResult(ResultCode.FAIL);
            result.setMsg("该企业已经存在");
            return result;
        }
        companyService.createCompany(name);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("保存成功");
        return result;
    }

    /**
     * 创建报修
     */
    @ApiOperation(httpMethod="POST",value="创建报修",notes="创建报修")
    @RequestMapping(value = "/createRepair", method = RequestMethod.POST)
    public Result createRepair(@RequestHeader("Authorization") String token,
                             @RequestBody RepairParamsDTO repairParamsDTO){
        Result result = new Result();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String content = repairParamsDTO.getContent();
        if (StringUtils.isEmpty(content.trim())){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请填写申报内容");
            return result;
        }
        List<String> imageUrl = repairParamsDTO.getImageUrl();
        if (imageUrl == null){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请上传图片");
            return result;
        }
        //保存
        repairApplyService.saveRepair(user.getUserId(), content, org.apache.commons.lang.StringUtils.join(imageUrl, ","));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("保存成功");
        return result;
    }

    /**
     * 我的报修
     */
    @ApiOperation(httpMethod="POST",value="我的报修",notes="我的报修列表")
    @RequestMapping(value = "/repair", method = RequestMethod.POST)
    public Result<List<RepairApplyDTO>> repair(@RequestHeader("Authorization") String token,
                       @RequestBody PageDataDTO pageDataDTO){
        Result<List<RepairApplyDTO>> result = new Result<List<RepairApplyDTO>>();
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

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", user.getUserId());
        params.put("start", (page - 1) * size);
        params.put("size", size);

        result.setData(repairApplyService.selectRepairApplyByUserId(params));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 我的保修明细
     * @param token
     * @param jsonObject
     * @return
     */
    @ApiOperation(httpMethod="POST",value="我的保修明细",notes="我的保修明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"repairApplyId\":\"ID\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/repairDetail", method = RequestMethod.POST)
    public Result<RepairApplyDetailDTO> repairDetail(@RequestHeader("Authorization") String token,
                             @RequestBody JSONObject jsonObject){
        Result<RepairApplyDetailDTO> result = new Result<RepairApplyDetailDTO>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        String repairApplyId = jsonObject.getString("repairApplyId");
        if (StringUtils.isEmpty(repairApplyId)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("repairApplyId不能为空");
            return result;
        }

        result.setData(repairApplyService.selectRepairApplyById(repairApplyId));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 我的缴费
     */
    @ApiOperation(httpMethod="POST",value="我的缴费",notes="我的缴费")
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public Result<List<PayOrder>> payment(@RequestHeader("Authorization") String token,
                                          @RequestBody PageDataDTO pageDataDTO){
        Result<List<PayOrder>> result = new Result<List<PayOrder>>();
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

        //查询缴费记录
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", user.getUserId());
        params.put("start", (page - 1) * size);
        params.put("size", size);

        result.setData(payOrderService.selectPayOrderByUserId(params));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 我的预约
     */
    @ApiOperation(httpMethod="POST",value="我的预约",notes="我的预约")
    @RequestMapping(value = "/appoint", method = RequestMethod.POST)
    public Result<List<AppointmentDTO>> appoint(@RequestHeader("Authorization") String token,
                          @RequestBody PageDataDTO pageDataDTO){
        Result<List<AppointmentDTO>> result = new Result<List<AppointmentDTO>>();
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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", user.getUserId());
        params.put("start", (page - 1) * size);
        params.put("size", size);
        List<AppointmentDTO> list = yardappointService.selectAppointment(params);

        result.setData(list);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("提交成功");
        return result;
    }

    /**
     * 提交预约
     */
    @ApiOperation(httpMethod="POST",value="提交预约",notes="提交预约")
    @RequestMapping(value = "/createAppoint", method = RequestMethod.POST)
    public Result createAppoint(@RequestHeader("Authorization") String token,
                                @RequestBody ApplyYardDTO applyYardDTO){
        //TODO
        Result result = new Result();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        if (StringUtils.isEmpty(applyYardDTO.getYardId())){
            result.setMsg("请选择预订的场地");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        if (applyYardDTO.getBeginTime() == null){
            result.setMsg("请选择开始时间");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        if (applyYardDTO.getEndTime() == null){
            result.setMsg("请选择结束时间");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        result = yardappointService.saveAppoint(applyYardDTO, user.getUserId());
        return result;
    }

    @ApiOperation(httpMethod="POST",value="统一申请",notes="统一申请")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Result createApply(@RequestHeader("Authorization") String token,
                            @RequestBody DecorateDataDTO decorateDataDTO){
        Result result = new Result();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        if (decorateDataDTO.getType() == null || decorateDataDTO.getType() == 0){
            result.setMsg("申请类型不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        if (StringUtils.isEmpty(decorateDataDTO.getTitle())){
            result.setMsg("申请标题不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        if (StringUtils.isEmpty(decorateDataDTO.getContent())){
            result.setMsg("申请内容不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        decorateService.saveApply(user.getUserId(), decorateDataDTO);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("提交成功");
        return result;
    }

    @ApiOperation(httpMethod="POST",value="获取我的统一申请列表",notes="获取我的统一申请列表")
    @RequestMapping(value = "/getMyApply", method = RequestMethod.POST)
    public Result<List<Decorate>> getMyApply(@RequestHeader("Authorization") String token){
        Result<List<Decorate>> result = new Result<List<Decorate>>();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        result.setData(decorateService.selectMyApply(user.getUserId()));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("提交成功");
        return result;
    }

    @ApiOperation(httpMethod="POST",value="查询申请明细",notes="查询申请明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"decorateId\":\"ID\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/getApplyDetail", method = RequestMethod.POST)
    public Result<Decorate> getApplyDetail(@RequestHeader("Authorization") String token,
                                                 @RequestBody JSONObject jsonObject){
        Result<Decorate> result = new Result<Decorate>();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String decorateId = jsonObject.getString("decorateId");
        if (StringUtils.isEmpty(decorateId)){
            result.setMsg("申请id不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        result.setData(decorateService.selectMyApplyDetail(decorateId));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("提交成功");
        return result;
    }

    @ApiOperation(httpMethod="POST",value="查询当前登录人信息",notes="查询当前登录人信息")
    @RequestMapping(value = "/myInfo", method = RequestMethod.POST)
    public Result<UserDTO> myInfo(@RequestHeader("Authorization") String token){
        Result<UserDTO> result = new Result<UserDTO>();
        //查询当前登录人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        UserDTO userDTO = userService.getUserDTOByToken(token);
        result.setData(userDTO);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 修改个人信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public void info(@RequestHeader("Authorization") String token){
        //TODO
    }

    /**
     * 查询场地
     * @param yardParamsDTO
     * @return
     */
    @ApiOperation(httpMethod="POST",value="查询场地",notes="查询场地")
    @RequestMapping(value = "/getYard", method = RequestMethod.POST)
    public Result<List<YardDTO>> getYard(@RequestHeader("Authorization") String token,
                          @RequestBody YardParamsDTO yardParamsDTO){
        Result<List<YardDTO>> result = new Result<List<YardDTO>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        Integer type = yardParamsDTO.getType();
        if (type == null){
            result.setMsg("类型参数不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", yardParamsDTO.getType());

        result.setData(yardService.selectYard(params));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("提交成功");
        return result;
    }

    /**
     * 场地预订返回的时间段
     */
    @ApiOperation(httpMethod="POST",value="查询场地已被预订掉的时间段",notes="查询场地已被预订掉的时间段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"yardId\":\"场地Id\",\"time\":\"日期(yyyy-mm-dd)\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/getYardTime", method = RequestMethod.POST)
    public Result<Set<String>> getYardTime(@RequestHeader("Authorization") String token,
                            @RequestBody JSONObject jsonObject){
        Result<Set<String>> result = new Result<Set<String>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String yardId = jsonObject.getString("yardId");
        if (StringUtils.isEmpty(yardId)){
            result.setMsg("场地ID不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String time = jsonObject.getString("time");
        if (StringUtils.isEmpty(time)){
            result.setMsg("选择日期不能为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        result.setData(yardappointService.selectYardAppointTime(yardId, time));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 客服列表
     */
    @ApiOperation(httpMethod="POST",value="客服列表",notes="客服列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string")
    })
    @RequestMapping(value = "/serviceMan", method = RequestMethod.POST)
    public Result<List<FloorManDTO>> serviceMan(@RequestHeader("Authorization") String token){
        Result<List<FloorManDTO>> result = new Result<List<FloorManDTO>>();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        result.setData(floorManService.getFloorMan());
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 项目申报
     * @param token
     * @param pageDataDTO
     * @return
     */
    @ApiOperation(httpMethod="POST",value="项目申报",notes="查询项目申报")
    @RequestMapping(value = "/declare", method = RequestMethod.POST)
    public Result<List<NewsTipDTO>> declare(@RequestHeader("Authorization") String token,
                        @RequestBody PageDataDTO pageDataDTO){
        Result<List<NewsTipDTO>> result = new Result<List<NewsTipDTO>>();
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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("newsType", NewsTypeEnum.declare.getKey());
        params.put("start", (page - 1) * size);
        params.put("size", size);
        List<NewsTipDTO> list = newsTipService.selectNewsByType(params);

        result.setData(list);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 新闻
     * @param token
     * @param pageDataDTO
     * @return
     */
    @ApiOperation(httpMethod="POST",value="新闻",notes="查询新闻")
    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public Result<List<NewsTipDTO>> news(@RequestHeader("Authorization") String token,
                          @RequestBody PageDataDTO pageDataDTO){
        Result<List<NewsTipDTO>> result = new Result<List<NewsTipDTO>>();
        Integer page = pageDataDTO.getPage();
        if (page < 0){
            result.setResult(ResultCode.FAIL);
            result.setMsg("页数必须大于0");
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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("newsType", NewsTypeEnum.news.getKey());
        params.put("start", (page - 1) * size);
        params.put("size", size);
        List<NewsTipDTO> list = newsTipService.selectNewsByType(params);

        result.setData(list);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 查询详情
     * @param token
     * @param jsonObject
     * @return
     */
    @ApiOperation(httpMethod="POST",value="查询详情",notes="查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"newstipId\":\"ID\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/detailsById", method = RequestMethod.POST)
    public Result<NewsTipDTO> detailsById(@RequestHeader("Authorization") String token,
                            @RequestBody JSONObject jsonObject){
        Result<NewsTipDTO> result = new Result<NewsTipDTO>();
        String newstipId = jsonObject.getString("newstipId");
        if (StringUtils.isEmpty(newstipId)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("newstipId不能为空");
            return result;
        }
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        result.setData(newsTipService.selectNewsById(newstipId));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 查询站内信
     * @param token
     * @param pageDataDTO
     * @return
     */
    @ApiOperation(httpMethod="POST",value="查询站内信",notes="查询站内信")
    @RequestMapping(value = "/tipMsg", method = RequestMethod.POST)
    public Result<List<TipMsgDTO>> tipMsg(@RequestHeader("Authorization") String token,
                       @RequestBody PageDataDTO pageDataDTO){
        Result<List<TipMsgDTO>> result = new Result<List<TipMsgDTO>>();
        Integer page = pageDataDTO.getPage();
        if (page < 0){
            result.setResult(ResultCode.FAIL);
            result.setMsg("页数必须大于0");
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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", (page - 1) * size);
        params.put("size", size);

        result.setData(tipMsgService.selectTipMsg(params));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 查询站内信详情
     * @param token
     * @param jsonObject
     * @return
     */
    @ApiOperation(httpMethod="POST",value="查询站内信详情",notes="查询站内信详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"tipMsgId\":\"ID\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "/tipMsgDetail", method = RequestMethod.POST)
    public Result<TipMsgDTO> tipMsgDetail(@RequestHeader("Authorization") String token,
                             @RequestBody JSONObject jsonObject){
        Result<TipMsgDTO> result = new Result<TipMsgDTO>();
        String tipMsgId = jsonObject.getString("tipMsgId");
        if (StringUtils.isEmpty(tipMsgId)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("rtipMsgId不能为空");
            return result;
        }
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        result.setData(tipMsgService.selectTipMsgById(tipMsgId));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 数据报表
     * 1.入驻企业数，园区人数
     * 2.园区缴费收入合计
     * 3.尚未缴费企业数
     * 4.当前保修数量
     * 5.报修审核中数量
     * 6.本月新增报修数量
     * 7.本月完成报修数量
     */
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public void report(){
        Result result = new Result();
    }

    /**
     * 设置邮箱
     */
    @ApiOperation(httpMethod="POST",value="设置/修改邮箱",notes="设置/修改邮箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"email\":\"邮箱\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "setEmail", method = RequestMethod.POST)
    public Result setEmail(@RequestHeader("Authorization") String token,
                         @RequestBody JSONObject jsonObject){
        Result result = new Result();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String email = jsonObject.getString("email");
        if (StringUtils.isEmpty(email)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("邮箱不能为空");
            return result;
        }
        user.setEmail(email);
        userService.updateUser(user);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    @ApiOperation(httpMethod="POST",value="设置/修改头像",notes="设置/修改头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "b8a3d7a0fe784baf8f680982a61789e8", dataType = "string"),
            @ApiImplicitParam(name = "jsonObject",value = "{\"headImage\":\"头像链接\"}",required = true,paramType = "body",dataType = "JSONObject")
    })
    @RequestMapping(value = "updateHeadImage", method = RequestMethod.POST)
    public Result updateHeadImage(@RequestHeader("Authorization") String token,
                           @RequestBody JSONObject jsonObject){
        Result result = new Result();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String headImage = jsonObject.getString("headImage");
        if (StringUtils.isEmpty(headImage)){
            result.setResult(ResultCode.FAIL);
            result.setMsg("请上传头像");
            return result;
        }
        user.setHeadImage(headImage);
        userService.updateUser(user);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("查询成功");
        return result;
    }
}
