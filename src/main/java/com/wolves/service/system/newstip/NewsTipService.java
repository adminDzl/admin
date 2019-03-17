package com.wolves.service.system.newstip;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.dao.DaoSupport;
import com.wolves.dto.NewsTipDTO;
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
	* 新闻列表
	*/
	public List<PageData> list(Page page) {
		return (List<PageData>)dao.findForList("NewsTipMapper.datalistPage", page);
	}

	/**
	 * 项目申报
	 * @return
	 */
	public List<PageData> declarelistPage(Page page){
		return (List<PageData>)dao.findForList("NewsTipMapper.declarelistPage", page);
	}
	
	/**
	*列表(全部新闻)
	*/
	public List<PageData> listNewAll(PageData pd) {
		return (List<PageData>)dao.findForList("NewsTipMapper.listNewAll", pd);
	}

	/**
	 *列表(全部项目)
	 */
	public List<PageData> listDeclareAll(PageData pd) {
		return (List<PageData>)dao.findForList("NewsTipMapper.listDeclareAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd) {
		return (PageData)dao.findForObject("NewsTipMapper.findById", pd);
	}
	
	/**
	* 新闻批量删除
	*/
	public void deleteNewAll(String[] ArrayDATA_IDS) {
		dao.delete("NewsTipMapper.deleteNewAll", ArrayDATA_IDS);
	}

	/**
	 * 项目申报批量删除
	 */
	public void deleteDeclareAll(String[] ArrayDATA_IDS) {
		dao.delete("NewsTipMapper.deleteDeclareAll", ArrayDATA_IDS);
	}

	/**
	 * 查询
	 * @param params
	 * @return
	 */
	public List<NewsTipDTO> selectNewsByType(Map<String,Object> params){
		return (List<NewsTipDTO>)dao.findForList("NewsTipMapper.selectNewsByType", params);
	}

	/**
	 * 根据id查询
	 * @param newstipId
	 * @return
	 */
	public NewsTipDTO selectNewsById(String newstipId){

		return (NewsTipDTO)dao.findForObject("NewsTipMapper.selectNewsById", newstipId);
	}
}

