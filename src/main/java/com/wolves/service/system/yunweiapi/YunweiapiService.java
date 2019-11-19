package com.wolves.service.system.yunweiapi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wolves.common.RepairClientConstants;
import com.wolves.dao.DaoSupport;
import com.wolves.dto.FloorManDTO;
import com.wolves.dto.RoomDTO;
import com.wolves.dto.UserCarDTO;
import com.wolves.dto.UserCarMonthCardDTO;
import com.wolves.dto.UserCarRenewalDTO;
import com.wolves.dto.YunweiCard;
import com.wolves.dto.YunweiConstruction;
import com.wolves.dto.YunweiConstructionItem;
import com.wolves.dto.user.CompanyDTO;
import com.wolves.dto.user.DecorateDataDTO;
import com.wolves.dto.user.YunweiCarMonth;
import com.wolves.entity.app.User;
import com.wolves.entity.system.Decorate;
import com.wolves.service.system.CompanyService;
import com.wolves.service.system.buildman.BuildManService;
import com.wolves.service.system.floorman.FloorManService;
import com.wolves.service.system.room.RoomService;
import com.wolves.service.system.user.UserService;
import com.wolves.service.system.usercarmonthcard.UserCarMonthCardService;
import com.wolves.util.HttpClientUtil;
import com.wolves.util.Logger;
import com.wolves.util.PageData;
import com.wolves.util.UuidUtil;

