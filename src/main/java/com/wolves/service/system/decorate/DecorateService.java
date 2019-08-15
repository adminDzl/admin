package com.wolves.service.system.decorate;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;

import com.wolves.common.ApplyTypeEnum;
import com.wolves.common.StatusEnum;
import com.wolves.dao.DaoSupport;
import com.wolves.dto.FloorManDTO;
import com.wolves.dto.RoomDTO;
import com.wolves.dto.user.DecorateDataDTO;
import com.wolves.dto.user.UnionDTO;
import com.wolves.entity.app.User;
import com.wolves.entity.system.Decorate;
import com.wolves.entity.system.Page;
import com.wolves.service.system.buildman.BuildManService;
import com.wolves.service.system.floorman.FloorManService;
import com.wolves.service.system.room.RoomService;
import com.wolves.service.system.user.UserService;
import com.wolves.util.PageData;
import com.wolves.util.StringUtils;
import com.wolves.util.UuidUtil;
import org.springframework.stereotype.Service;

@Service("decorateService")
public class DecorateService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name="userService")
	private UserService userService;

	@Resource(name="buildmanService")
	private BuildManService buildManService;

	@Resource(name="floormanService")
	private FloorManService floorManService;

	@Resource(name="roomService")
	private RoomService roomService;
	
	/**
	* 新增
	*/
	public void save(PageData pd)  {
		dao.save("DecorateMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd)  {
		dao.delete("DecorateMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd)  {
		dao.update("DecorateMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page)  {
		return (List<PageData>)dao.findForList("DecorateMapper.datalistPage", page);
	}

	/**
	 * 一卡通申请
	 * @return
	 */
	public List<PageData> passList(Page page){

		return (List<PageData>)dao.findForList("DecorateMapper.passlistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)  {
		return (List<PageData>)dao.findForList("DecorateMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)  {
		return (PageData)dao.findForObject("DecorateMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)  {
		dao.delete("DecorateMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 保存申请
	 */
	public void saveApply(String token, List<DecorateDataDTO> decorateDataDTOs){
		User user = userService.getUser(token);
		if (decorateDataDTOs != null && !decorateDataDTOs.isEmpty()){
			for (DecorateDataDTO decorateDataDTO : decorateDataDTOs){
				Decorate decorate = new Decorate();
				decorate.setDecorateNo(UuidUtil.get32UUID());
				decorate.setDecorateId(UuidUtil.get32UUID());
				decorate.setUserId(user.getUserId());
				decorate.setType(decorateDataDTO.getType());
				decorate.setName(decorateDataDTO.getName());
				decorate.setSex(decorateDataDTO.getSex());
				decorate.setIdCard(decorateDataDTO.getIdCard());
				decorate.setPhone(decorateDataDTO.getPhone());
				decorate.setAccess(decorateDataDTO.getAccess());

				List<RoomDTO> roomDTOs = roomService.selectRoomByCompanyId(user.getCompanyId());
				if (roomDTOs != null && !roomDTOs.isEmpty()){
					decorate.setRoom(roomDTOs.get(0).getName());
					FloorManDTO floorManDTO = floorManService.selectFloorManById(roomDTOs.get(0).getFloormanId());
					if (floorManDTO !=  null){
						decorate.setFloor(floorManDTO.getFloor());
						PageData pd = new PageData();
						pd.put("BUILDMAN_ID", floorManDTO.getBuildNo());
						pd = buildManService.findById(pd);
						if (pd != null && pd.get("BUILD_NAME") != null){
							decorate.setBuildman(pd.get("BUILD_NAME").toString());
						}
					}
				}
				decorate.setContent(decorateDataDTO.getContent());
				decorate.setFileUrl(decorateDataDTO.getFileUrl());
				decorate.setStatus(Integer.valueOf(StatusEnum.INIT.getKey()));
				dao.save("DecorateMapper.saveApply", decorate);
				if (decorateDataDTO.getPhone().equals(user.getPhone())){
					user.setSfid(decorateDataDTO.getIdCard());
					user.setSex(decorateDataDTO.getSex());
					userService.updateUser(user);
				}
			}
		}
	}

	/**
	 * 查询申请列表
	 * @param userId
	 * @return
	 */
	public List<Decorate> selectMyApply(String userId){

		return (List<Decorate>)dao.findForList("DecorateMapper.selectMyApply", userId);
	}

	/**
	 * 查询申请明细
	 * @param decorateId
	 * @return
	 */
	public Decorate selectMyApplyDetail(String decorateId){

		return (Decorate) dao.findForObject("DecorateMapper.selectMyApplyDetail", decorateId);
	}

	/**
	 * 取消预约
	 * @param decorateId
	 */
	public void updateDecorate(String decorateId){
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("decorateId", decorateId);
		params.put("status", StatusEnum.REJECT.getKey());

		dao.update("DecorateMapper.updateDecorate", params);
	}

	/**
	 * 查询一卡通导出pdf数据
	 */
	public UnionDTO findUnionById(String decorateId){

		return (UnionDTO) dao.findForObject("DecorateMapper.findUnionById", decorateId);
	}
}

