package com.wolves.service.system.repair;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.dto.user.RepairApplyDTO;
import com.wolves.dto.user.RepairApplyDetailDTO;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("repairApplyService")
public class RepairApplyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("RepairApplyMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("RepairApplyMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("RepairApplyMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("RepairApplyMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("RepairApplyMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("RepairApplyMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("RepairApplyMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询报修列表
	 * @param params
	 * @return
	 */
	public List<RepairApplyDTO> selectRepairApplyByUserId(Map<String, Object> params){
		return (List<RepairApplyDTO>) dao.findForList("RepairApplyMapper.selectRepairApplyByUserId", params);
	}

	/**
	 *  查询报修详情
	 * @param repairApplyId
	 * @return
	 */
	public RepairApplyDetailDTO selectRepairApplyById(String repairApplyId){
		return (RepairApplyDetailDTO)dao.findForObject("RepairApplyMapper.selectRepairApplyById", repairApplyId);
	}
}

