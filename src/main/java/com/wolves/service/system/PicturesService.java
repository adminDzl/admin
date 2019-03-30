package com.wolves.service.system;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.dto.PictureDTO;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("picturesService")
public class PicturesService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd){
		dao.save("PicturesMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("PicturesMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("PicturesMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("PicturesMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("PicturesMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("PicturesMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("PicturesMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 批量获取
	*/
	public List<PageData> getAllById(String[] ArrayDATA_IDS){
		return (List<PageData>)dao.findForList("PicturesMapper.getAllById", ArrayDATA_IDS);
	}
	
	/*
	* 删除图片
	*/
	public void delTp(PageData pd){
		dao.update("PicturesMapper.delTp", pd);
	}

	/**
	 * 查询轮播列表
	 * @return
	 */
	public List<PictureDTO> selectPicture(){

		return (List<PictureDTO>) dao.findForList("PicturesMapper.selectPicture", null);
	}
	
}

