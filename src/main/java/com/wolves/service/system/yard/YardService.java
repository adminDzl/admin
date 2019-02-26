package com.wolves.service.system.yard;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.dao.DaoSupport;
import com.wolves.entity.Page;
import com.wolves.util.PageData;
import org.springframework.stereotype.Service;

@Service("yardService")
public class YardService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("YardMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("YardMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("YardMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("YardMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("YardMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("YardMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("YardMapper.deleteAll", ArrayDATA_IDS);
	}
}
