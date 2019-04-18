package com.wolves.controller.system;

import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.Page;
import com.wolves.service.system.decorate.DecorateService;
import com.wolves.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 一卡通申请
 * @author gf
 * @date 2019/4/18
 */
@Controller
@RequestMapping(value="/unimpeded")
public class UnimpededController extends BaseController {

    /**
     * 菜单地址(权限用)
     */
    String menuUrl = "unimpeded/list.do";
    @Resource(name="decorateService")
    private DecorateService decorateService;

    /**
     * 新增
     */
    @RequestMapping(value="/save")
    public ModelAndView save() throws Exception{
        logBefore(logger, "新增unimpeded");
        //校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd.put("DECORATE_ID", this.get32UUID());
        pd.put("STATUS", "0");
        pd.put("CREATE_TIME", Tools.date2Str(new Date()));
        pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
        decorateService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除unimpeded");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
            return;
        }
        PageData pd = this.getPageData();
        decorateService.delete(pd);
        out.write("success");
        out.close();
    }

    /**
     * 修改
     */
    @RequestMapping(value="/edit")
    public ModelAndView edit() throws Exception{
        logBefore(logger, "修改unimpeded");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        decorateService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "列表unimpeded");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        List<PageData> varList = decorateService.passList(page);
        mv.setViewName("system/decorate/unimpeded_list");
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX,this.getHC());
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value="/goAdd")
    public ModelAndView goAdd(){
        logBefore(logger, "去新增unimpeded页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        mv.setViewName("system/decorate/decorate_edit");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(){
        logBefore(logger, "去修改unimpeded页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd = decorateService.findById(pd);
        mv.setViewName("system/decorate/decorate_edit");
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 审核页面
     * @return
     */
    @RequestMapping(value="/goCheck")
    public ModelAndView goCheck(){
        logBefore(logger, "去审核unimpeded页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd = decorateService.findById(pd);
        mv.setViewName("system/decorate/decorate_check");
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 批量删除
     */
    @RequestMapping(value="/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除unimpeded");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
        PageData pd = null;
        Map<String,Object> map = new HashMap<String,Object>();
        pd = this.getPageData();
        List<PageData> pdList = new ArrayList<PageData>();
        String DATA_IDS = pd.getString("DATA_IDS");
        if(null != DATA_IDS && !"".equals(DATA_IDS)){
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            decorateService.deleteAll(ArrayDATA_IDS);
            pd.put("msg", "ok");
        }else{
            pd.put("msg", "no");
        }
        pdList.add(pd);
        map.put("list", pdList);
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 导出到excel
     * @return
     */
    @RequestMapping(value="/excel")
    public ModelAndView exportExcel(){
        logBefore(logger, "导出unimpeded到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("编号");
        titles.add("申请标题");
        titles.add("申请内容");
        titles.add("审核状态");
        titles.add("创建时间");
        titles.add("更新时间");
        dataMap.put("titles", titles);
        List<PageData> varOList = decorateService.listAll(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for(int i=0;i<varOList.size();i++){
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("DECORATE_NO"));
            vpd.put("var2", varOList.get(i).getString("TITLE"));
            vpd.put("var3", varOList.get(i).getString("CONTENT"));
            vpd.put("var4", varOList.get(i).get("STATUS").toString());
            vpd.put("var5", varOList.get(i).getString("CREATE_TIME"));
            vpd.put("var6", varOList.get(i).getString("UPDATE_TIME"));
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        return new ModelAndView(erv,dataMap);
    }

    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
    }
}
