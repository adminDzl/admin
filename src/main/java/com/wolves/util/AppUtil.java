package com.wolves.util;

import java.util.Map;
import org.codehaus.jackson.map.util.JSONPObject;

public class AppUtil  {
	
	protected static Logger logger = Logger.getLogger(AppUtil.class);
	
	public static void main(String[] args) {
		PageData pd = new PageData();
		pd.put("username", "zhangsan");
		
		checkParam("registered", pd);
	}
	//检查参数是否完整
	public static boolean checkParam(String method, PageData pd){
		boolean result = false;
		
		int falseCount = 0;
		String[] paramArray = new String[20];
		String[] valueArray = new String[20];
		String[] tempArray  = new String[20];
		
		if("registered".equals(method)){
			paramArray = Const.APP_REGISTERED_PARAM_ARRAY;
			valueArray = Const.APP_REGISTERED_VALUE_ARRAY;
		}else if("getAppuserByUsernmae".equals(method)){
			//根据用户名获取会员信息
			paramArray = Const.APP_GETAPPUSER_PARAM_ARRAY;  
			valueArray = Const.APP_GETAPPUSER_VALUE_ARRAY;
		}
		int size = paramArray.length;
		for(int i=0;i<size;i++){
			String param = paramArray[i];
			if(!pd.containsKey(param)){
				tempArray[falseCount] = valueArray[i]+"--"+param;
				falseCount += 1;
			}
		}
		if(falseCount>0){
			logger.error(method+"接口，请求协议中缺少 "+falseCount+"个 参数");
			for(int j=1;j<=falseCount;j++){
				logger.error("   第"+j+"个："+ tempArray[j-1]);
			}
		} else {
			result = true;
		}
		return result;
	}
	
	/**
	 * 设置分页的参数
	 * @param pd
	 * @return
	 */
	public static PageData setPageParam(PageData pd){
		String page_now_str = pd.get("page_now").toString();
		int pageNowInt = Integer.parseInt(page_now_str)-1;
		//每页显示记录数
		String page_size_str = pd.get("page_size").toString();
		int pageSizeInt = Integer.parseInt(page_size_str);
		
		String page_now = pageNowInt+"";
		String page_start = (pageNowInt*pageSizeInt)+"";
		
		pd.put("page_now", page_now);
		pd.put("page_start", page_start);
		
		return pd;
	}
	
	public static Object returnObject(PageData pd, Map map){
		if(pd.containsKey("callback")){
			String callback = pd.get("callback").toString();
			return new JSONPObject(callback, map);
		}else{
			return map;
		}
	}

	/**
	 * 去除html代码中含有的标签
	 * @param htmlStr
	 * @return
	 */
	public static String delHtmlTags(String htmlStr) {
		//定义script的正则表达式，去除js可以防止注入
		String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
		//定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
		String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
		//定义HTML标签的正则表达式，去除标签，只提取文字内容
		String htmlRegex="<[^>]+>";
		//定义空格,回车,换行符,制表符
		String spaceRegex = "\\s*|\t|\r|\n";

		// 过滤script标签
		htmlStr = htmlStr.replaceAll(scriptRegex, "");
		// 过滤style标签
		htmlStr = htmlStr.replaceAll(styleRegex, "");
		// 过滤html标签
		htmlStr = htmlStr.replaceAll(htmlRegex, "");
		// 过滤空格等
		htmlStr = htmlStr.replaceAll(spaceRegex, "");
		return htmlStr.trim(); // 返回文本字符串
	}
	/**
	 * 获取HTML代码里的内容
	 * @param htmlStr
	 * @return
	 */
	public static String getTextFromHtml(String htmlStr){
		//去除html标签
		htmlStr = delHtmlTags(htmlStr);
		//去除空格" "
		htmlStr = htmlStr.replaceAll(" ","");
		return htmlStr;
	}
}
