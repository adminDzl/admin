package com.wolves.service.system.parkinglot;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.entity.system.Page;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.util.PageData;

@Service("parkinglotService")
public class ParkingLotService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("ParkingLotMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("ParkingLotMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("ParkingLotMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("ParkingLotMapper.datalistPage", page);
	}
	/**
	 *列表
	 */
	public List<PageData> listOwn(Page page){
		return (List<PageData>)dao.findForList("ParkingLotMapper.datalistOwnPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("ParkingLotMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("ParkingLotMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("ParkingLotMapper.deleteAll", ArrayDATA_IDS);
	}
}

