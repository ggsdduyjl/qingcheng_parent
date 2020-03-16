package com.qingcheng.controller.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.service.order.CategoryReportService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 统计昨日商品类目销售情况
 */
@Component
public class CategoryReportTask {

    @Reference
    private CategoryReportService categoryReportService;

    /**
     * 每天凌晨1点执行
     */
    //@Scheduled(cron = "0 0 1 * * ?")
    public void execute() {
        categoryReportService.yesterday();
    }

}
