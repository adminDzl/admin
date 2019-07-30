package com.wolves.service.system.payorder;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.dto.pay.CompantYearPayDTO;
import com.wolves.entity.app.PayOrder;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("payorderService")
public class PayOrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("PayOrderMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("PayOrderMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("PayOrderMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("PayOrderMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("PayOrderMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("PayOrderMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("PayOrderMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询付费记录，通过客户id
	 * @param params
	 * @return
	 */
	public List<PayOrder> selectPayOrderByUserId(Map<String, Object> params){

		return (List<PayOrder>)dao.findForList("PayOrderMapper.selectPayOrderByUserId", params);
	}

    /**
     * 查询一家公司全部已经缴纳费用
     * @param params
     * @return
     */
    public PageData selectAllAmount(Map<String,Object> params){

        return (PageData)dao.findForObject("PayOrderMapper.selectAllAmount", params);
    }

	/**
	 * 查询全部收入
	 * @return
	 */
	public PageData selectPayAmount(){

		return (PageData)dao.findForObject("PayOrderMapper.selectPayAmount", null);
	}
}