@Service("yunweiapiService")
public class YunweiapiService {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name="userService")
	private UserService userService;
	
	 @Resource(name = "companyService")
	 private CompanyService companyService;
	 
	 @Resource(name="roomService")
		private RoomService roomService;
	 
	 @Resource(name="floormanService")
		private FloorManService floorManService;
	 
	 @Resource(name="buildmanService")
		private BuildManService buildManService;
	 
	 @Resource(name="usercarmonthcardService")
	 	private UserCarMonthCardService usercarmonthcardService;
	 @Resource(name = "daoSupport")
	    private DaoSupport dao;
	
	 /**
	  * 一卡通申请
	  * @param user
	  * @param decorateParamDTOs
	  * @return
	  */
	 public Integer createApply(User user, List<DecorateDataDTO> decorateParamDTOs){
	        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
	        //照抄，获取楼宇，楼梯，楼层信息
	        Decorate decorate = new Decorate();
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
				decorate.setBody(roomService.selectBodyByCompanyId(user.getCompanyId()).get(0).getBodyname());
			}
			
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("appType", RepairClientConstants.APP_TYPE);
	        jsonObject.put("title","一卡通");
	        jsonObject.put("applicant",user.getName());
	        jsonObject.put("applyphone",user.getPhone());
	      //  jsonObject.put("applyapartment",user.getPosition());
	        jsonObject.put("applyapartment","部门");
	        jsonObject.put("company", companyDTO.getCompanyName());
	        jsonObject.put("louyu",decorate.getBuildman());//楼宇：智慧大厦
	        jsonObject.put("louti",decorate.getBody());//楼体：主楼/裙楼
	        jsonObject.put("floor",decorate.getFloor());
	        jsonObject.put("roomnumber",decorate.getRoom());
	        jsonObject.put("describes","备注信息");
	        //itemlist列表子信息（子类）
	        List<YunweiCard> yunweiCards=new ArrayList<YunweiCard>();
	        for(DecorateDataDTO decorateDataDTO: decorateParamDTOs) {
	        	YunweiCard yunweiCard=new YunweiCard();
	        	yunweiCards.add(yunweiCard);
	        	yunweiCards.get(yunweiCards.size()-1).setName(decorateDataDTO.getName());
	        	yunweiCards.get(yunweiCards.size()-1).setSex(decorateDataDTO.getSex());
	        	yunweiCards.get(yunweiCards.size()-1).setCard(decorateDataDTO.getIdCard());
	        	yunweiCards.get(yunweiCards.size()-1).setPhone(decorateDataDTO.getPhone());
	        	yunweiCards.get(yunweiCards.size()-1).setBusiness_type(decorateDataDTO.getType());
	        	yunweiCards.get(yunweiCards.size()-1).setLimits(decorateDataDTO.getAccess());
	        	yunweiCards.get(yunweiCards.size()-1).setSpecial_case_supplement(decorateDataDTO.getContent());	        	
        }
	        jsonObject.put("itemList",yunweiCards);

	        System.out.println("json:"+jsonObject);
	        this.decorateLogin();
	        JSONObject result = null;
	        try {
	            String resu = HttpClientUtil.send(RepairClientConstants.ADDRESS+RepairClientConstants.APPLY, jsonObject, "UTF-8");
	            logger.info("result:>>>>>>"+resu);
	            result = JSONObject.parseObject(resu);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Integer res = result.getInteger("res");
	        if (res != 1){
	            logger.warn("工单创建失败, 消息如下："+jsonObject.toJSONString());
	        }
	        logger.info("result:>>>>>"+JSONObject.toJSONString(result));
	        JSONObject object = result.getJSONObject("obj");
	        return res;	        
	    }
	 
	 //----------------------------------------------------运维登陆-----------------------------------------------------------
	    public Integer decorateLogin(){
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("appType", RepairClientConstants.APP_TYPE);
	        params.put("username", RepairClientConstants.USER_NAME);
	        params.put("password", RepairClientConstants.PASS_WARD);

	        JSONObject jsonObject = HttpClientUtil.httpHttpFormData(RepairClientConstants.ADDRESS+RepairClientConstants.LOGIN, params);
	        Integer res = jsonObject.getInteger("res");
	        if (res != 1){
	            logger.warn("装修登录失败, 消息如下："+jsonObject.toJSONString());
	        }
	        return res;
	    }
	    
	    /**
	     * 车库申请
	     */
	    public Integer createUserCar(User user,UserCarDTO userCarDTO){	    	
	    	//传入缴费车辆信息
	    	UserCarMonthCardDTO monthCardDTO = new UserCarMonthCardDTO();
			monthCardDTO.setUserId(user.getUserId());
			monthCardDTO.setUserName(user.getName());
			monthCardDTO.setCardNo(UuidUtil.get32UUID());
			monthCardDTO.setCarNo(userCarDTO.getPlate());
			monthCardDTO.setPrice("100");
			monthCardDTO.setMonths(userCarDTO.getMonth());
//			Integer day = userCarDTO.getMonth()*30;
//			monthCardDTO.setUseTilDate(this.getDay(null, day));
			Integer month = userCarDTO.getMonth();
			monthCardDTO.setUseTilDate(this.getDay1(null, month));
			monthCardDTO.setUserCarMonthCardId(UuidUtil.get32UUID());
			
			
	        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
	        //照抄，获取楼宇，楼梯，楼层信息
	        Decorate decorate = new Decorate();
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
				decorate.setBody(roomService.selectBodyByCompanyId(user.getCompanyId()).get(0).getBodyname());
			}
			//
			
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("appType", RepairClientConstants.APP_TYPE);
	        jsonObject.put("title","车库业务申请");
	        jsonObject.put("proposer", user.getName());//申请人
	        jsonObject.put("name_of_applicant",companyDTO.getCompanyName());//申请单位
	        jsonObject.put("contact",user.getPhone());//联系方式
	        //enterprise_entry 为checkbox值为3【智慧大厦】，2【信息港】，1【其他】
	        String enterprise_entry=this.getEnter(decorate.getBuildman());
	        jsonObject.put("enterprise_entry",enterprise_entry);//企业入驻情况：信息港/智慧大厦
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        jsonObject.put("apply_time",formatter.format(new Date()));//申请时间
	        jsonObject.put("address_of_applicant",decorate.getRoom());//企业地址
	        
	        List<YunweiCarMonth> yunweiCarMonths=new ArrayList<YunweiCarMonth>();
	        YunweiCarMonth yunweiCarMonth=new YunweiCarMonth();	        
	        yunweiCarMonth.setBusiness_type("首次开通");
	        yunweiCarMonth.setParking_lot_no("");
	        yunweiCarMonth.setSpecific_location("具体地点");//具体地点
	        yunweiCarMonth.setGarage_location(decorate.getBuildman());//车库地点
	        yunweiCarMonth.setStaff_name(user.getName());
	        yunweiCarMonth.setId_number(user.getSfid());//身份证号
	        yunweiCarMonth.setMobile_no(user.getPhone());
	        yunweiCarMonth.setPlan_start_time(formatter.format(new Date()));//预计开始时间,目前默认即时开始
	        yunweiCarMonth.setMonths(monthCardDTO.getMonths().toString());
	        yunweiCarMonth.setLicense_plate_number(monthCardDTO.getCarNo());
	        Integer x=monthCardDTO.getMonths()*100;
	        yunweiCarMonth.setExpense(x.toString());
	        yunweiCarMonths.add(yunweiCarMonth);
 	        
	        jsonObject.put("itemList",yunweiCarMonths);
	        System.out.println("json:"+jsonObject);
	    
	        this.decorateLogin();
	        JSONObject result = null;
	        try {
	            String resu = HttpClientUtil.send(RepairClientConstants.ADDRESS+RepairClientConstants.CARMONTH, jsonObject, "UTF-8");
	            logger.info("result:>>>>>>"+resu);
	            result = JSONObject.parseObject(resu);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Integer res = result.getInteger("res");
	        if (res != 1){
	            logger.warn("工单创建失败, 消息如下："+jsonObject.toJSONString());
	        }
	        logger.info("result:>>>>>"+JSONObject.toJSONString(result));
	        JSONObject object = result.getJSONObject("obj");

	        return res;	      	        
	    }	 
	    /**
	     * 续卡服务
	     * @param user
	     * @param userCarRenewalDTO
	     * @return
	     */
	    public Integer renewCar(User user,UserCarRenewalDTO userCarRenewalDTO) {
	    	//传入续费车辆信息
	    	UserCarMonthCardDTO userCarMonthCardDTO = usercarmonthcardService.selectUserCarById(userCarRenewalDTO.getUserCarMonthCardId());
	   // 	userCarMonthCardDTO.setMonths(userCarRenewalDTO.getMonth());
			Integer month = userCarRenewalDTO.getMonth();
			userCarMonthCardDTO.setMonths(month);
		    Date tildate=	userCarMonthCardDTO.getUseTilDate();//获取原截止日期			
			
	        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
	        //照抄，获取楼宇，楼梯，楼层信息
	        Decorate decorate = new Decorate();
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
				decorate.setBody(roomService.selectBodyByCompanyId(user.getCompanyId()).get(0).getBodyname());
			}
			//
			
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("appType", RepairClientConstants.APP_TYPE);
	        jsonObject.put("title","车库业务申请");
	        jsonObject.put("proposer", user.getName());//申请人
	        jsonObject.put("name_of_applicant",companyDTO.getCompanyName());//申请单位
	        jsonObject.put("contact",user.getPhone());//联系方式
	        //enterprise_entry 为checkbox值为3【智慧大厦】，2【信息港】，1【其他】
	        String enterprise_entry=this.getEnter(decorate.getBuildman());
	        jsonObject.put("enterprise_entry",enterprise_entry);//企业入驻情况：信息港/智慧大厦
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        jsonObject.put("apply_time",formatter.format(new Date()));//申请时间
	        jsonObject.put("address_of_applicant",decorate.getRoom());//企业地址
	        
	        List<YunweiCarMonth> yunweiCarMonths=new ArrayList<YunweiCarMonth>();
	        YunweiCarMonth yunweiCarMonth=new YunweiCarMonth();	        
	        yunweiCarMonth.setBusiness_type("延期续费");
	        yunweiCarMonth.setParking_lot_no("");
	        yunweiCarMonth.setSpecific_location("具体地点");//具体地点
	        yunweiCarMonth.setGarage_location(decorate.getBuildman());//车库地点
	        yunweiCarMonth.setStaff_name(user.getName());
	        yunweiCarMonth.setId_number(user.getSfid());//身份证号
	        yunweiCarMonth.setMobile_no(user.getPhone());
	        Integer i=(new Date()).compareTo(tildate);
	        if(i<0) {//期内续费
	        	Calendar calendar2 = Calendar.getInstance();
	        	calendar2.setTime(tildate);
	        	calendar2.add(Calendar.DATE, 1);
	        	yunweiCarMonth.setPlan_start_time(formatter.format(calendar2.getTime()));//默认从截止日期+1天开始
	        }else {//断续续费
	        	yunweiCarMonth.setPlan_start_time(formatter.format(new Date()));//从续费当日开始
	        }	        
	        yunweiCarMonth.setMonths(userCarMonthCardDTO.getMonths().toString());
	        yunweiCarMonth.setLicense_plate_number(userCarMonthCardDTO.getCarNo());
	        Integer x=userCarMonthCardDTO.getMonths()*100;
	        yunweiCarMonth.setExpense(x.toString());
	        yunweiCarMonths.add(yunweiCarMonth);
 	        
	        jsonObject.put("itemList",yunweiCarMonths);
	        //System.out.println("json:"+jsonObject);
	    
	        this.decorateLogin();
	        JSONObject result = null;
	        try {
	            String resu = HttpClientUtil.send(RepairClientConstants.ADDRESS+RepairClientConstants.CARMONTH, jsonObject, "UTF-8");
	            logger.info("result:>>>>>>"+resu);
	            result = JSONObject.parseObject(resu);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Integer res = result.getInteger("res");
	        if (res != 1){
	            logger.warn("工单创建失败, 消息如下："+jsonObject.toJSONString());
	        }
	        logger.info("result:>>>>>"+JSONObject.toJSONString(result));
	        JSONObject object = result.getJSONObject("obj");

	        return res;	 
	    }   
	    
	    public Integer createConstruction(User user, YunweiConstruction yunweiconstruction) {
	        CompanyDTO companyDTO = companyService.selectCompanyById(user.getCompanyId());
			
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("appType", RepairClientConstants.APP_TYPE);
	        jsonObject.put("title",yunweiconstruction.getTitle());
	        jsonObject.put("proposer", user.getName());//申请人
	        jsonObject.put("applyphone",user.getPhone());//申请人联系方式
	        jsonObject.put("apply_company",companyDTO.getCompanyName());//申请单位
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        jsonObject.put("application_time",formatter.format(new Date()));
	        jsonObject.put("construction_units",yunweiconstruction.getConstruction_units());
	        jsonObject.put("construction_director",yunweiconstruction.getConstruction_director());
	        jsonObject.put("contact_way",yunweiconstruction.getContact_way());
	        jsonObject.put("plan_start_time",yunweiconstruction.getPlan_start_time());
	        jsonObject.put("plan_end_time",yunweiconstruction.getPlan_end_time());
	        jsonObject.put("construction_type", yunweiconstruction.getConstruction_type());
	        jsonObject.put("system_id", yunweiconstruction.getSystem_id());
	        jsonObject.put("describes",yunweiconstruction.getDescribes());
	        jsonObject.put("job_location",yunweiconstruction.getJob_location());
	        jsonObject.put("safeguard_procedures", yunweiconstruction.getSafeguard_procedures());	        
	        jsonObject.put("itemList",yunweiconstruction.getItemList());	        
 	        
	        System.out.println("json:"+jsonObject);
	    	
	    	this.decorateLogin();
	        JSONObject result = null;
	        try {
	            String resu = HttpClientUtil.send(RepairClientConstants.ADDRESS+RepairClientConstants.CONSTRUCTION, jsonObject, "UTF-8");
	            logger.info("result:>>>>>>"+resu);
	            result = JSONObject.parseObject(resu);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Integer res = result.getInteger("res");
	        if (res != 1){
	            logger.warn("工单创建失败, 消息如下："+jsonObject.toJSONString());
	        }
	        logger.info("result:>>>>>"+JSONObject.toJSONString(result));
	        JSONObject object = result.getJSONObject("obj");
	        this.saveConstruct(user.getUserId(), yunweiconstruction, object);
	        return res;	 
	    }
	    
	    public void saveConstruct(String userId, YunweiConstruction yunweiconstruction, JSONObject jsonObject) {
	    	String constructionId=UuidUtil.get32UUID();//本机单号
	    	yunweiconstruction.setConstructionId(constructionId);
	    	yunweiconstruction.setUserId(userId);
	    	yunweiconstruction.setStatus(0);
	    	yunweiconstruction.setProcId(jsonObject.getString("procId"));
	    	yunweiconstruction.setWjbiid(jsonObject.getString("wjbiid"));
	    	yunweiconstruction.setTaskId(jsonObject.getString("taskId"));
	    	yunweiconstruction.setCreateTime(new Date());
	    	yunweiconstruction.setUpdateTime(new Date());
	    	List<YunweiConstructionItem> ycIL=yunweiconstruction.getItemList();
	    	 if ((Integer) dao.save("YunweiapiMapper.saveConstruction", yunweiconstruction)==1) {
	    		 for(YunweiConstructionItem ycI:ycIL) {
	    			 ycI.setConstructionId(constructionId);
	    			 dao.save("YunweiapiMapper.saveConstructionItems", ycI); 
	    			 System.out.println(ycI);
	    		 }
	    	 }
	    }
	    
	    
	    /**
	     * 获取某个时间后几个月的天数
	     * @param startDate 传递日期
	     * @param day 增加的天数
	     * @return
	     */
		private Date getDay(Date startDate,Integer day){
			Calendar calendar2 = Calendar.getInstance();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	        if (startDate != null){
	            calendar2.setTime(startDate);
	        }
			calendar2.add(Calendar.DATE, day);
			String dayAfter = sdf2.format(calendar2.getTime());

			try {
				return sdf2.parse(dayAfter);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * 根据楼宇关键字设置接口enterprise_entrycheckbox值
		 * @param enter
		 * @return
		 */
		private String getEnter(String enter) {
			if(enter.contains("智慧大厦")) {
				return "3";				
			}else if(enter.contains("信息港")) {
				return "2";
			}else {
				return "1";
			}
		}
		/**
		 * 计算固定日期+月份后的日期
		 * @param startDate
		 * @param month
		 * @return
		 */
		private Date getDay1(Date startDate,Integer month){
			Calendar calendar2 = Calendar.getInstance();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	        if (startDate != null){
	            calendar2.setTime(startDate);
	        }
			calendar2.add(Calendar.MONTH, month);
			String dayAfter = sdf2.format(calendar2.getTime());

			try {
				return sdf2.parse(dayAfter);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
}
