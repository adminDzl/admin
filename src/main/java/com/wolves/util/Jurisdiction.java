package com.wolves.util;

import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import com.wolves.entity.system.Menu;

/**
 * 权限处理
*/
public class Jurisdiction {

	/**
	 * 访问权限及初始化按钮权限(控制按钮的显示)
	 */
	public static boolean hasJurisdiction(String menuUrl){
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		Boolean b = true;
		List<Menu> menuList = (List)session.getAttribute(Const.SESSION_allmenuList);
		
		for(int i=0;i<menuList.size();i++){
			for(int j=0;j<menuList.get(i).getSubMenu().size();j++){
				if(menuList.get(i).getSubMenu().get(j).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){
					//判断有无此菜单权限
					if(!menuList.get(i).getSubMenu().get(j).isHasMenu()){
						return false;
					}else{
						//按钮权限
						Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);
						map.remove("add");
						map.remove("del");
						map.remove("edit");
						map.remove("cha");
						String MENU_ID =  menuList.get(i).getSubMenu().get(j).getMENU_ID();
						//获取当前登录者loginname
						String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();
						Boolean isAdmin = "admin".equals(USERNAME);
						map.put("add", (RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin?"1":"0");
						map.put("del", RightsHelper.testRights(map.get("dels"), MENU_ID) || isAdmin?"1":"0");
						map.put("edit", RightsHelper.testRights(map.get("edits"), MENU_ID) || isAdmin?"1":"0");
						map.put("cha", RightsHelper.testRights(map.get("chas"), MENU_ID) || isAdmin?"1":"0");
						session.removeAttribute(Const.SESSION_QX);
						//重新分配按钮权限
						session.setAttribute(Const.SESSION_QX, map);
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 按钮权限(方法中校验)
	 * @param menuUrl  菜单路径
	 * @param type  类型(add、del、edit、cha)
	 * @return
	 */
	public static boolean buttonJurisdiction(String menuUrl, String type){
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		Boolean b = true;
		List<Menu> menuList = (List)session.getAttribute(Const.SESSION_allmenuList);
		
		for(int i=0;i<menuList.size();i++){
			for(int j=0;j<menuList.get(i).getSubMenu().size();j++){
				if(menuList.get(i).getSubMenu().get(j).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){
					//判断有无此菜单权限
					if(!menuList.get(i).getSubMenu().get(j).isHasMenu()){
						return false;
					}else{
						Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);
						String MENU_ID =  menuList.get(i).getSubMenu().get(j).getMENU_ID();
						String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();
						Boolean isAdmin = "admin".equals(USERNAME);
						if("add".equals(type)){
							return ((RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin);
						}else if("del".equals(type)){
							return ((RightsHelper.testRights(map.get("dels"), MENU_ID)) || isAdmin);
						}else if("edit".equals(type)){
							return ((RightsHelper.testRights(map.get("edits"), MENU_ID)) || isAdmin);
						}else if("cha".equals(type)){
							return ((RightsHelper.testRights(map.get("chas"), MENU_ID)) || isAdmin);
						}
					}
				}
			}
		}
		return true;
	}
}