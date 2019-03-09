package com.wolves.controller.system;

import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.Page;
import com.wolves.service.system.newstip.NewsTipService;
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
 * 项目申报
 * @author gf
 * @date 2019/3/9
 */
@Controller
@RequestMapping(value="/declare")
public class NewsDeclareController extends BaseController {

    /**
     * 菜单地址(权限用)
     */
    String menuUrl = "declare/list.do";
    @Resource(name="newstipService")
    private NewsTipService newstipService;

    /**
     * 新增
     */
    @RequestMapping(value="/save")
    public ModelAndView save() throws Exception{
        logBefore(logger, "新增declare");
        //校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        //主键
        pd.put("NEWSTIP_ID", this.get32UUID());
        pd.put("STATUS", 1);
        newstipService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除declare");
        //校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
        PageData pd = this.getPageData();
        newstipService.delete(pd);
        out.write("success");
        out.close();
    }

    /**
     * 修改
     */
    @RequestMapping(value="/edit")
    public ModelAndView edit() throws Exception{
        logBefore(logger, "修改declare");
        //校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd.put("STATUS", 1);
        newstipService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 查询项目申报列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "列表NewsTip");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        List<PageData> varList = newstipService.declarelistPage(page);
        mv.setViewName("system/newstip/newsdeclare_list");
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
        logBefore(logger, "去新增NewsTip页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        mv.setViewName("system/newstip/newsdeclare_edit");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(){
        logBefore(logger, "去修改NewsTip页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd = newstipService.findById(pd);
        mv.setViewName("system/newstip/newsdeclare_edit");
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
        logBefore(logger, "批量删除NewsTip");
        //校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        pd = this.getPageData();
        List<PageData> pdList = new ArrayList<PageData>();
        String DATA_IDS = pd.getString("DATA_IDS");
        if(null != DATA_IDS && !"".equals(DATA_IDS)){
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            newstipService.deleteDeclareAll(ArrayDATA_IDS);
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
        logBefore(logger, "导出NewsTip到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("id");
        titles.add("新闻类型（1.新闻 2.项目申报）");
        titles.add("新闻标题");
        titles.add("新闻内容");
        titles.add("附件url");
        titles.add("创建时间");
        titles.add("修改时间");
        dataMap.put("titles", titles);
        List<PageData> varOList = newstipService.listDeclareAll(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for(int i=0;i<varOList.size();i++){
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).get("ID").toString());
            vpd.put("var2", varOList.get(i).get("NEWS_TYPE").toString());
            vpd.put("var3", varOList.get(i).getString("NEWS_TITLE"));
            vpd.put("var4", varOList.get(i).getString("NEWS_CONTENT"));
            vpd.put("var5", varOList.get(i).getString("ATTACH_URL"));
            vpd.put("var6", varOList.get(i).getString("CREATE_TIME"));
            vpd.put("var7", varOList.get(i).getString("UPDATE_TIME"));
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        return new ModelAndView(erv,dataMap);
    }

    public Map<String, String> getHC(){
        //shiro管理的session
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