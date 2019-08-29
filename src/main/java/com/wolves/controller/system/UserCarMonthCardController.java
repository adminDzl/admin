package com.wolves.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.wolves.common.BuildEnum;
import com.wolves.dto.FloorManDTO;
import com.wolves.dto.RoomDTO;
import com.wolves.dto.UserCarMonthCardDTO;
import com.wolves.dto.user.CompanyDTO;
import com.wolves.dto.user.ExportDTO;
import com.wolves.entity.app.PayOrder;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.wolves.controller.base.BaseController;
import com.wolves.entity.system.Page;
import com.wolves.service.system.usercarmonthcard.UserCarMonthCardService;

/**
 * @author gf
 */
@Controller
@RequestMapping(value="/usercarmonthcard")
public class UserCarMonthCardController extends BaseController {

	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "usercarmonthcard/list.do";
	@Resource(name="usercarmonthcardService")
	private UserCarMonthCardService usercarmonthcardService;
	@Resource(name="roomService")
	private RoomService roomService;
	@Resource(name="floormanService")
	private FloorManService floormanService;

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增UserCarMonthCard");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("user_car_month_card_id", this.get32UUID());
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
		usercarmonthcardService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除UserCarMonthCard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = this.getPageData();
		usercarmonthcardService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改UserCarMonthCard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		usercarmonthcardService.edit(pd);
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
		logBefore(logger, "导出UserCarMonthCard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			return;
		}
		PageData pd = this.getPageData();
		String id = pd.getString("user_car_month_card_id");
		ExportDTO exportDTO = usercarmonthcardService.findMonthCardById(id);
		if (exportDTO != null && exportDTO.getId() != null){
			//设置是首次还是第二次
			List<PayOrder> payOrders = usercarmonthcardService.selectPayMonthCard(exportDTO.getId());
			if (payOrders != null){
				if (payOrders.size() > 1){
					exportDTO.setAgain("On");
				}else {
					exportDTO.setFirst("On");
				}
			}
			//设置楼栋
			List<RoomDTO> roomDTOS = roomService.selectRoomByCompanyId(exportDTO.getCompanyId());
			if (roomDTOS != null && !roomDTOS.isEmpty()){
				StringBuilder stringBuffer = new StringBuilder();
				for (RoomDTO roomDTO : roomDTOS){
					stringBuffer.append(roomDTO.getName()).append(",");
				}
				String floormanId =  roomDTOS.get(0).getFloormanId();
				FloorManDTO floorManDTO = floormanService.selectFloorManById(floormanId);
				if (floorManDTO != null){
					if (BuildEnum.south.getName().equals(floorManDTO.getBuildNo()) ||
							BuildEnum.north.getName().equals(floorManDTO.getBuildNo())){
						exportDTO.setHouse("On");
					}else {
						exportDTO.setBuild("On");
					}
					exportDTO.setBuilding(floorManDTO.getFloor());
				}
				exportDTO.setFloor(stringBuffer.toString().substring(0, stringBuffer.toString().length()-1));
			}

		}
		try {
			PdfUtil.setPdfData(response, "https://adminwolves.oss-cn-beijing.aliyuncs.com/admin/1556341521919.pdf", "停车场业务申请", exportDTO);
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
		logBefore(logger, "列表UserCarMonthCard");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = usercarmonthcardService.list(page);
		mv.setViewName("system/usercarmonthcard/usercarmonthcard_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
//		mv.addObject("field1", pd.getString("field1"));
//		mv.addObject("field2", pd.getString("field2"));
		mv.addObject(Const.SESSION_QX,this.getHC());
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增UserCarMonthCard页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/usercarmonthcard/usercarmonthcard_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改UserCarMonthCard页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = usercarmonthcardService.findById(pd);
		mv.setViewName("system/usercarmonthcard/usercarmonthcard_edit");
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
		logBefore(logger, "批量删除UserCarMonthCard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;}
		PageData pd = null;
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			usercarmonthcardService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping(value="/importMonthCardUserExcel")
	@ResponseBody
	public Object importMonthCardUserExcel(@RequestParam(value="uploadFile") MultipartFile file){
		logger.info("客户数据excel导入-->/importMonthCardUserExcel");
		PageData pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>(10);
		List<PageData> pdList = new ArrayList<PageData>();
		ImportExcelUtil importExcelUtil = new ImportExcelUtil();
		List<Map<String, Object>> monthCard = importExcelUtil.getExcelInfo(file);
		if (monthCard != null && !monthCard.isEmpty() && monthCard.size() < 50000){
			pd = usercarmonthcardService.checkExcelData(monthCard, pd);
			String status = pd.get("status").toString();
			if (status.equals("0")){
				List<UserCarMonthCardDTO> userCarMonthCardDTOS = usercarmonthcardService.getCompanyData(monthCard);
				usercarmonthcardService.createMonthCard(userCarMonthCardDTOS);
				pd.put("msg", "ok");
			}
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
		logBefore(logger, "导出UserCarMonthCard到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		PageData pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户ID");
		titles.add("月卡号");
		titles.add("金额");
		titles.add("有效日期");
		titles.add("月卡状态");
		titles.add("创建时间");
		titles.add("更新时间");
		dataMap.put("titles", titles);
		List<PageData> varOList = usercarmonthcardService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("USER_ID"));
			vpd.put("var2", varOList.get(i).getString("CARD_NO"));
			vpd.put("var3", varOList.get(i).getString("PRICE"));
			vpd.put("var4", varOList.get(i).getString("USE_TIL_DATE"));
			vpd.put("var5", varOList.get(i).get("CARD_STATUS").toString());
			vpd.put("var6", varOList.get(i).getString("CREATE_TIME"));
			vpd.put("var7", varOList.get(i).getString("UPDATE_TIME"));
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