package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.ReturnOrderMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.ReturnOrder;
import com.qingcheng.service.order.ReturnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class ReturnOrderServiceImpl implements ReturnOrderService {

    @Autowired
    private ReturnOrderMapper returnOrderMapper;

    public List<ReturnOrder> findAll() {
        return returnOrderMapper.selectAll();
    }

    public PageResult<ReturnOrder> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<ReturnOrder> returnOrders = (Page<ReturnOrder>) returnOrderMapper.selectAll();
        return new PageResult<ReturnOrder>(returnOrders.getTotal(), returnOrders.getResult());
    }

    public List<ReturnOrder> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return returnOrderMapper.selectByExample(example);
    }

    public PageResult<ReturnOrder> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<ReturnOrder> returnOrders = (Page<ReturnOrder>) returnOrderMapper.selectByExample(example);
        return new PageResult<ReturnOrder>(returnOrders.getTotal(), returnOrders.getResult());
    }

    public ReturnOrder findById(Long id) {
        return returnOrderMapper.selectByPrimaryKey(id);
    }

    public void add(ReturnOrder returnOrder) {
        returnOrderMapper.insert(returnOrder);
    }

    public void update(ReturnOrder returnOrder) {
        returnOrderMapper.updateByPrimaryKeySelective(returnOrder);
    }

    public void delete(Long id) {
        returnOrderMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(ReturnOrder.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 用户账号
            if (searchMap.get("userAccount") != null && !"".equals(searchMap.get("userAccount"))) {
                criteria.andLike("userAccount", "%" + searchMap.get("userAccount") + "%");
            }
            // 联系人
            if (searchMap.get("linkman") != null && !"".equals(searchMap.get("linkman"))) {
                criteria.andLike("linkman", "%" + searchMap.get("linkman") + "%");
            }
            // 联系人手机
            if (searchMap.get("linkmanMobile") != null && !"".equals(searchMap.get("linkmanMobile"))) {
                criteria.andLike("linkmanMobile", "%" + searchMap.get("linkmanMobile") + "%");
            }
            // 类型
            if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                criteria.andLike("type", "%" + searchMap.get("type") + "%");
            }
            // 是否退运费
            if (searchMap.get("isReturnFreight") != null && !"".equals(searchMap.get("isReturnFreight"))) {
                criteria.andLike("isReturnFreight", "%" + searchMap.get("isReturnFreight") + "%");
            }
            // 申请状态
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andLike("status", "%" + searchMap.get("status") + "%");
            }
            // 凭证图片
            if (searchMap.get("evidence") != null && !"".equals(searchMap.get("evidence"))) {
                criteria.andLike("evidence", "%" + searchMap.get("evidence") + "%");
            }
            // 问题描述
            if (searchMap.get("description") != null && !"".equals(searchMap.get("description"))) {
                criteria.andLike("description", "%" + searchMap.get("description") + "%");
            }
            // 处理备注
            if (searchMap.get("remark") != null && !"".equals(searchMap.get("remark"))) {
                criteria.andLike("remark", "%" + searchMap.get("remark") + "%");
            }
            // 退款金额
            if (searchMap.get("returnMoney") != null) {
                criteria.andEqualTo("returnMoney", searchMap.get("returnMoney"));
            }
            // 退货退款原因
            if (searchMap.get("returnCause") != null) {
                criteria.andEqualTo("returnCause", searchMap.get("returnCause"));
            }
            // 管理员id
            if (searchMap.get("adminId") != null) {
                criteria.andEqualTo("adminId", searchMap.get("adminId"));
            }
        }
        return example;
    }

}
