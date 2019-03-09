package com.wolves.service.system.yard;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.dao.DaoSupport;
import com.wolves.dto.YardDTO;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;
import org.springframework.stereotype.Service;

@Service("yardService")
public class YardService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("YardMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("YardMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("YardMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("YardMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("YardMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("YardMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("YardMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询场地分页
	 * @param params
	 * @return
	 */
	public List<YardDTO> selectYard(Map<String, Object> params){
		return (List<YardDTO>) dao.findForList("YardMapper.selectYard", params);
	}
}

