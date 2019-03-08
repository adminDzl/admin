package com.wolves.service.system.floorman;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.dto.FloorManAllDTO;
import com.wolves.dto.FloorManDTO;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("floormanService")
public class FloorManService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd) {
		dao.save("FloorManMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd) {
		dao.delete("FloorManMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd) {
		dao.update("FloorManMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page) {
		return (List<PageData>)dao.findForList("FloorManMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd) {
		return (List<PageData>)dao.findForList("FloorManMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd) {
		return (PageData)dao.findForObject("FloorManMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS) {
		dao.delete("FloorManMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询客服列表
	 */
	public List<FloorManDTO> getFloorMan(){
		return (List<FloorManDTO>) dao.findForList("FloorManMapper.selectFloorMan", null);
	}

	/**
	 * 查询楼层
	 */
	public List<FloorManAllDTO> getAllFloorMan(String buildNo){

		return (List<FloorManAllDTO>) dao.findForList("FloorManMapper.selectFloorManByBuildNo", buildNo);
	}
}

