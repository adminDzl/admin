package com.wolves.controller.system;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import com.wolves.entity.Page;
import com.wolves.service.system.PicturesService;

/** 
 * 类名称：PicturesController
 */
@Controller
@RequestMapping(value="/pictures")
public class PicturesController extends BaseController {
	
	String menuUrl = "pictures/list.do"; //菜单地址(权限用)
	@Resource(name="picturesService")
	private PicturesService picturesService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save( @RequestParam(required=false) MultipartFile file ) throws Exception{
		logBefore(logger, "新增Pictures");
		Map<String,String> map = new HashMap<String,String>();
		String  ffile = DateUtil.getDays(), fileName = "";
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			if (null != file && !file.isEmpty()) {
				//文件上传路径
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;
				//执行上传
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());
			}else{
				System.out.println("上传失败");
			}
			//主键
			pd.put("PICTURES_ID", this.get32UUID());
			//标题
			pd.put("TITLE", "图片");
			//文件名
			pd.put("NAME", fileName);
			//路径
			pd.put("PATH", ffile + "/" + fileName);
			//创建时间
			pd.put("CREATETIME", Tools.date2Str(new Date()));
			//附属与
			pd.put("MASTER_ID", "1");
			//备注
			pd.put("BZ", "图片管理处上传");
			//加水印
			picturesService.save(pd);
		}
		map.put("result", "ok");
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Pictures");
		PageData pd;
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			pd = this.getPageData();
			DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + pd.getString("PATH"));
			picturesService.delete(pd);
		}
		out.write("success");
		out.close();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			HttpServletRequest request,
			@RequestParam(value="tp",required=false) MultipartFile file,
			@RequestParam(value="tpz",required=false) String tpz,
			@RequestParam(value="PICTURES_ID",required=false) String PICTURES_ID,
			@RequestParam(value="TITLE",required=false) String TITLE,
			@RequestParam(value="MASTER_ID",required=false) String MASTER_ID,
			@RequestParam(value="BZ",required=false) String BZ
			) throws Exception{
		logBefore(logger, "修改Pictures");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("PICTURES_ID", PICTURES_ID);
			pd.put("TITLE", TITLE);
			pd.put("MASTER_ID", MASTER_ID);
			pd.put("BZ", BZ);
			
			if(null == tpz){tpz = "";}
			String  ffile = DateUtil.getDays(), fileName = "";
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());
				pd.put("PATH", ffile + "/" + fileName);
				pd.put("NAME", fileName);
			}else{
				pd.put("PATH", tpz);
			}
			picturesService.edit(pd);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Pictures");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String KEYW = pd.getString("keyword");
		if(null != KEYW && !"".equals(KEYW)){
			KEYW = KEYW.trim();
			pd.put("KEYW", KEYW);
		}
		page.setPd(pd);
		List<PageData>	varList = picturesService.list(page);
		mv.setViewName("information/pictures/pictures_list");
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
		logBefore(logger, "去新增Pictures页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("information/pictures/pictures_add");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Pictures页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = picturesService.findById(pd);
		mv.setViewName("information/pictures/pictures_edit");
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
		logBefore(logger, "批量删除Pictures");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				List<PageData> pdList = new ArrayList<PageData>();
				List<PageData> pathList = new ArrayList<PageData>();
				String DATA_IDS = pd.getString("DATA_IDS");
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					pathList = picturesService.getAllById(ArrayDATA_IDS);
					//删除图片
					for(int i=0;i<pathList.size();i++){
						DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + pathList.get(i).getString("PATH"));
					}
					picturesService.deleteAll(ArrayDATA_IDS);
					pd.put("msg", "ok");
				}else{
					pd.put("msg", "no");
				}
				pdList.add(pd);
				map.put("list", pdList);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Pictures到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("标题");
			titles.add("文件名");
			titles.add("路径");
			titles.add("创建时间");
			titles.add("属于");
			titles.add("备注");
			dataMap.put("titles", titles);
			List<PageData> varOList = picturesService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TITLE"));
				vpd.put("var2", varOList.get(i).getString("NAME"));
				vpd.put("var3", varOList.get(i).getString("PATH"));
				vpd.put("var4", varOList.get(i).getString("CREATETIME"));
				vpd.put("var5", varOList.get(i).getString("MASTER_ID"));
				vpd.put("var6", varOList.get(i).getString("BZ"));
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	//删除图片
	@RequestMapping(value="/deltp")
	public void deltp(PrintWriter out) {
		logBefore(logger, "删除图片");
		try{
			PageData pd = this.getPageData();
			String PATH = pd.getString("PATH");
			DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + pd.getString("PATH"));
			if(PATH != null){
				picturesService.delTp(pd);
			}	
			out.write("success");
			out.close();
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
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
