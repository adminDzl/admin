package com.wolves.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.wolves.controller.base.BaseController;
import com.wolves.dto.user.ForgetDTO;
import com.wolves.dto.user.LoginDTO;
import com.wolves.dto.user.RegisterDTO;
import com.wolves.entity.app.User;
import com.wolves.framework.common.Result;
import com.wolves.framework.common.ResultCode;
import com.wolves.service.system.SmsService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
import com.wolves.util.Tools;
import com.wolves.util.UuidUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 个人中心相关接口
 * @author xulu
 * @date 2019/2/26
 * @link https://github.com/xulu163
 */
@RestController
@RequestMapping(value = "/api/user")
public class AppUserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;

    /**
     * 登陆,返回token
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody LoginDTO loginDTO){
        Result result = new Result();
        if (StringUtils.isEmpty(loginDTO.getTelephone())){
            result.setMsg("登陆账号为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        if (StringUtils.isEmpty(loginDTO.getPassword())){
            result.setMsg("登陆密码为空");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //加密密码
        String password = new SimpleHash("SHA-1", loginDTO.getTelephone(), loginDTO.getPassword()).toString();
        User user = new User();
        user.setPhone(loginDTO.getTelephone());
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
    public Result logout(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        //判断token是否有效
        String token = jsonObject.getString("token");
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
        String encrypt = new SimpleHash("SHA-1", telephone, password).toString();
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
        //获取ip
        HttpServletRequest request = this.getRequest();
        //保存数据
        User user = new User();
        user.setUserId(this.get32UUID());
        user.setUsername(telephone);
        user.setPassword(encrypt);
        user.setPhone(telephone);
        user.setName(name);
        user.setSex(sex);
        user.setIdCardFrontUrl(idCardFrontUrl);
        user.setIdCardBackUrl(idCardBackUrl);
        user.setIp(Tools.getIpAddr(request));
        //身份证已经绑定
        Integer i = userService.saveUser(user);
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
    public Result forgetPassword(@RequestBody ForgetDTO forgetDTO){
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
        String encrypt = new SimpleHash("SHA-1", telephone, password).toString();
        User user = new User();
        user.setUserId("");
        user.setPhone(telephone);
        user.setPassword(encrypt);

        //返回结果
        return result;
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    public Result getCode(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        //获取手机号码
        String telephone = jsonObject.getString("phone");
        //验证手机号
        Boolean isTrue = Tools.checkMobileNumber(telephone);
        if (!isTrue){
            result.setMsg("请输入正确的手机号码");
            result.setResult(ResultCode.FAIL);
            return result;
        }
        //生成验证码
        Integer code = Tools.getRandomNum();
        //保存记录
        smsService.sendSms(telephone, 1, code.toString(), "您的验证码是：4625【煦睿科技】");
        //返回结果
        result.setResult(ResultCode.SUCCESS);
        result.setMsg("发送成功");
        return result;
    }

    /**
     * 我的企业信息
     */
    @RequestMapping(value = "/orgInfo", method = RequestMethod.POST)
    public void orgInfo(){

    }

    /**
     * 我的报修
     */
    @RequestMapping(value = "/repair", method = RequestMethod.POST)
    public void repair(){

    }

    /**
     * 我的缴费
     */
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public void payment(){

    }

    /**
     * 我的预约
     */
    @RequestMapping(value = "/appoint", method = RequestMethod.POST)
    public void appoint(){

    }

    /**
     * 修改个人信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public void info(){

    }

    /**
     * 客服列表
     */
    @RequestMapping(value = "/serviceMan", method = RequestMethod.POST)
    public void serviceMan(){

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

    }
}
