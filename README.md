# 1.报修单 repair_apply
	id
	apply_no 工单编号
	type(1巡检报修、2弱电报修、3门禁报修、4其他报修)
	order_date（预约上门日期）
	order_time（预约上门时间）
	repair_content
	image_urls
	create_time
	apply_status
# 2.维修进度 repair_progress
    id
    apply_id
    repair_goods维修物品
    progress(进度状态)
    image_urls
    create_time
    create_user_id
# 3.缴费单 payment
	id
	缴费单位id/个人id
	payment_type（物业、水、电、一卡通）
	amount 金额
	payment_date 费用月度
	status
	create_time
# 4.客服表 service_man
	id
	floor 楼层
	floor_master_name 层长姓名
	master_tel 联系方式
	create_time
# 5.用户车牌绑定表 user_car_bind
	id
	user_id 用户id
	car_province 车牌归属
	car_no 车牌号
	status 绑定状态
	create_time
	update_time
# 6.用户停车月卡 user_car_month_card
	id
	user_id 用户id
	card_no 月卡号
	price 金额
	use_month 有效月份
	card_status 月卡状态
	create_time
    update_time
# 7.停车记录表 parking
	id
	user_id 用户id
	begin_time 开始时间
	end_time 结束时间
	total_hour 总时长
	fee 停车费
	create_time
    update_time
# 8.付费记录 pay_order
	id
	order_type（1水电物业，2停车费）
	order_amount 付费金额
	create_time 付费时间
    update_time
	return_time 确认时间
	pay_status	付费状态
# 9.站内信表 tip_msg
	id
	msg_type 公告消息，进度提醒
	to_user 公告为空，
	alert_title 提醒标题
	alert_content 提醒内容text
    attach_url 附件url
	create_time
    update_time
# 10.场地表 yard
	id
	place_type（1.会议室，2.活动室，3健身房）
	postion 所处位置
	image_url
	equipment 设备
	equipment_health 设备运行情况
	rent_fee 租金
	create_time
    update_time
# 11.场地预定表 yard_booking
	id
	place_id 场地id
	apply_user_id 预定人
	place_date 预定日期
	place_time 预定时间
	status 预定状态
	create_time
    update_time
# 12.装修申请表


# 13.项目申请表


# 14.入住企业表company
    id
    company_name(企业名)
    type (1.自己公司，2.其他公司)
    status
    description 描述
    level(企业等级)
    place_name(企业租位)
    create_time
    update_time
# 15.企业租位登记表 rent_place
    id
    place_address(租位地址)
    place_cycle(租位周期)
    place_rent(租位租金)
    attach_url
    create_time
    update_time


