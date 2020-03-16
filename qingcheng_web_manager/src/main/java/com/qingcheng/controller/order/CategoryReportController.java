package com.qingcheng.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.order.CategoryReport;
import com.qingcheng.service.order.CategoryReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categoryReport")
public class CategoryReportController {

    @Reference
    private CategoryReportService categoryReportService;

    @RequestMapping("getCategoryReport")
    public List<CategoryReport> getCategoryReport(String date) {
        return categoryReportService.getCategoryReport(date);
    }

    @RequestMapping("getCategory1")
    public List<Map<String, Object>> getCategory1(String startTime, String endTime) {
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            startTime = endTime = LocalDate.now().toString();
        }
        return categoryReportService.getCategory1(startTime, endTime);
    }

}
