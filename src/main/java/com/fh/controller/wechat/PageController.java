package com.fh.controller.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xulu on 2016/5/12.
 */
@Controller
public class PageController extends BaseController {

    /**
     * 天气预报
     */
    @RequestMapping("/appwechat/weather")
    public ModelAndView getWeather(ModelAndView model){
        model.setViewName("");

        return model;
    }
}
