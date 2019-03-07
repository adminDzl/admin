package com.wolves.service.system.newstip;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;
import org.springframework.stereotype.Service;

@Service("newstipService")
public class NewsTipService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd) {
		dao.save("NewsTipMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd) {
		dao.delete("NewsTipMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd) {
		dao.update("NewsTipMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page) {
		return (List<PageData>)dao.findForList("NewsTipMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd) {
		return (List<PageData>)dao.findForList("NewsTipMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd) {
		return (PageData)dao.findForObject("NewsTipMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS) {
		dao.delete("NewsTipMapper.deleteAll", ArrayDATA_IDS);
	}
}

