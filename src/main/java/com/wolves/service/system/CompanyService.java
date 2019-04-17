package com.wolves.service.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.common.CompanyTypeEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dto.user.BaseCompanyDTO;
import com.wolves.dto.user.CompanyDTO;
import com.wolves.dto.user.UserExcelDTO;
import com.wolves.util.UuidUtil;
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

	/**
	 * 保存
	 * @param companyDTO
	 */
	public void saveCompany(CompanyDTO companyDTO){

		dao.save("CompanyMapper.saveCompany", companyDTO);
	}

	/**
	 * 查询企业
	 * @param name
	 * @return
	 */
	public List<BaseCompanyDTO> selectCompanyByName(String name){

		return (List<BaseCompanyDTO>) dao.findForList("CompanyMapper.selectCompanyByName", name);
	}


	public void createCompany(String name){
		//判断企业是否存在
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setType(Integer.valueOf(CompanyTypeEnum.out.getKey()));
		companyDTO.setStatus(Integer.valueOf(StatusEnum.INIT.getKey()));
		companyDTO.setCompanyName(name);
		companyDTO.setCompanyId(UuidUtil.get32UUID());
		this.saveCompany(companyDTO);
	}


	public List<String> getCompanyData(List<Map<String, Object>> list){
		List<String> companyDTOS = new ArrayList<String>();
		if (list != null && !list.isEmpty()){
			for (Map<String, Object> map : list){
				companyDTOS.add(this.getData(map));
			}
		}
		return companyDTOS;
	}

	private String getData(Map<String, Object> map){
		Object companyName = map.get("企业名称");
		if (companyName == null){
			String r = "有企业名称为空，请核实";
			throw new RuntimeException(r);
		}
		return companyName.toString();
	}

	/**
	 * 创建企业
	 * @param companyDTOS
	 */
	public void createCompanyByExcel(List<String> companyDTOS){
		if (companyDTOS != null && !companyDTOS.isEmpty()){
			for (String name : companyDTOS){
				this.createCompany(name);
			}
		}
	}
}

