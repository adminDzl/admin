package com.wolves.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

	@ApiModel(description = "施工许可参数")
	public class YunweiConstructionDTO {
		    
		@ApiModelProperty(name = "title",value = "标题信息")
		    private String title;
		 @ApiModelProperty(name = "construction_units",value = "施工单位")
		    private String construction_units;
		 @ApiModelProperty(name = "construction_director",value = "施工负责人")
		    private String construction_director;
		 @ApiModelProperty(name = "contact_way",value = "施工联系方式")
		    private String contact_way;
		 @ApiModelProperty(name = "plan_start_time",value = "工作时间-开始")
		    private String plan_start_time;
		 @ApiModelProperty(name = "plan_end_time",value = "工作时间-结束")
		    private String plan_end_time;
		 @ApiModelProperty(name = "construction_type",value = "施工分类")
		    private String construction_type;
		 @ApiModelProperty(name = "system_id",value = "具体类型")
		    private String system_id;
		 @ApiModelProperty(name = "describes",value = "其他类型")
		    private String describes;
		 @ApiModelProperty(name = "job_location",value = "工作区域")
		    private String job_location;
		 @ApiModelProperty(name = "safeguard_procedures",value = "保护措施")
		    private String safeguard_procedures;
		 @ApiModelProperty(name = "itemList",value = "申请列表信息")
		 	private List<YunweiConstructionItemDTO> itemList;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getConstruction_units() {
			return construction_units;
		}
		public void setConstruction_units(String construction_units) {
			this.construction_units = construction_units;
		}
		public String getConstruction_director() {
			return construction_director;
		}
		public void setConstruction_director(String construction_director) {
			this.construction_director = construction_director;
		}
		public String getContact_way() {
			return contact_way;
		}
		public void setContact_way(String contact_way) {
			this.contact_way = contact_way;
		}
		public String getPlan_start_time() {
			return plan_start_time;
		}
		public void setPlan_start_time(String pan_start_time) {
			this.plan_start_time = pan_start_time;
		}
		public String getPlan_end_time() {
			return plan_end_time;
		}
		public void setPlan_end_time(String plan_end_time) {
			this.plan_end_time = plan_end_time;
		}
		public String getConstruction_type() {
			return construction_type;
		}
		public void setConstruction_type(String construction_type) {
			this.construction_type = construction_type;
		}
		public String getSystem_id() {
			return system_id;
		}
		public void setSystem_id(String system_id) {
			this.system_id = system_id;
		}
		public String getDescribes() {
			return describes;
		}
		public void setDescribes(String describes) {
			this.describes = describes;
		}
		public String getJob_location() {
			return job_location;
		}
		public void setJob_location(String job_location) {
			this.job_location = job_location;
		}
		public String getSafeguard_procedures() {
			return safeguard_procedures;
		}
		public void setSafeguard_procedures(String safeguard_procedures) {
			this.safeguard_procedures = safeguard_procedures;
		}
		public List<YunweiConstructionItemDTO> getItemList() {
			return itemList;
		}
		public void setItemList(List<YunweiConstructionItemDTO> itemList) {
			this.itemList = itemList;
		}
		 
		   
}
