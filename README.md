# 1.报修单 repair_apply
	id
	apply_no 工单编号
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
# 3.缴费单 payment（app上单位的ceo和ceo指定的人能看到并操作）
	id
	缴费单位id
	payment_type（物业、水、电）
	amount 金额
	payment_date 费用月度
	status
	create_time
# 4.楼长表 build_man
	id
    build_no 楼栋号
	build_master_name 楼长姓名
	master_tel 联系方式
	create_time	
# 5.层长表 floor_man
	id
    build_no 楼栋号
	floor 楼层
	floor_master_name 层长姓名
	master_tel 联系方式
	create_time
# 6.用户车牌绑定表 user_car_bind
	id
	user_id 用户id
	car_province 车牌归属
	car_no 车牌号
	status 绑定状态
	is_license 是否已上传行驶证
	create_time
	update_time
# 7.用户停车月卡 user_car_month_card
	id
	user_id 用户id
	card_no 月卡号
	price 金额
	use_til_date 有效月份
	card_status 月卡状态
	create_time
    update_time
# 8.停车记录表 parking 暂时不确定是自己座还是用自己
	id
	user_id 用户id
	begin_time 开始时间
	end_time 结束时间
	total_hour 总时长
	fee 停车费
	create_time
    update_time
# 9.付费记录 pay_order
	id
	pay_type（1水电物业，2停车费，3场地预定费4，一卡通费用）
	pay_amount 付费金额
	create_time 付费时间
    update_time
	return_time 确认时间
	pay_status	付费状态
# 10.站内信表 tip_msg
	id
	msg_type 公告消息，进度提醒
	to_user 公告为空，
	alert_title 提醒标题
	alert_content 提醒内容text
	create_time
    update_time
# 11.场地表 yard
	id
	place_type（1.会议室，2.活动室，3健身房）
	position 所处位置
	image_url
	equipment 设备
	rent_fee 价格
	create_time
    update_time
# 12.场地预定表 yard_appoint
	id
	place_id 场地id
	apply_user_id 预定人
	place_date 预定日期
	begin_time 预定开始时间
	end_time 预定结束时间
	book_duration 预定时长
	book_fee 预定费用
	status 预定状态
	create_time
    update_time
# 13.装修申请表 decorate
    id
    decorate_no
    title
    content
    status 审核状态
    create_time
    update_time

# 14.新闻公告 news_tip
    id
	news_type 1.新闻2.项目申报
	news_title 标题
	news_content 内容text
    attach_url 附件url
	create_time
    update_time
# 15.入住企业表 company
    id
    company_name(企业名)
    type (1.自己公司，2.其他公司)
    status
    description 描述
    company_certify(企业认证)
    place_id(企业租位)
    logo
    create_time
    update_time
# 16.企业租位登记表 rent_place
    id
    place_address(租位地址)
    place_cycle(租位周期)
    place_rent(租位租金)
    attach_url
    create_time
    update_time
# 17 企业用户表

# 18 用户门禁权限表
    id
    user_id
    gate_id
    status
    begain_time
    end_time
 
# 19 车位管理 parking_lot
    id
    lot_no 车位号
    location 位置
    user_id 用户
    status

# 20 app_role角色
    id
    role_name
    company_id
# 21 app_resource资源
    id
    resource_name
    resuorce_type 0公司，1门禁
    room_id 如果是门禁资源，就对应到room_id,resource_name取room_name
    company_id
# 22 app_user_role
    id
    user_id
    app_role_id
# 23 app_role_resource
    id
    role_id
    resource_id
    
    
    
alipay: https://docs.open.alipay.com/204




