package com.wolves.service.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.common.CompanyTypeEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dto.user.BaseCompanyDTO;
import com.wolves.dto.user.CompanyDTO;
import com.wolves.dto.user.UserExcelDTO;
import com.wolves.util.StringUtils;
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
		CompanyDTO companyDTO = (CompanyDTO) dao.findForObject("CompanyMapper.selectCompanyById", companyId);
		if (companyDTO != null){
			if (!StringUtils.isEmpty(companyDTO.getCompanyCertify())) {
				String[] arr = companyDTO.getCompanyCertify().split(",");
				companyDTO.setCompanyCertifys(Arrays.asList(arr));
			}
		}
		return companyDTO;
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

	/**
	 *	创建企业
	 * @param name 企业名称
	 * @param y true 审核通过
	 */
	public void createCompany(String name, Boolean y){
		//判断企业是否存在
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setType(Integer.valueOf(CompanyTypeEnum.out.getKey()));
		if (y){
			companyDTO.setStatus(Integer.valueOf(StatusEnum.SUCCESS.getKey()));
		}else {
			companyDTO.setStatus(Integer.valueOf(StatusEnum.INIT.getKey()));
		}
		companyDTO.setCompanyName(name);
		companyDTO.setCompanyId(UuidUtil.get32UUID());
		this.saveCompany(companyDTO);
	}


	public List<CompanyDTO> getCompanyData(List<Map<String, Object>> list){
		List<CompanyDTO> companyDTOS = new ArrayList<CompanyDTO>();
		if (list != null && !list.isEmpty()){
			for (Map<String, Object> map : list){

				companyDTOS.add(getData(map));
			}
		}
		return companyDTOS;
	}

	private CompanyDTO getData(Map<String, Object> map){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CompanyDTO companyDTO = new CompanyDTO();
		Object companyName = map.get("企业名称");
		companyDTO.setCompanyName(companyName.toString());
		try {
			Object comeTime = map.get("入住时间");
			companyDTO.setComeTime(sdf.parse(comeTime.toString()));
			Object agreementTime = map.get("合约时间");
			companyDTO.setAgreementTime(sdf.parse(agreementTime.toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Object scale = map.get("企业规模");
		companyDTO.setScale(scale.toString());
		return companyDTO;
	}

	public PageData checkExcelData(List<Map<String, Object>> list, PageData pd){

		if (list != null && !list.isEmpty()){
			for (Map<String, Object> map : list){
				Object name = map.get("企业名称");
				if (StringUtils.isEmpty(name.toString().trim())){
					pd.put("msg", "企业名称不能为空");
					pd.put("status", "1");
					return pd;
				}
				Object phone = map.get("入住时间");
				if (StringUtils.isEmpty(phone.toString().trim())){
					pd.put("msg", "入住时间不能为空");
					pd.put("status", "1");
					return pd;
				}
				Object email = map.get("合约时间");
				if (StringUtils.isEmpty(email.toString().trim())){
					pd.put("msg", "合约时间不能为空");
					pd.put("status", "1");
					return pd;
				}
				Object company = map.get("企业规模");
				if (StringUtils.isEmpty(company.toString().trim())){
					pd.put("msg", "企业规模不能为空");
					pd.put("status", "1");
					return pd;
				}
			}
		}
		pd.put("status", "0");
		return pd;
	}
	/**
	 * 创建企业
	 * @param companyDTOS 导入的客户默认为审核成功
	 */
	public void createCompanyByExcel(List<CompanyDTO> companyDTOS){
		if (companyDTOS != null && !companyDTOS.isEmpty()){
			for (CompanyDTO companyDTO : companyDTOS){
				//判断公司是否已经存在
				List<BaseCompanyDTO> baseCompanyDTOS = this.selectCompanyByName(companyDTO.getCompanyName());
				if (baseCompanyDTOS.isEmpty()){
					companyDTO.setType(Integer.valueOf(CompanyTypeEnum.out.getKey()));
					companyDTO.setCompanyId(UuidUtil.get32UUID());
					companyDTO.setStatus(Integer.valueOf(StatusEnum.SUCCESS.getKey()));
					this.saveCompany(companyDTO);
				}

			}
		}
	}

	/**
	 * 更新企业信息
	 * @param companyDTO
	 */
	public void updateCompanyById(CompanyDTO companyDTO){

		dao.update("CompanyMapper.updateCompanyById", companyDTO);
	}
}

