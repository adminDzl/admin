package com.wolves.service.system.menu;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Menu;
import com.wolves.util.PageData;

@Service("menuService")
public class MenuService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public void deleteMenuById(String MENU_ID)   {
		dao.save("MenuMapper.deleteMenuById", MENU_ID);
		
	}

	public PageData getMenuById(PageData pd)   {
		return (PageData) dao.findForObject("MenuMapper.getMenuById", pd);
		
	}

	//取最大id
	public PageData findMaxId(PageData pd)   {
		return (PageData) dao.findForObject("MenuMapper.findMaxId", pd);
		
	}
	
	public List<Menu> listAllParentMenu()   {
		return (List<Menu>) dao.findForList("MenuMapper.listAllParentMenu", null);
		
	}

	public void saveMenu(Menu menu)   {
		if(menu.getMENU_ID()!=null && menu.getMENU_ID() != ""){
			//dao.update("MenuMapper.updateMenu", menu);
			dao.save("MenuMapper.insertMenu", menu);
		}else{
			dao.save("MenuMapper.insertMenu", menu);
		}
	}

	public List<Menu> listSubMenuByParentId(String parentId)   {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", parentId);
		
	}
		
	public List<Menu> listAllMenu()   {
		List<Menu> rl = this.listAllParentMenu();
		for(Menu menu : rl){
			List<Menu> subList = this.listSubMenuByParentId(menu.getMENU_ID());
			menu.setSubMenu(subList);
		}
		return rl;
	}

	public List<Menu> listAllSubMenu()  {
		return (List<Menu>) dao.findForList("MenuMapper.listAllSubMenu", null);
		
	}
	
	/**
	 * 编辑
	 */
	public PageData edit(PageData pd)   {
		return (PageData)dao.findForObject("MenuMapper.updateMenu", pd);
	}
	/**
	 * 保存菜单图标 (顶部菜单)
	 */
	public PageData editicon(PageData pd)   {
		return (PageData)dao.findForObject("MenuMapper.editicon", pd);
	}
	
	/**
	 * 更新子菜单类型菜单
	 */
	public PageData editType(PageData pd)   {
		return (PageData)dao.findForObject("MenuMapper.editType", pd);
	}
}
