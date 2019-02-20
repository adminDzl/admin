# 1.报修单repair_apply	
	id
	apply_no 工单编号
	type(1巡检报修、2弱电报修、3门禁报修)
	order_date
	order_time
	repair_content
	image_urls
	apply_time
	apply_status
#2.缴费单(payment)
	id
	缴费单位id/个人id
	payment_type（物业、水、电、一卡通）
	amount
	payment_date
	status
	
#3.客服表(servicer)
	id
	floor
	floor_master_name
	master_tel
#4.用户车牌绑定表(user_car_bind)
	id
	user_id
	car_province
	car_no
	status
#5.用户停车月卡(user_car_monthcard)
	id
	user_id
	card_no
	use_month
	card_status
#6.停车记录表(parking)
	id
	user_id
	begain_time
	end_time
	total_hour
	fee
#7.付费记录（pay_order）
	id
	order_type（1水电物业，2停车费）
	order_amount
	pay_apply_time
	pay_return_time
	pay_status	
#8.公告消息表(msg)
	id
	msg_title
	msg_content
	create_time
#9.进度提醒表(alert_msg)
	id
	to_user
	alert_title
	alert_content
#10.场地表(place)
	id
	place_type（1.会议室，2.活动室，3健身房）
	postion所处位置
	image
	equipment
	租金
#11场地预定表(place_apply)
	id
	place_id
	apply_user_id
	apply_time
	place_date
	place_time
	status
#12.装修申请表


#13.项目申请表


#14.入住企业表

#15.

