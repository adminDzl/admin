package com.wolves.service.system.room;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.dto.RoomDTO;
import com.wolves.entity.system.Page;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.util.PageData;

@Service("roomService")
public class RoomService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("RoomMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("RoomMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("RoomMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("RoomMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("RoomMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("RoomMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("RoomMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询楼层
	 * @param floormanId
	 * @return
	 */
	public List<RoomDTO> listRoom(String floormanId){

		return (List<RoomDTO>)dao.findForList("RoomMapper.selectRoomByFloor", floormanId);
	}
}

