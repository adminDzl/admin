package com.wolves.service.system.payment;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.dto.pay.PayMentDTO;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("paymentService")
public class PaymentService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("PaymentMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("PaymentMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("PaymentMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("PaymentMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("PaymentMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("PaymentMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("PaymentMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询缴费记录
	 * @param companyId
	 * @return
	 */
	public List<PayMentDTO> selectPayMentById(String companyId){

		return (List<PayMentDTO>)dao.findForList("PaymentMapper.selectPayMentById", companyId);
	}
}

