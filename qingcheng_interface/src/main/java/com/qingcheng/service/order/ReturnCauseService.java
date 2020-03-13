package com.qingcheng.service.order;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.ReturnCause;

import java.util.*;

public interface ReturnCauseService {

    List<ReturnCause> findAll();

    PageResult<ReturnCause> findPage(int page, int size);

    List<ReturnCause> findList(Map<String, Object> searchMap);

    PageResult<ReturnCause> findPage(Map<String, Object> searchMap, int page, int size);

    ReturnCause findById(Integer id);

    void add(ReturnCause returnCause);

    void update(ReturnCause returnCause);

    void delete(Integer id);

}
