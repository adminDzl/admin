package com.fh.controller.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by xulu on 2016/3/19.
 */
@Controller
public class SpreadController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * app下载
     */
    @RequestMapping("/spread/download")
    public ModelAndView download() throws Exception {
        ModelAndView modelAndView = new ModelAndView("/app/down");
        return modelAndView;
    }

    /**
     * 当月热门活动
     */
    @RequestMapping("/spread/hot_activity")
    public void activity(HttpServletResponse response) throws Exception {
        response.sendRedirect("/stat/help.htm");
    }

    /**
     * 热点问题
     */
    @RequestMapping("/spread/hot_question")
    public void hotQuestion(HttpServletResponse response) throws Exception {
        response.sendRedirect("/stat/help.htm");
    }
}
