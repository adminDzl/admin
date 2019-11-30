package com.wolves.service.system;

import com.alibaba.fastjson.JSONObject;
import com.wolves.dto.AppointmentDTO;
import com.wolves.entity.app.PayOrder;
import com.wolves.service.system.payorder.PayOrderService;
import com.wolves.service.system.yardappoint.YardAppointService;
import com.wolves.service.system.yunweiapi.YunweiapiService;
import com.wolves.util.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 定时器任务
 * Created by Administrator on 2019/9/10.
 */
@Component
@EnableScheduling
public class SchedulerService {

    protected Logger logger = Logger.getLogger(this.getClass());

    @Resource(name="payorderService")
    private PayOrderService payorderService;

    @Resource(name="yardappointService")
    private YardAppointService yardappointService;
    
    @Resource(name="yunweiapiService")
    private YunweiapiService yunweiapiService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateYardAppoint(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("定时任务执行开始============》 开始时间:"+sdf.format(new Date(System.currentTimeMillis())));
        //查询待支付订单信息
        List<PayOrder> payOrders = payorderService.selectAllYard();
        if (payOrders != null && !payOrders.isEmpty()){
            for (PayOrder payOrder : payOrders){
                Date now = new Date(System.currentTimeMillis());
                Date create = payOrder.getCreateTime();

                long nowTime = now.getTime();
                //从对象中拿到时间
                long createTime = create.getTime();
                long diff = (nowTime-createTime)/1000/60;
                //大于等于5分钟
                if (diff >= 5){
                    String yardappointId = payOrder.getYardappointId();
                    //五分钟内未进行支付将取消预订场地信息,设置状态为驳回
                    AppointmentDTO appointmentDTO = yardappointService.selectYardAppointById(yardappointId);
                    logger.info("appointmentDTO:"+ JSONObject.toJSONString(appointmentDTO));
                    if (appointmentDTO != null && appointmentDTO.getStatus() != 2){
                        yardappointService.updateYardAppoint(yardappointId);
                    }
                }
            }
        }

        logger.info("定时任务执行结束============》 结束时间:"+sdf.format(new Date(System.currentTimeMillis())));
    }
    
    @Scheduled(cron = "0 0/1 * * * ?")
    public void syncOrderStat() {
    	Date now=new Date();
    	System.out.println("同步运维工单定时任务5分钟一次-->"+now);
    	//1同步施工单状态
    	yunweiapiService.syncConstruction();
    	//2同步一卡通状态
    	yunweiapiService.syncDecoration();
    	
    	//3同步装修申请状态
    	yunweiapiService.syncDecorationApply();
    	//4同步报修单状态**
    	yunweiapiService.syncRepair();
    	
    	//同步车库？不用同步
    	
    	
    	
    }
}
