package com.qingcheng.service.order;

import com.qingcheng.pojo.order.CategoryReport;

import java.util.List;
import java.util.Map;

public interface CategoryReportService {

    /**
     * 商品类目销售情况统计
     *
     * @param date 日期
     * @return List<CategoryReport>
     */
    List<CategoryReport> getCategoryReport(String date);

    /**
     * 统计昨日商品类目销售情况
     */
    void yesterday();

    /**
     * 按1级类目统计
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List<Map<String, Object>>
     */
    List<Map<String,Object>> getCategory1(String startTime,String endTime);

}
