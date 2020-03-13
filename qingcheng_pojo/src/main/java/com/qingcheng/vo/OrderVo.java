package com.qingcheng.vo;

import com.qingcheng.pojo.order.Order;
import com.qingcheng.pojo.order.OrderItem;

import java.io.Serializable;
import java.util.List;

public class OrderVo implements Serializable {

    private Order order;

    private List<OrderItem> orderItemList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
