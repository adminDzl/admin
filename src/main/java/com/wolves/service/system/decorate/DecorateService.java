package com.wolves.service.system.decorate;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.dao.DaoSupport;
import com.wolves.entity.Page;
import com.wolves.util.PageData;
import org.springframework.stereotype.Service;

@Service("decorateService")
public class DecorateService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd)  {
		dao.save("DecorateMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd)  {
		dao.delete("DecorateMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd)  {
		dao.update("DecorateMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page)  {
		return (List<PageData>)dao.findForList("DecorateMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)  {
		return (List<PageData>)dao.findForList("DecorateMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)  {
		return (PageData)dao.findForObject("DecorateMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)  {
		dao.delete("DecorateMapper.deleteAll", ArrayDATA_IDS);
	}
}

