package com.qingcheng.dao;

import com.qingcheng.pojo.order.CategoryReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CategoryReportMapper extends Mapper<CategoryReport> {

    /**
     * 商品类目销售情况统计
     *
     * @param date 日期
     * @return List<CategoryReport>
     */
    @Select("SELECT " +
            "t1.category_id1 as categoryId1, " +
            "t1.category_id2 as categoryId2, " +
            "t1.category_id3 as categoryId3, " +
            "DATE_FORMAT(t2.pay_time,'%Y-%m-%d') as countDate, " +
            "sum(t1.num) as num, " +
            "sum(t1.money) as money " +
            "FROM " +
            "tb_order_item t1 " +
            "LEFT JOIN tb_order t2 ON t1.order_id = t2.id " +
            "WHERE " +
            "t2.pay_status = 1 and DATE_FORMAT(t2.pay_time,'%Y-%m-%d') = #{date} " +
            "GROUP BY " +
            "t1.category_id1,t1.category_id2,t1.category_id3,DATE_FORMAT(t2.pay_time,'%Y-%m-%d')")
    List<CategoryReport> getCategoryReport(@Param("date") LocalDate date);

    /**
     * 按1级类目统计
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List<Map<String, Object>>
     */
    @Select("SELECT " +
            "t2.`name` as categoryName, " +
            "SUM(t1.num) as num, " +
            "SUM(t1.money) as money " +
            "FROM " +
            "tb_category_report t1 " +
            "LEFT JOIN qingcheng_goods.tb_category t2 ON t1.category_id1 = t2.id " +
            "WHERE " +
            "t1.count_date >= #{startTime} " +
            "AND t1.count_date <= #{endTime} " +
            "GROUP BY " +
            "t1.category_id1")
    List<Map<String, Object>> getCategory1(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
