# 1.报修单repair_apply	
	id
	apply_no 工单编号
	type(1巡检报修、2弱电报修、3门禁报修)
	order_date
	order_time
	repair_content
	image_urls
	create_time
	apply_status
#2.维修进度repair_progress
    id
    apply_id
    progress(进度状态)
    create_time
    create_user_id
#3.缴费单payment
	id
	缴费单位id/个人id
	payment_type（物业、水、电、一卡通）
	amount 金额
	payment_date 费用月度
	status
	create_time
#4.客服表service_man
	id
	floor 楼层
	floor_master_name 层长姓名
	master_tel 联系方式
	create_time
#5.用户车牌绑定表user_car_bind
	id
	user_id 用户id
	car_province 车牌归属
	car_no 车牌号
	status 绑定状态
	create_time
	update_time
#6.用户停车月卡user_car_month_card
	id
	user_id 用户id
	card_no 月卡号
	price 金额
	use_month 有效月份
	card_status 月卡状态
	create_time
    update_time
#7.停车记录表parking
	id
	user_id 用户id
	begain_time 开始时间
	end_time 结束时间
	total_hour 总时长
	fee 停车费
	create_time
    update_time
#8.付费记录pay_order
	id
	order_type（1水电物业，2停车费）
	order_amount 付费金额
	create_time 付费时间
    update_time
	return_time 确认时间
	pay_status	付费状态
#9.公告消息表tip_msg
	id
	msg_title 公告标题
	msg_content 公告内容
	img_url 图片url
	attach_url 附件url
	create_time
    update_time
#10.进度提醒表alert_msg
	id
	to_user
	alert_title 提醒标题
	alert_content 提醒内容
	img_url 图片url
    attach_url 附件url
	create_time
    update_time
#11.场地表place
	id
	place_type（1.会议室，2.活动室，3健身房）
	postion 所处位置
	image_url
	equipment 设备
	equipment_health 设备运行情况
	rent_fee 租金
	create_time
    update_time
#12.场地预定表place_booking
	id
	place_id 场地id
	apply_user_id 预定人
	place_date 预定日期
	place_time 预定时间
	status 预定状态
	create_time
    update_time
#13.装修申请表


#14.项目申请表


#15.入住企业表company
    id
    company_name(企业名)
    level(企业等级)
    place(企业租位)
    create_time
    update_time
#16.

