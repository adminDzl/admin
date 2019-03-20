package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.wolves.common.LicensePlateEnum;
import com.wolves.common.NewsTypeEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dto.*;
import com.wolves.dto.user.ForgetDTO;
import com.wolves.dto.user.LoginDTO;
import com.wolves.dto.user.RegisterDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.SmsService;
import com.wolves.service.system.appuser.UserCarBindService;
import com.wolves.service.system.floorman.FloorManService;
import com.wolves.service.system.newstip.NewsTipService;
import com.wolves.service.system.parking.ParkingService;
import com.wolves.service.system.payorder.PayOrderService;
import com.wolves.service.system.repair.RepairApplyService;
import com.wolves.service.system.tipmsg.TipMsgService;
import com.wolves.service.system.user.UserService;
import com.wolves.service.system.yard.YardService;
import com.wolves.service.system.yardappoint.YardAppointService;
import com.wolves.util.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 个人中心相关接口
 * @author xulu
 * @date 2019/2/26
 * @link https://github.com/xulu163
 */
@RestController
@RequestMapping(value = "/app/user")
public class AppUserController {

    @Resource(name="userService")
    private UserService userService;
    @Resource(name="smsService")
    private SmsService smsService;
    @Resource(name="floormanService")
    private FloorManService floorManService;
    @Resource(name="usercarbindService")
    private UserCarBindService usercarbindService;
    @Resource(name="yardService")
    private YardService yardService;
    @Resource(name="yardappointService")
    private YardAppointService yardappointService;
    @Resource(name="parkingService")
    private ParkingService parkingService;
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

