package com.wolves.service.system.newstip;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Resource;

import com.wolves.common.ApplyTypeEnum;
import com.wolves.common.NewsTypeEnum;
import com.wolves.dao.DaoSupport;
import com.wolves.dto.ApplyDataDTO;
import com.wolves.dto.NewsTipDTO;
import com.wolves.dto.YardDTO;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;
import com.wolves.util.ReduceHtmlUtil;
import com.wolves.util.StringUtils;
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
		List<PageData> pageDatas = (List<PageData>)dao.findForList("NewsTipMapper.datalistPage", page);
		if (pageDatas != null && !pageDatas.isEmpty()){
			for (PageData pageData : pageDatas){
				String content = pageData.getString("NEWS_CONTENT");
				if (StringUtils.isNotEmpty(content.trim())){
					pageData.put("NEWS_CONTENT", ReduceHtmlUtil.removeHtmlTag(content));
				}
			}
		}
		return pageDatas;
	}

	/**
	 * 统一申请
	 */
	public List<PageData> decorateList(Page page) {
		List<PageData> pageDatas = (List<PageData>)dao.findForList("NewsTipMapper.decorateList", page);
		return pageDatas;
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

	/**
	 * 查询统一申请的项目申报
	 */
	public List<NewsTipDTO> selectApplyNewsByType(String type){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newsType", NewsTypeEnum.declare.getKey());
		params.put("newsTitle", ApplyTypeEnum.queryValueByKey(type));

		return this.selectNewsByType(params);
	}

	/**
	 * 删除图片
	 * @param id
	 * @param url
	 */
	public void delImage(String id, String url){
		if (StringUtils.isNotEmpty(id)){
			NewsTipDTO newsTipDTO = this.selectNewsById(id);
			String imageUrls = newsTipDTO.getHeadImage();
			List<String> images = Arrays.asList(imageUrls.split(","));
			if (!images.isEmpty() && StringUtils.isNotEmpty(url)){
				final CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<String>(images);
				for (String item : cowList) {
					if (item.equals(url)) {
						cowList.remove(item);
					}
				}
				newsTipDTO.setHeadImage(StringUtils.join(cowList, ","));
			}
			this.updateNews(newsTipDTO);
		}
	}

	/**
	 * 更新数据
	 * @param newsTipDTO
	 */
	public void updateNews(NewsTipDTO newsTipDTO){

		dao.update("NewsTipMapper.updateNews", newsTipDTO);
	}

	/**
	 * 查询统一申请
	 * @return
	 */
	public List<ApplyDataDTO> selectApplyData(){

		return (List<ApplyDataDTO>)dao.findForList("NewsTipMapper.selectApplyData", null);
	}
}

