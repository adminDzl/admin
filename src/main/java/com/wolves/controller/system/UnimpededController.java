package com.wolves.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.DocumentException;
import com.wolves.common.BuildEnum;
import com.wolves.common.Constants;
import com.wolves.common.StatusEnum;
import com.wolves.controller.base.BaseController;
import com.wolves.dto.FloorManDTO;
import com.wolves.dto.RoomDTO;
import com.wolves.dto.user.UnionDTO;
import com.wolves.entity.system.Page;
import com.wolves.service.system.decorate.DecorateService;
import com.wolves.service.system.floorman.FloorManService;
import com.wolves.service.system.room.RoomService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    @Resource(name="roomService")
    private RoomService roomService;
    @Resource(name="floormanService")
    private FloorManService floormanService;

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
        pd.put("STATUS", StatusEnum.INIT.getKey());
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
     * pdf导出
     * @param response
     * @param out
     */
    @RequestMapping(value="/exportPdf")
    @ResponseBody
    public void exportPdf(HttpServletResponse response, PrintWriter out){
        logBefore(logger, "导出unimpeded");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
            return;
        }
        PageData pd = this.getPageData();
        pd = decorateService.findById(pd);

        UnionDTO unionDTO = decorateService.findUnionById(pd.getString("DECORATE_ID"));
        if (unionDTO != null) {
            //设置单位类型
            String status = unionDTO.getStatus();
            if (StatusEnum.SUCCESS.getKey().equals(status)){
                unionDTO.setEnter("On");
            }else if (StatusEnum.INIT.getKey().equals(status)){
                unionDTO.setWaitEnter("On");
            }else {
                unionDTO.setNon("On");
            }
            //设置男女
            String sex = unionDTO.getSex();
            if (sex.equals(Constants.woman)){
                unionDTO.setWoman("On");
            }else {
                unionDTO.setMan("On");
            }
            //设置申请业务
            unionDTO.setFresh("On");
            //设置楼栋
            List<RoomDTO> roomDTOS = roomService.selectRoomByCompanyId(unionDTO.getCompanyId());
            if (roomDTOS != null && !roomDTOS.isEmpty()) {
                StringBuilder stringBuffer = new StringBuilder();
                for (RoomDTO roomDTO : roomDTOS) {
                    stringBuffer.append(roomDTO.getName()).append(",");
                }
                String floormanId = roomDTOS.get(0).getFloormanId();
                System.out.println("floormanId:"+floormanId);
                FloorManDTO floorManDTO = floormanService.selectFloorManById(floormanId);
                if (floorManDTO != null) {
                    if (BuildEnum.south.getName().equals(floorManDTO.getBuildNo()) ||
                            BuildEnum.north.getName().equals(floorManDTO.getBuildNo())) {
                        unionDTO.setHouse("On");
                    } else {
                        unionDTO.setBuild("On");
                    }
                    unionDTO.setBuilding(floorManDTO.getFloor());
                }
                unionDTO.setFloor(stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1));
            }
        }
        try {
            PdfUtil.setPdfData(response, "https://adminwolves.oss-cn-beijing.aliyuncs.com/admin/1564040859151.pdf", "一卡通申请", unionDTO);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        out.write("success");
        out.close();
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
        mv.setViewName("system/decorate/unimpeded_edit");
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
        mv.setViewName("system/decorate/unimpeded_edit");
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
        mv.setViewName("system/decorate/unimpeded_check");
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
