package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qingcheng.dao.CategoryReportMapper;
import com.qingcheng.pojo.order.CategoryReport;
import com.qingcheng.service.order.CategoryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CategoryReportService.class)
public class CategoryReportServiceImpl implements CategoryReportService {

    @Autowired
    private CategoryReportMapper categoryReportMapper;

    @Override
    public List<CategoryReport> getCategoryReport(String date) {
        LocalDate time;
        if (date == null || "".equals(date)) {
            // 获取昨天日期
            time = LocalDate.now().minusDays(1);
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            time = LocalDate.parse(date, dtf);
        }
        return categoryReportMapper.getCategoryReport(time);
    }

    @Transactional
    @Override
    public void yesterday() {
        List<CategoryReport> categoryReportList = getCategoryReport(null);
        for (CategoryReport categoryReport : categoryReportList) {
            categoryReportMapper.insertSelective(categoryReport);
        }
    }

    @Override
    public List<Map<String, Object>> getCategory1(String startTime, String endTime) {
        return categoryReportMapper.getCategory1(startTime, endTime);
    }

}