    /**
     * 登陆,返回token
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody LoginDTO dto){
        Result result = new Result();
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
        String token = "";
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
        PageData pageData = smsService.selectOneByPhone(telephone);
        String smsCode = pageData.getString("CODE");
        //判断手机验证码是否存在
        if (!code.equals(smsCode)){
            result.setMsg("填写的手机验证码不正确");
            result.setResult(ResultCode.FAIL);
            return result;
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
        String encrypt = MD5.md5(password);
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
        //保存数据
        User userInfo = new User();
        userInfo.setUserId(UuidUtil.get32UUID());
        userInfo.setUsername(name);
        userInfo.setPassword(encrypt);
        userInfo.setPhone(telephone);
        userInfo.setName(name);
        userInfo.setSex(sex);
        userInfo.setIdCardFrontUrl(idCardFrontUrl);
        userInfo.setIdCardBackUrl(idCardBackUrl);
        userInfo.setIp("");
        userInfo.setEmail(email);
        //身份证已经绑定
        Integer i = userService.saveUser(userInfo);
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
    @RequestMapping(value = "/forget", method = RequestMethod.POST)
    public Result forgetPassword(@RequestHeader("Authorization") String token,
                                 @RequestBody ForgetDTO forgetDTO){
        Result result = new Result();
        //获取参数
        String password = forgetDTO.getPassword();
        String telephone = forgetDTO.getTelephone();
        Boolean isTrue = Tools.checkMobileNumber(telephone);
        if (!isTrue){
            result.setMsg("请输入正确的手机号码");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        String code = forgetDTO.getCode();
        PageData pageData = smsService.selectOneByPhone(telephone);
        String smsCode = pageData.getString("CODE");
        //判断手机验证码是否存在
        if (!code.equals(smsCode)){
            result.setMsg("填写的手机验证码不正确");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //重置密码
        String encrypt = MD5.md5(password);
        User user = new User();
        user.setPhone(telephone);
        user = userService.getUserByPhone(user);
        user.setPassword(encrypt);

        //返回结果
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("修改成功");
        return result;
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.POST, produces = "application/json")
    public Result getCode(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        //获取手机号码
        String telephone = jsonObject.getString("phone");
        String type = jsonObject.getString("type");
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

    /**
     * 我的企业信息
     */
    @RequestMapping(value = "/orgInfo", method = RequestMethod.POST)
    public Result orgInfo(@RequestHeader("Authorization") String token){
        Result result = new Result();
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
     * 创建企业
     */

    /**
     * 我的报修
     */
    @RequestMapping(value = "/repair", method = RequestMethod.POST)
    public Result repair(@RequestHeader("Authorization") String token,
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
    @RequestMapping(value = "/repairDetail", method = RequestMethod.POST)
    public Result repairDetail(@RequestHeader("Authorization") String token,
                             @RequestBody JSONObject jsonObject){
        Result result = new Result();
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
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public Result payment(@RequestHeader("Authorization") String token,
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
    @RequestMapping(value = "/appoint", method = RequestMethod.POST)
    public Result appoint(@RequestHeader("Authorization") String token,
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
    @RequestMapping(value = "/createAppoint", method = RequestMethod.POST)
    public Result createAppoint(@RequestHeader("Authorization") String token,
                                @RequestBody ApplyYardDTO applyYardDTO){
        Result result = new Result();
        //使用token获取登陆人信息
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        PageData pd = new PageData();
        //主键
        pd.put("YARDAPPOINT_ID", UuidUtil.get32UUID());
        //场地ID
        pd.put("PLACE_ID", applyYardDTO.getYardId());
        //预定人ID
        pd.put("APPLY_USER_ID", user.getUserId());
        //预定金额
        pd.put("BOOK_FEE", applyYardDTO.getAmount());
        //预定状态
        pd.put("STATUS", StatusEnum.INIT.getKey());
        //预定日期
        pd.put("PLACE_DATE", applyYardDTO.getPlaceDate());
        //开始时间
        pd.put("BEGIN_TIME", applyYardDTO.getBeginTime());
        //结束时间
        pd.put("END_TIME", applyYardDTO.getEndTime());
        //时长
        pd.put("BOOK_DURATION", applyYardDTO.getDuration());
        //备注
        pd.put("NOTE", applyYardDTO.getNote());
        yardappointService.save(pd);

        result.setResult(ResultCode.SUCCESS);
        result.setMsg("预约成功");
        return result;
    }

    /**
     * 修改个人信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public void info(@RequestHeader("Authorization") String token){

    }

    /**
     * 查询场地
     * @param pageDataDTO
     * @return
     */
    @RequestMapping(value = "/getYard", method = RequestMethod.POST)
    public Result getYard(@RequestHeader("Authorization") String token,
                          @RequestBody PageDataDTO pageDataDTO){
        Result result = new Result();
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", (page - 1) * size);
        params.put("size", size);

        result.setData(yardService.selectYard(params));
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("提交成功");
        return result;
    }

    /**
     * 客服列表
     */
    @RequestMapping(value = "/serviceMan", method = RequestMethod.POST)
    public Result serviceMan(@RequestHeader("Authorization") String token){
        Result result = new Result();;
        User user = userService.getUser(token);
        if (user == null){
            result.setMsg("请登录");
            result.setResult(ResultCode.FAIL);
            return result;
        }

        result.setData(floorManService.getFloorMan());
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("发送成功");
        return result;
    }

    /**
     * 项目申报
     * @param token
     * @param pageDataDTO
     * @return
     */
    @RequestMapping(value = "/declare", method = RequestMethod.POST)
    public Result declare(@RequestHeader("Authorization") String token,
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
    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public Result news(@RequestHeader("Authorization") String token,
                          @RequestBody PageDataDTO pageDataDTO){
        Result result = new Result();
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
    @RequestMapping(value = "/detailsById", method = RequestMethod.POST)
    public Result detailsById(@RequestHeader("Authorization") String token,
                            @RequestBody JSONObject jsonObject){
        Result result = new Result();
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

    //统一申请

    /**
     * 上传图片
     * @param files
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/imageUpload")
    public Result imageUpload(@RequestParam(required=false) MultipartFile[] files) throws Exception{
        Result result = new Result();

        String fileAddress = DateUtil.getDays(), fileName = "";
        List<Map<String,Object>> pageDatas = new ArrayList<Map<String,Object>>();
        if (files != null && files.length > 0){
            for (MultipartFile file : files){
                Map<String,Object> params = new HashMap<String, Object>();
                if (null != file && !file.isEmpty()) {
                    //文件上传路径
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + fileAddress;
                    //执行上传
                    fileName = FileUpload.fileUp(file, filePath, UuidUtil.get32UUID());
                }else{
                    System.out.println("上传失败");
                }
                //主键
                params.put("PICTURES_ID", UuidUtil.get32UUID());
                //文件名
                params.put("NAME", fileName);
                //路径
                params.put("PATH", fileAddress + "/" + fileName);
                //创建时间
                params.put("CREATETIME", Tools.date2Str(new Date()));
                pageDatas.add(params);
            }
        }

        result.setData(pageDatas);
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("上传成功");
        return result;
    }

    /**
     * 查询站内信
     * @param token
     * @param pageDataDTO
     * @return
     */
    @RequestMapping(value = "/tipMsg", method = RequestMethod.POST)
    public Result tipMsg(@RequestHeader("Authorization") String token,
                       @RequestBody PageDataDTO pageDataDTO){
        Result result = new Result();
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
    @RequestMapping(value = "/tipMsgDetail", method = RequestMethod.POST)
    public Result tipMsgDetail(@RequestHeader("Authorization") String token,
                             @RequestBody JSONObject jsonObject){
        Result result = new Result();
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
    @RequestMapping(value = "setEmail", method = RequestMethod.POST)
    public void setEmail(@RequestHeader("Authorization") String token,
                         @RequestBody JsonObject jsonObject){



    }

    /**
     * 修改邮箱
     */
    @RequestMapping(value = "editEmail", method = RequestMethod.POST)
    public void editEmail(@RequestHeader("Authorization") String token,
                         @RequestBody JsonObject jsonObject){



    }
}
