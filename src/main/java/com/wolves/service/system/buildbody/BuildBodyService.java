package com.wolves.service.system.buildbody;

import java.util.List;
import javax.annotation.Resource;
import com.wolves.entity.system.Page;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.util.PageData;

@Service("buildBodyService")
public class BuildBodyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("BuildBodyMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("BuildBodyMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("BuildBodyMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("BuildBodyMapper.datalistPage", page);
	}

	/**
	 *列表
	 */
	public List<PageData> getByBuildId(String buildId){
		PageData pd = new PageData();
		pd.put("build_man_id", buildId);
		return (List<PageData>)dao.findForList("BuildBodyMapper.getByBuildId", pd);
	}

	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("BuildBodyMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("BuildBodyMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("BuildBodyMapper.deleteAll", ArrayDATA_IDS);
	}
}

