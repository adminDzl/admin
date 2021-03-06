package com.wolves.service.system.parking;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.dto.UserParkingDTO;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("parkingService")
public class ParkingService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("ParkingMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("ParkingMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("ParkingMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("ParkingMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("ParkingMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("ParkingMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("ParkingMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询停车记录
	 * @param params
	 * @return
	 */
	public List<UserParkingDTO> selectParking(Map<String,Object> params){

		return (List<UserParkingDTO>) dao.findForList("ParkingMapper.selectParking", params);
	}
}

