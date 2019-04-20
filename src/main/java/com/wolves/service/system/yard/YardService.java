package com.wolves.service.system.yard;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Resource;

import com.wolves.dao.DaoSupport;
import com.wolves.dto.YardDTO;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
import org.springframework.stereotype.Service;

@Service("yardService")
public class YardService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("YardMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("YardMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("YardMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("YardMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("YardMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("YardMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("YardMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询场地分页
	 * @param params
	 * @return
	 */
	public List<YardDTO> selectYard(Map<String, Object> params){
		return (List<YardDTO>) dao.findForList("YardMapper.selectYard", params);
	}

	/**
	 * 查询场地
	 * @param yardId
	 * @return
	 */
	public YardDTO getYardById(String yardId){

		return (YardDTO) dao.findForObject("YardMapper.getYardById", yardId);
	}

	/**
	 * 更新数据
	 * @param yardDTO
	 */
	public void updateYard(YardDTO yardDTO){

		dao.update("YardMapper.updateYard", yardDTO);
	}

	/**
	 * 删除图片
	 * @param id
	 * @param url
	 */
	public void delImage(String id, String url){
		if (StringUtils.isNotEmpty(id)){
			YardDTO yardDTO = this.getYardById(id);
			String imageUrls = yardDTO.getImageUrl();
			List<String> images = Arrays.asList(imageUrls.split(","));
			if (!images.isEmpty() && StringUtils.isNotEmpty(url)){
				final CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<String>(images);
				for (String item : cowList) {
					if (item.equals(url)) {
						cowList.remove(item);
					}
				}
				yardDTO.setImageUrl(StringUtils.join(cowList, ","));
			}
			this.updateYard(yardDTO);
		}
	}
}

