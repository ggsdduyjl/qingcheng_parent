package com.qingcheng.controller.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.service.order.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单超时自动处理
 * 当订单超过一定时间（后台设置）后用户没有付款，需要将订单关闭
 */
@Component
public class OrderTask {

    @Reference
    private OrderService orderService;

    /**
     * 订单超时未付款，自动关闭
     * 两分钟执行一次
     */
    //@Scheduled(cron = "0 0/2 * * * ?")
    public void execute() {
        orderService.orderTimeOutLogic();
    }
}
