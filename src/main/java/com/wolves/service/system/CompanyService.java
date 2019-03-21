package com.wolves.service.system;

import java.util.List;
import javax.annotation.Resource;

import com.wolves.dto.user.BaseCompanyDTO;
import com.wolves.dto.user.CompanyDTO;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("companyService")
public class CompanyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("CompanyMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("CompanyMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("CompanyMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("CompanyMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("CompanyMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("CompanyMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("CompanyMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询企业
	 * @param companyId
	 * @return
	 */
	public CompanyDTO selectCompanyById(String companyId){
		return (CompanyDTO) dao.findForObject("CompanyMapper.selectCompanyById", companyId);
	}

	/**
	 * 查询所有的企业
	 * @return
	 */
	public List<BaseCompanyDTO> selectAllCompany(){

		return (List<BaseCompanyDTO>) dao.findForList("CompanyMapper.selectAllCompany", null);
	}
}

