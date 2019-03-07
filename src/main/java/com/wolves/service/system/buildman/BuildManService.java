package com.wolves.service.system.buildman;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("buildmanService")
public class BuildManService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd) {
		dao.save("BuildManMapper.save", pd);
	}

	/**
	* 删除
	*/
	public void delete(PageData pd) {
		dao.delete("BuildManMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd) {
		dao.update("BuildManMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page) {
		return (List<PageData>)dao.findForList("BuildManMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd) {
		return (List<PageData>)dao.findForList("BuildManMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd) {
		return (PageData)dao.findForObject("BuildManMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS) {
		dao.delete("BuildManMapper.deleteAll", ArrayDATA_IDS);
	}
}

