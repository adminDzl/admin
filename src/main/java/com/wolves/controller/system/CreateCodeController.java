package com.wolves.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wolves.controller.base.BaseController;
import com.wolves.util.DelAllFile;
import com.wolves.util.FileDownload;
import com.wolves.util.FileZip;
import com.wolves.util.Freemarker;
import com.wolves.util.PageData;
import com.wolves.util.PathUtil;

@Controller
@RequestMapping(value="/createCode")
public class CreateCodeController extends BaseController {
	
	/**
	 * 生成代码
	 */
	@RequestMapping(value="/proCode")
	public void proCode(HttpServletResponse response) throws Exception{
		PageData pd = this.getPageData();
		
		String packageName = pd.getString("packageName");
		String objectName = pd.getString("objectName");
		String tabletop = pd.getString("tabletop");
		tabletop = null == tabletop?"":tabletop.toUpperCase();
		String zindext = pd.getString("zindex");
		int zindex = 0;
		if(null != zindext && !"".equals(zindext)){
			zindex = Integer.parseInt(zindext);
		}
		List<String[]> fieldList = new ArrayList<String[]>();
		for(int i=0; i< zindex; i++){
			fieldList.add(pd.getString("field"+i).split(",fh,"));
		}
		
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("fieldList", fieldList);
		root.put("packageName", packageName);
		root.put("objectName", objectName);
		root.put("objectNameLower", objectName.toLowerCase());
		root.put("objectNameUpper", objectName.toUpperCase());
		root.put("tabletop", tabletop);
		root.put("nowDate", new Date());
		
		DelAllFile.delFolder(PathUtil.getClasspath()+"admin/ftl");

		String filePath = "admin/ftl/code/";
		String ftlPath = "createCode";
		
		/*生成controller*/
		Freemarker.printFile("controllerTemplate.ftl", root, "controller/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName+"Controller.java", filePath, ftlPath);
		
		/*生成service*/
		Freemarker.printFile("serviceTemplate.ftl", root, "service/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName+"Service.java", filePath, ftlPath);

		/*生成mybatis xml*/
		Freemarker.printFile("mapperMysqlTemplate.ftl", root, "mybatis_mysql/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
		Freemarker.printFile("mapperOracleTemplate.ftl", root, "mybatis_oracle/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
		
		/*生成SQL脚本*/
		Freemarker.printFile("mysql_SQL_Template.ftl", root, "mysql数据库脚本/"+tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
		Freemarker.printFile("oracle_SQL_Template.ftl", root, "oracle数据库脚本/"+tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
		
		/*生成jsp页面*/
		Freemarker.printFile("jsp_list_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_list.jsp", filePath, ftlPath);
		Freemarker.printFile("jsp_edit_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_edit.jsp", filePath, ftlPath);
		
		/*生成说明文档*/
		Freemarker.printFile("docTemplate.ftl", root, "说明.doc", filePath, ftlPath);
		

		/*生成的全部代码压缩成zip文件*/
		FileZip.zip(PathUtil.getClasspath()+"admin/ftl/code", PathUtil.getClasspath()+"admin/ftl/code.zip");
		
		/*下载代码*/
		FileDownload.fileDownload(response, PathUtil.getClasspath()+"admin/ftl/code.zip", "code.zip");
		
	}
	
}
