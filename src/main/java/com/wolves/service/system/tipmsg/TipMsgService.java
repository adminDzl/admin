package com.wolves.service.system.tipmsg;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.dto.user.TipMsgDTO;
import com.wolves.dto.user.ToMsgDTO;
import com.wolves.util.UuidUtil;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("tipmsgService")
public class TipMsgService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("TipMsgMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("TipMsgMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("TipMsgMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("TipMsgMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("TipMsgMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("TipMsgMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("TipMsgMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询最近的三条消息
	 */
	public List<PageData> getMsgByUser(PageData pd){

		return (List<PageData>) dao.findForList("TipMsgMapper.getMsgByUser", pd);
	}

	/**
	 *  查询列表
	 * @param params
	 * @return
	 */
	public List<TipMsgDTO> selectTipMsg(Map<String, Object> params){
		return (List<TipMsgDTO>) dao.findForList("TipMsgMapper.selectTipMsg", params);
	}

	/**
	 * 查询详情
	 * @param tipMsgId
	 * @return
	 */
	public TipMsgDTO selectTipMsgById(String tipMsgId){

		return (TipMsgDTO) dao.findForObject("TipMsgMapper.selectTipMsgById", tipMsgId);
	}

	/**
	 * 添加单条信息
	 * @param toMsgDTO
	 */
	public void createTipMsg(ToMsgDTO toMsgDTO){
		toMsgDTO.setTipmsgId(UuidUtil.get32UUID());
		dao.save("TipMsgMapper.saveTipMsg", toMsgDTO);
	}

	/**
	 * 添加多条信息
	 * @param toMsgDTOs 信息内容
	 */
	public void addTipMsg(List<ToMsgDTO> toMsgDTOs){
		if (toMsgDTOs != null && !toMsgDTOs.isEmpty()){
			for (ToMsgDTO toMsgDTO : toMsgDTOs){
				toMsgDTO.setTipmsgId(UuidUtil.get32UUID());
				dao.save("TipMsgMapper.saveTipMsg", toMsgDTO);
			}
		}
	}

}

