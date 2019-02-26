package com.wolves.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人中心相关接口
 * @author xulu
 * @date 2019/2/26
 * @link https://github.com/xulu163
 */
@RestController
@RequestMapping(value = "/api/user")
public class AppUserController {

    /**
     * 登陆,返回token
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(){

    }

    /**
     * 登出,清空token
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(){

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
